package com.mycompany.eventotraductor;

import Cambiar_Color.Implementacion.SwingSeleccionColor;
import Crear_Partida_Lobby.Interfaces.IModeloLobbyDatos;
import Crear_Partida_Lobby.MVC.ControlLobby;
import Crear_Partida_Lobby.MVC.FrameAccesoLobby;
import Crear_Partida_Lobby.MVC.FrameLobby;
import Ejercer_Turno.Interfaces.IServicioSeleccionColor;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.FrameTablero;
import Ejercer_Turno.MVC.ModeloJuego;
import contenido.AudioManager;

/**
 * Punto de entrada del nodo Publicador/Consumidor.
 *
 * Cablea la frontera de red via BootstrapRed, monta el MVC del lobby
 * y, cuando el dominio anuncia la partida iniciada (via ModeloLobby),
 * carga el primer estado en el ModeloJuego y abre el FrameTablero del
 * MVC Ejercer_Turno, ya existente.
 *
 * Configurable via argumentos:
 *   args[0] = idJugadorLocal (default "1")
 *   args[1] = puertoLocal    (default 5001 + idJugadorLocal)
 *   args[2] = ipLocal        (default 127.0.0.1)
 *   args[3] = hostBroker     (default 192.168.100.97)
 *   args[4] = puertoBroker   (default 5001)
 */
public class Ejecutador {

    private static final String DEFAULT_HOST_BROKER = "192.168.100.97";
    private static final int DEFAULT_PUERTO_BROKER = 5001;
    private static final String DEFAULT_IP_LOCAL = "127.0.0.1";

    public static void main(String[] args) {
        String idJugadorLocal = args.length > 0 ? args[0] : "1";
        int puertoLocal = args.length > 1
                ? Integer.parseInt(args[1])
                : 5001 + Integer.parseInt(idJugadorLocal);
        String ipLocal = args.length > 2 ? args[2] : DEFAULT_IP_LOCAL;
        String hostBroker = args.length > 3 ? args[3] : DEFAULT_HOST_BROKER;
        int puertoBroker = args.length > 4 ? Integer.parseInt(args[4]) : DEFAULT_PUERTO_BROKER;

        // El nombre y avatar definitivos los recoge la pantalla de acceso del lobby;
        // aqui solo necesitamos un placeholder para el registro inicial en el broker.
        String nombrePlaceholder = "Jugador-" + idJugadorLocal;
        String avatarPlaceholder = "/avatares/avatar1.png";

        BootstrapRed bootstrap = BootstrapRed.iniciar(hostBroker, puertoBroker,
                ipLocal, puertoLocal, idJugadorLocal, nombrePlaceholder, avatarPlaceholder);

        EventoTraductor eventos = bootstrap.getTraductor();
        ModeloJuego modeloJuego = bootstrap.getModeloJuego();

        AudioManager audio = new AudioManager();
        try {
            audio.loadMusic("/sound/music/dkc1_achuatic.wav");
            audio.loadEffect("tirar", "/sound/effect/tirar.wav", 5);
            audio.loadEffect("jalar", "/sound/effect/jalar.wav", 5);
            audio.loadEffect("uno", "/sound/effect/uno.wav", 5);
            audio.loadEffect("alerta", "/sound/effect/alerta.wav", 5);
        } catch (Exception ex) {
            System.err.println("Audio no disponible: " + ex.getMessage());
        }

        ControlLobby controlLobby = new ControlLobby(eventos, bootstrap.getModeloLobby());

        FrameLobby.CallbackPartidaIniciada onIniciada = modeloLobby -> abrirTablero(modeloLobby,
                modeloJuego, eventos, audio);

        java.awt.EventQueue.invokeLater(() -> {
            FrameAccesoLobby acceso = new FrameAccesoLobby(controlLobby, new FrameAccesoLobby.CallbackAcceso() {
                @Override
                public void crearPartida() {
                    abrirLobby(controlLobby, bootstrap.getModeloLobby(), onIniciada);
                    controlLobby.solicitarCrearPartida();
                }
                @Override
                public void unirsePartida(String idPartida) {
                    abrirLobby(controlLobby, bootstrap.getModeloLobby(), onIniciada);
                    controlLobby.solicitarUnirsePartida(idPartida);
                }
            });
            acceso.setVisible(true);
        });
    }

    private static void abrirLobby(ControlLobby controlLobby, IModeloLobbyDatos modelo,
                                   FrameLobby.CallbackPartidaIniciada onIniciada) {
        FrameLobby frame = new FrameLobby(controlLobby, modelo, onIniciada);
        frame.setVisible(true);
    }

    private static void abrirTablero(IModeloLobbyDatos modeloLobby, ModeloJuego modeloJuego,
                                     EventoTraductor eventos, AudioManager audio) {
        // Inyecta el snapshot inicial al ModeloJuego usando los DTOs primitivos
        // que ModeloLobby cacheo desde EventoPartidaIniciada.
        modeloJuego.aplicarActualizacion(
                modeloLobby.getJugadoresIniciales(),
                modeloLobby.getDescarteInicial(),
                modeloLobby.getIdJugadorTurnoInicial());

        IServicioSeleccionColor servicioColor = new SwingSeleccionColor();
        ControlJuego control = new ControlJuego(eventos, servicioColor);

        if (audio != null) audio.playMusicLoop();
        FrameTablero frame = new FrameTablero(control, modeloJuego, audio);
        frame.setVisible(true);
    }
}
