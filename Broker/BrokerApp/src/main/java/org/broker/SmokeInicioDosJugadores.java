package org.broker;

import dtos.JugadorDTO;
import entrada.Receptor;
import entrada.ServidorTCP;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.codedesc.CodeDescFactory;
import org.codedesc.IDeserializador;
import org.codedesc.ISerializador;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoAccion;
import org.eventos.ejercer_turno.EventoFallo;
import org.eventos.ejercer_turno.EventoIniciarPartida;
import org.eventos.ejercer_turno.EventoListosIniciar;
import org.eventos.ejercer_turno.EventoPartidaIniciada;
import org.eventos.ejercer_turno.EventoUnirseExitoso;
import org.eventos.ejercer_turno.EventoUnirsePartida;
import salida.DispatcherFactory;
import salida.IDispatcher;

/**
 * Smoke test headless: 2 jugadores confirman inicio por consenso.
 */
public class SmokeInicioDosJugadores {

    private static final String HOST = System.getProperty("uno.host", "127.0.0.1");
    private static final int BROKER = 5001;

    public static void main(String[] args) throws Exception {
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        ISerializador<EventoAccion> serializador = CodeDescFactory.crearSerializador();
        IDeserializador<Evento> deserializador = CodeDescFactory.crearDeserializador();

        CountDownLatch lobby = new CountDownLatch(1);
        CountDownLatch listos = new CountDownLatch(1);
        CountDownLatch partida = new CountDownLatch(1);
        AtomicReference<String> error = new AtomicReference<>();

        ServidorTCP servidor = new ServidorTCP(5002);
        servidor.addObserver(new Receptor(bytes -> {
            Evento e = deserializador.bytesAObjeto(bytes);
            if (e == null) return;
            System.out.println("[SMOKE-2] Recibido: " + e.getClass().getSimpleName());
            if (e instanceof EventoUnirseExitoso unirse) {
                int n = unirse.getJugadoresEnSala() != null ? unirse.getJugadoresEnSala().size() : 0;
                if (n >= 2) lobby.countDown();
            } else if (e instanceof EventoListosIniciar listosEvt) {
                int confirmados = listosEvt.getJugadoresListos() != null
                    ? listosEvt.getJugadoresListos().size() : 0;
                System.out.println("[SMOKE-2] Listos: " + confirmados + "/"
                    + listosEvt.getTotalJugadoresEnSala());
                if (confirmados == 1 && listosEvt.getTotalJugadoresEnSala() == 2) {
                    listos.countDown();
                }
            } else if (e instanceof EventoPartidaIniciada iniciada) {
                int jugadores = iniciada.getJugadores() != null ? iniciada.getJugadores().size() : 0;
                boolean manosOk = iniciada.getJugadores() != null
                    && iniciada.getJugadores().stream().allMatch(j -> j.getMano() != null && j.getMano().size() == 7);
                System.out.println("[SMOKE-2] Partida iniciada. Jugadores=" + jugadores
                    + ", cartaCima=" + iniciada.getCartaEnCima()
                    + ", turno=" + iniciada.getIdJugadorTurnoActual()
                    + ", 7 cartas c/u=" + manosOk);
                if (jugadores == 2 && iniciada.getCartaEnCima() != null && manosOk) {
                    partida.countDown();
                } else {
                    error.set("Estado inválido en EventoPartidaIniciada (esperado 2 jugadores)");
                    partida.countDown();
                }
            } else if (e instanceof EventoFallo fallo) {
                error.set("Fallo inesperado: " + fallo.getError());
                listos.countDown();
                partida.countDown();
            }
        }));
        servidor.iniciar();
        Thread.sleep(500);

        String[] ids = {"1", "2"};
        String[] nombres = {"Rafael", "Jugador 2"};

        for (int i = 0; i < ids.length; i++) {
            EventoUnirsePartida evt = new EventoUnirsePartida(
                ids[i], "UNIRSE_" + ids[i], new JugadorDTO(ids[i], nombres[i]));
            dispatcher.dispatch(HOST, BROKER, serializador.objetoABytes(evt));
            Thread.sleep(300);
        }

        if (!lobby.await(10, TimeUnit.SECONDS)) {
            System.err.println("[SMOKE-2] FALLO: timeout lobby (2 jugadores)");
            System.exit(1);
        }

        dispatcher.dispatch(HOST, BROKER, serializador.objetoABytes(
            new EventoIniciarPartida("1", "INICIAR_1")));

        if (!listos.await(10, TimeUnit.SECONDS)) {
            System.err.println("[SMOKE-2] FALLO: timeout EventoListosIniciar (1/2)");
            System.exit(1);
        }

        dispatcher.dispatch(HOST, BROKER, serializador.objetoABytes(
            new EventoIniciarPartida("2", "INICIAR_2")));

        if (!partida.await(15, TimeUnit.SECONDS)) {
            System.err.println("[SMOKE-2] FALLO: timeout partida iniciada");
            System.exit(1);
        }

        if (error.get() != null) {
            System.err.println("[SMOKE-2] FALLO: " + error.get());
            System.exit(1);
        }

        System.out.println("[SMOKE-2] OK - Inicio por consenso con 2 jugadores funciona.");
        servidor.detener();
        System.exit(0);
    }
}
