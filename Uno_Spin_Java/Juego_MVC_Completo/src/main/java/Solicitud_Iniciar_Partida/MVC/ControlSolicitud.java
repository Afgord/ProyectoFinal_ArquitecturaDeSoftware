package Solicitud_Iniciar_Partida.MVC;

import Crear_Partida_Lobby.MVC.ControlLobby;

/**
 * Controlador del MVC de la solicitud de inicio. Delegado fino sobre
 * ControlLobby para mantener el flujo del lobby como unico punto de
 * publicacion outbound. Solo expone aceptar() / esperar().
 */
public class ControlSolicitud {

    private final ControlLobby controlLobby;

    public ControlSolicitud(ControlLobby controlLobby) {
        this.controlLobby = controlLobby;
    }

    public void aceptar() {
        controlLobby.responderSolicitud(true);
    }

    public void esperar() {
        controlLobby.responderSolicitud(false);
    }
}
