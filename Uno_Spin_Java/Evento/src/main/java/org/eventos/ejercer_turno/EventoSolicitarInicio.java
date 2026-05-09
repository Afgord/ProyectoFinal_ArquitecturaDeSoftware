package org.eventos.ejercer_turno;

/**
 * Acción del host del lobby: solicita iniciar la partida y dispara la
 * recolección de aceptaciones del resto de jugadores.
 */
public class EventoSolicitarInicio extends EventoAccion {
    private static final long serialVersionUID = 1L;

    public EventoSolicitarInicio(String idJugador, String idEvento) {
        super(idJugador, idEvento);
    }
}
