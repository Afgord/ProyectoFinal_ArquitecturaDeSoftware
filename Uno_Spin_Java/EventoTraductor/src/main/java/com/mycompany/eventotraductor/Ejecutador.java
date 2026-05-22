package com.mycompany.eventotraductor;

import Cambiar_Color.Implementacion.SwingSeleccionColor;
import Ejercer_Turno.Interfaces.IServicioSeleccionColor;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.FrameTablero;
import Ejercer_Turno.MVC.ModeloJuego;
import Iniciar_Partida.MVC.ControlLobby;
import Iniciar_Partida.MVC.FrameLobby;
import Iniciar_Partida.MVC.ModeloLobby;
import contenido.AudioManager;
import dtos.JugadorDTO;
import entrada.Receptor;
import entrada.ServidorTCP;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.codedesc.CodeDescFactory;
import org.codedesc.IDeserializador;
import org.codedesc.ISerializador;
import org.eventos.ejercer_turno.Evento;
import salida.DispatcherFactory;
import salida.IDispatcher;

/**
 * Composition root del nodo Publicador/Consumidor.
 *
 * Cablea red, traductores de eventos y MVC. Requiere Dominio y Broker activos.
 */
public class Ejecutador {

    private static final int PUERTO_DOMINIO = 5000;
    private static final int PUERTO_BROKER = 5001;
    private static final int TIMEOUT_CONEXION_MS = 3_000;
    private static final int TIMEOUT_LOBBY_MS = 10_000;
    private static final int TIMEOUT_PARTIDA_MS = 60_000;

    private record NodoCliente(
            ModeloJuego modelo,
            ModeloLobby modeloLobby,
            EventoTraductor traductor,
            ReceptorProcesador procesador) {
    }

