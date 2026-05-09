package org.eventos.ejercer_turno;

/**
 * Acción del jugador respondiendo a la solicitud de inicio del host.
 * acepta=true equivale a ACEPTAR, acepta=false a ESPERAR.
 */
public class EventoResponderSolicitudInicio extends EventoAccion {
    private static final long serialVersionUID = 1L;
    private final boolean acepta;

    public EventoResponderSolicitudInicio(boolean acepta, String idJugador, String idEvento) {
        super(idJugador, idEvento);
        this.acepta = acepta;
    }

    public boolean isAcepta() { return acepta; }
}
