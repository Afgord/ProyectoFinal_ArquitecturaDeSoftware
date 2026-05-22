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

import java.util.concurrent.CountDownLatch;

import java.util.concurrent.TimeUnit;

import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JOptionPane;

import javax.swing.SwingUtilities;



/**

 * Punto de entrada del nodo Publicador/Consumidor.

 *

 * Requiere Dominio Suscriptor y Broker activos. Muestra lobby visual y abre

 * el tablero al recibir EventoPartidaIniciada.

 */

public class Ejecutador {



    private static final int PUERTO_DOMINIO = 5000;

    private static final int PUERTO_BROKER = 5001;

    private static final int TIMEOUT_LOBBY_MS = 10_000;

    private static final int TIMEOUT_PARTIDA_MS = 60_000;



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

        ModeloLobby modeloLobby = sesion.getModeloLobby();

        EventoTraductor eventos = sesion.getTraductor();

        ReceptorProcesador procesador = sesion.getProcesador();



        CountDownLatch lobbyListo = new CountDownLatch(1);

        CountDownLatch partidaLista = new CountDownLatch(1);

        AtomicReference<String> errorSync = new AtomicReference<>();



        procesador.setEscuchaLobby(() -> lobbyListo.countDown());



        procesador.setEscuchaEstadoInicial(new ReceptorProcesador.EscuchaEstadoInicial() {

            @Override

            public void onEstadoRecibido() {

                partidaLista.countDown();

            }



            @Override

            public void onErrorConexion(String mensaje) {

                errorSync.set(mensaje);

                lobbyListo.countDown();

                partidaLista.countDown();

            }

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



        SwingUtilities.invokeLater(() -> {

            FrameLobby frameLobby = new FrameLobby(controlLobby, modeloLobby);

            frameLobby.setVisible(true);



            Thread esperaPartida = new Thread(() -> {

                try {

                    if (!partidaLista.await(TIMEOUT_PARTIDA_MS, TimeUnit.MILLISECONDS)) {

                        SwingUtilities.invokeLater(() -> mostrarErrorYSalir(

                            "Tiempo de espera agotado al iniciar la partida.\n"

                            + "Conecta al menos 2 jugadores y pulsa Iniciar partida."

                        ));

                        return;

                    }



                    if (errorSync.get() != null) {

                        SwingUtilities.invokeLater(() -> mostrarErrorYSalir(errorSync.get()));

                        return;

                    }



                    if (modelo.getCartaDescarteDTO() == null || modelo.getJugadoresDTO().isEmpty()) {

                        SwingUtilities.invokeLater(() -> mostrarErrorYSalir(

                            "El servidor no envió el estado inicial de la partida."

                        ));

                        return;

                    }



                    SwingUtilities.invokeLater(() -> {

                        frameLobby.dispose();

                        eventos.setRedHabilitada(true);

                        System.out.println("=== [CLIENTE] Partida iniciada. Turno actual: "

                                + modelo.getIdJugadorTurnoActual() + " ===");

                        new FrameTablero(controlJuego, modelo, audioModel).setVisible(true);

                    });

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                    SwingUtilities.invokeLater(() -> mostrarErrorYSalir("Conexión interrumpida."));

                }

            }, "espera-partida");

            esperaPartida.setDaemon(true);

            esperaPartida.start();

        });

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


