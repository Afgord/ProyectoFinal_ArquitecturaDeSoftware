package Iniciar_Partida.MVC;

import Iniciar_Partida.Interfaces.IModelEventosLobby;

public class ControlLobby {

    private final IModelEventosLobby eventos;

    public ControlLobby(IModelEventosLobby eventos) {
        this.eventos = eventos;
    }

    public void solicitarIniciarPartida() {
        eventos.emitirIniciarPartida();
    }
}
