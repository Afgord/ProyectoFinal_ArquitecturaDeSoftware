package org.eventos.ejercer_turno;

/**
 * Acción del jugador para abandonar el lobby antes de iniciar la partida.
 */
public class EventoAbandonarLobby extends EventoAccion {
    private static final long serialVersionUID = 1L;

    public EventoAbandonarLobby(String idJugador, String idEvento) {
        super(idJugador, idEvento);
    }
}
