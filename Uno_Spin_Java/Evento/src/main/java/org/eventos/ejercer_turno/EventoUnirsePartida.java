package org.eventos.ejercer_turno;

import dtos.JugadorDTO;

/**
 * Acción del jugador para unirse a un lobby existente.
 */
public class EventoUnirsePartida extends EventoAccion {
    private static final long serialVersionUID = 1L;
    private final String idPartida;
    private final JugadorDTO jugador;

    public EventoUnirsePartida(String idPartida, JugadorDTO jugador, String idJugador, String idEvento) {
        super(idJugador, idEvento);
        this.idPartida = idPartida;
        this.jugador = jugador;
    }

    public String getIdPartida() { return idPartida; }
    public JugadorDTO getJugador() { return jugador; }
}
