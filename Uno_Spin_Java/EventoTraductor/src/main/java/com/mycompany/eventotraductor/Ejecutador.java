package com.mycompany.eventotraductor;

import Cambiar_Color.Implementacion.SwingSeleccionColor;
import Ejercer_Turno.Interfaces.IServicioSeleccionColor;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.FrameTablero;
import Ejercer_Turno.MVC.ModeloJuego;
import contenido.AudioManager;
import dtos.JugadorDTO;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JOptionPane;

/**
 * Punto de entrada del nodo Publicador/Consumidor.
 *
 * Requiere Dominio Suscriptor y Broker activos. El estado inicial llega
 * desde el dominio al solicitar unirse/reconectar a la partida.
 */
public class Ejecutador {

    private static final int PUERTO_DOMINIO = 5000;
    private static final int PUERTO_BROKER = 5001;
    private static final int TIMEOUT_SINCRONIZACION_MS = 10_000;

    public static void main(String[] args) {
        String host = System.getProperty("uno.host", "127.0.0.1");
        String idJugadorLocal = args.length > 0 ? args[0] : "1";
        int puertoLocal = PUERTO_BROKER + Integer.parseInt(idJugadorLocal);

        if (!VerificadorConexion.puedeConectar(host, PUERTO_DOMINIO)) {
            mostrarErrorYSalir(
                "No se pudo conectar al Dominio Suscriptor en "
                + host + ":" + PUERTO_DOMINIO
                + ".\n\nLevanta primero DominioSuscriptor\\TraductorEventos (mvn exec:java)."
            );
        }

        if (!VerificadorConexion.puedeConectar(host, PUERTO_BROKER)) {
            mostrarErrorYSalir(
                "No se pudo conectar al Broker en "
                + host + ":" + PUERTO_BROKER
                + ".\n\nLevanta primero Broker\\BrokerApp (mvn exec:java)."
            );
        }

        System.out.println("=== [CLIENTE] Jugador " + idJugadorLocal
                + " | broker=" + host + ":" + PUERTO_BROKER
                + " | escucha=" + puertoLocal + " ===");

        SesionCliente sesion = BootstrapRed.iniciar(host, PUERTO_BROKER, puertoLocal, idJugadorLocal);
        ModeloJuego modelo = sesion.getModelo();
        EventoTraductor eventos = sesion.getTraductor();
        ReceptorProcesador procesador = sesion.getProcesador();

        CountDownLatch sincronizado = new CountDownLatch(1);
        AtomicReference<String> errorSync = new AtomicReference<>();

        procesador.setEscuchaEstadoInicial(new ReceptorProcesador.EscuchaEstadoInicial() {
            @Override
            public void onEstadoRecibido() {
                sincronizado.countDown();
            }

            @Override
            public void onErrorConexion(String mensaje) {
                errorSync.set(mensaje);
                sincronizado.countDown();
            }
        });

        eventos.setRedHabilitada(false);
        eventos.emitirUnirsePartida(crearJugador(idJugadorLocal));

        try {
            if (!sincronizado.await(TIMEOUT_SINCRONIZACION_MS, TimeUnit.MILLISECONDS)) {
                mostrarErrorYSalir(
                    "Tiempo de espera agotado al sincronizar con el Dominio.\n"
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

        if (modelo.getCartaDescarteDTO() == null || modelo.getJugadoresDTO().isEmpty()) {
            mostrarErrorYSalir("El servidor no envió el estado inicial de la partida.");
        }

        eventos.setRedHabilitada(true);
        System.out.println("=== [CLIENTE] Sincronizado. Turno actual: "
                + modelo.getIdJugadorTurnoActual() + " ===");

        IServicioSeleccionColor servicioColor = new SwingSeleccionColor();
        ControlJuego control = new ControlJuego(eventos, servicioColor);

        AudioManager audioModel = new AudioManager();
        audioModel.loadMusic("/sound/music/dkc1_achuatic.wav");
        audioModel.loadEffect("tirar", "/sound/effect/tirar.wav", 5);
        audioModel.loadEffect("jalar", "/sound/effect/jalar.wav", 5);
        audioModel.loadEffect("uno", "/sound/effect/uno.wav", 5);
        audioModel.loadEffect("alerta", "/sound/effect/alerta.wav", 5);

        java.awt.EventQueue.invokeLater(() -> new FrameTablero(control, modelo, audioModel).setVisible(true));
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