    public static void main(String[] args) {
        String host = System.getProperty("uno.host", "127.0.0.1");
        String idJugadorLocal = args.length > 0 ? args[0] : "1";
        int puertoLocal = PUERTO_BROKER + Integer.parseInt(idJugadorLocal);

        if (!puedeConectar(host, PUERTO_DOMINIO)) {
            mostrarErrorYSalir(
                "No se pudo conectar al Dominio Suscriptor en "
                + host + ":" + PUERTO_DOMINIO
                + ".\n\nLevanta primero DominioSuscriptor\\TraductorEventos (mvn exec:java)."
            );
        }

        if (!puedeConectar(host, PUERTO_BROKER)) {
            mostrarErrorYSalir(
                "No se pudo conectar al Broker en "
                + host + ":" + PUERTO_BROKER
                + ".\n\nLevanta primero Broker\\BrokerApp (mvn exec:java)."
            );
        }

        System.out.println("=== [CLIENTE] Jugador " + idJugadorLocal
                + " | broker=" + host + ":" + PUERTO_BROKER
                + " | escucha=" + puertoLocal + " ===");

        NodoCliente nodo = iniciarNodo(host, PUERTO_BROKER, puertoLocal, idJugadorLocal);
        ModeloJuego modelo = nodo.modelo();
        ModeloLobby modeloLobby = nodo.modeloLobby();
        EventoTraductor eventos = nodo.traductor();
        ReceptorProcesador procesador = nodo.procesador();

        CountDownLatch lobbyListo = new CountDownLatch(1);
        AtomicReference<String> errorSync = new AtomicReference<>();

        procesador.setEscuchaLobby(lobbyListo::countDown);
        procesador.setEscuchaEstadoInicial(mensaje -> {
            errorSync.set(mensaje);
            lobbyListo.countDown();
        });

        eventos.setRedHabilitada(false);
        eventos.emitirUnirsePartida(crearJugador(idJugadorLocal));

        try {
            if (!lobbyListo.await(TIMEOUT_LOBBY_MS, TimeUnit.MILLISECONDS)) {
                mostrarErrorYSalir(
                    "Tiempo de espera agotado al unirse al lobby.\n"
                    + "Verifica que Dominio y Broker estén corriendo."
                );
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            mostrarErrorYSalir("Conexión interrumpida.");
        }

        if (errorSync.get() != null) {
            mostrarErrorYSalir(errorSync.get());
        }

        ControlLobby controlLobby = new ControlLobby(eventos);
        IServicioSeleccionColor servicioColor = new SwingSeleccionColor();
        ControlJuego controlJuego = new ControlJuego(eventos, servicioColor);

        AudioManager audioModel = new AudioManager();
        audioModel.loadMusic("/sound/music/dkc1_achuatic.wav");
        audioModel.loadEffect("tirar", "/sound/effect/tirar.wav", 5);
        audioModel.loadEffect("jalar", "/sound/effect/jalar.wav", 5);
        audioModel.loadEffect("uno", "/sound/effect/uno.wav", 5);
        audioModel.loadEffect("alerta", "/sound/effect/alerta.wav", 5);

        AtomicReference<FrameLobby> frameLobbyRef = new AtomicReference<>();
        AtomicReference<Timer> timeoutPartidaRef = new AtomicReference<>();
        AtomicBoolean transicionHecha = new AtomicBoolean(false);

        Runnable abrirTablero = () -> {
            if (!transicionHecha.compareAndSet(false, true)) {
                return;
            }
            Timer timeout = timeoutPartidaRef.get();
            if (timeout != null) {
                timeout.stop();
            }

            FrameLobby frameLobby = frameLobbyRef.get();
            if (frameLobby == null) {
                return;
            }

            if (modelo.getCartaDescarteDTO() == null || modelo.getJugadoresDTO().isEmpty()) {
                mostrarErrorYSalir("El servidor no envió el estado inicial de la partida.");
                return;
            }

            frameLobby.dispose();
            eventos.setRedHabilitada(true);
            System.out.println("=== [CLIENTE] Partida iniciada. Turno actual: "
                    + modelo.getIdJugadorTurnoActual() + " ===");
            new FrameTablero(controlJuego, modelo, audioModel).setVisible(true);
        };

        procesador.setEscuchaPartidaIniciada(
            () -> SwingUtilities.invokeLater(abrirTablero)
        );

        try {
            SwingUtilities.invokeAndWait(() -> {
                FrameLobby frameLobby = new FrameLobby(controlLobby, modeloLobby);
                frameLobby.setOnPartidaIniciada(abrirTablero);
                frameLobby.setVisible(true);
                frameLobbyRef.set(frameLobby);

                Timer timeoutPartida = new Timer(TIMEOUT_PARTIDA_MS, e ->
                    mostrarErrorYSalir(
                        "Tiempo de espera agotado al iniciar la partida.\n"
                        + "Conecta 2-3 jugadores y confirma, o espera 4 para auto-inicio."
                    )
                );
                timeoutPartida.setRepeats(false);
                timeoutPartida.start();
                timeoutPartidaRef.set(timeoutPartida);

                procesador.marcarLobbyUiListo();
                frameLobby.verificarPartidaPendiente();
            });
        } catch (Exception e) {
            mostrarErrorYSalir("No se pudo mostrar el lobby: " + e.getMessage());
        }
    }

    private static NodoCliente iniciarNodo(String hostBroker, int puertoBroker,
                                           int puertoLocal, String idJugadorLocal) {
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        ISerializador<Evento> serializador = CodeDescFactory.crearSerializador();
        IDeserializador<Evento> deserializador = CodeDescFactory.crearDeserializador();

        ModeloJuego modelo = new ModeloJuego(idJugadorLocal);
        ModeloLobby modeloLobby = new ModeloLobby(idJugadorLocal);
        EventoTraductor traductor = new EventoTraductor(
            dispatcher, hostBroker, puertoBroker, serializador, idJugadorLocal);
        ReceptorProcesador procesador = new ReceptorProcesador(
            deserializador, modelo, modeloLobby, idJugadorLocal);

        ServidorTCP servidor = new ServidorTCP(puertoLocal);
        servidor.addObserver(new Receptor(procesador));
        servidor.iniciar();

        return new NodoCliente(modelo, modeloLobby, traductor, procesador);
    }

    private static boolean puedeConectar(String host, int puerto) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, puerto), TIMEOUT_CONEXION_MS);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static JugadorDTO crearJugador(String id) {
        String nombre = switch (id) {
            case "1" -> "Rafael";
            case "2" -> "Jugador 2";
            case "3" -> "Jugador 3";
            case "4" -> "Jugador 4";
            default -> "Jugador " + id;
        };
        return new JugadorDTO(id, nombre);
    }

    private static void mostrarErrorYSalir(String mensaje) {
        System.err.println("[CLIENTE] " + mensaje);
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            "UNO Spin - Sin conexión",
            JOptionPane.ERROR_MESSAGE
        );
        System.exit(1);
    }
}
