package org.eventos.ejercer_turno;

import dtos.JugadorDTO;

/**
 * Acción del jugador host para crear un nuevo lobby de partida.
 */
public class EventoCrearPartida extends EventoAccion {
    private static final long serialVersionUID = 1L;
    private final JugadorDTO host;

    public EventoCrearPartida(JugadorDTO host, String idJugador, String idEvento) {
        super(idJugador, idEvento);
        this.host = host;
    }

    public JugadorDTO getHost() { return host; }
}
