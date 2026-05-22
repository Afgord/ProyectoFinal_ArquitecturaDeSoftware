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

/** Smoke test: 3 jugadores confirman inicio por consenso. */
public class SmokeInicioTresJugadores {

    private static final String HOST = System.getProperty("uno.host", "127.0.0.1");
    private static final int BROKER = 5001;

    public static void main(String[] args) throws Exception {
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        ISerializador<EventoAccion> serializador = CodeDescFactory.crearSerializador();
        IDeserializador<Evento> deserializador = CodeDescFactory.crearDeserializador();

        CountDownLatch lobby = new CountDownLatch(1);
        CountDownLatch listos2 = new CountDownLatch(1);
        CountDownLatch partida = new CountDownLatch(1);
        AtomicReference<String> error = new AtomicReference<>();

        ServidorTCP servidor = new ServidorTCP(5002);
        servidor.addObserver(new Receptor(bytes -> {
            Evento e = deserializador.bytesAObjeto(bytes);
            if (e == null) return;
            System.out.println("[SMOKE-3] Recibido: " + e.getClass().getSimpleName());
            if (e instanceof EventoUnirseExitoso unirse) {
                int n = unirse.getJugadoresEnSala() != null ? unirse.getJugadoresEnSala().size() : 0;
                if (n == 3) {
                    lobby.countDown();
                }
            } else if (e instanceof EventoListosIniciar listosEvt) {
                int confirmados = listosEvt.getJugadoresListos() != null
                    ? listosEvt.getJugadoresListos().size() : 0;
                System.out.println("[SMOKE-3] Listos: " + confirmados + "/"
                    + listosEvt.getTotalJugadoresEnSala());
                if (confirmados == 2 && listosEvt.getTotalJugadoresEnSala() == 3) {
                    listos2.countDown();
                }
            } else if (e instanceof EventoPartidaIniciada iniciada) {
                int jugadores = iniciada.getJugadores() != null ? iniciada.getJugadores().size() : 0;
                boolean manosOk = iniciada.getJugadores() != null
                    && iniciada.getJugadores().stream().allMatch(j -> j.getMano() != null && j.getMano().size() == 7);
                if (jugadores == 3 && iniciada.getCartaEnCima() != null && manosOk) {
                    partida.countDown();
                } else {
                    error.set("Estado inválido (esperado 3 jugadores)");
                    partida.countDown();
                }
            } else if (e instanceof EventoFallo fallo) {
                error.set("Fallo inesperado: " + fallo.getError());
                partida.countDown();
            }
        }));
        servidor.iniciar();
        Thread.sleep(500);

        for (String id : new String[] {"1", "2", "3"}) {
            dispatcher.dispatch(HOST, BROKER, serializador.objetoABytes(
                new EventoUnirsePartida(id, "UNIRSE_" + id, new JugadorDTO(id, "Jugador " + id))));
            Thread.sleep(300);
        }

        if (!lobby.await(10, TimeUnit.SECONDS)) {
            System.err.println("[SMOKE-3] FALLO: timeout lobby (3 jugadores)");
            System.exit(1);
        }

        dispatcher.dispatch(HOST, BROKER, serializador.objetoABytes(
            new EventoIniciarPartida("1", "INICIAR_1")));
        dispatcher.dispatch(HOST, BROKER, serializador.objetoABytes(
            new EventoIniciarPartida("2", "INICIAR_2")));

        if (!listos2.await(10, TimeUnit.SECONDS)) {
            System.err.println("[SMOKE-3] FALLO: timeout EventoListosIniciar (2/3)");
            System.exit(1);
        }

        dispatcher.dispatch(HOST, BROKER, serializador.objetoABytes(
            new EventoIniciarPartida("3", "INICIAR_3")));

        if (!partida.await(15, TimeUnit.SECONDS)) {
            System.err.println("[SMOKE-3] FALLO: timeout partida iniciada");
            System.exit(1);
        }

        if (error.get() != null) {
            System.err.println("[SMOKE-3] FALLO: " + error.get());
            System.exit(1);
        }

        System.out.println("[SMOKE-3] OK - Inicio por consenso con 3 jugadores funciona.");
        servidor.detener();
        System.exit(0);
    }
}
