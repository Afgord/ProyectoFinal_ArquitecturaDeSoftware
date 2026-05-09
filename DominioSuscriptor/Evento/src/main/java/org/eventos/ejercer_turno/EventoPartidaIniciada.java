package org.eventos.ejercer_turno;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.util.List;

/**
 * Notificación broadcast: la partida arrancó. Lleva el estado inicial:
 * lista de jugadores con sus manos repartidas, primera carta del
 * descarte y el id del jugador que arranca el turno.
 */
public class EventoPartidaIniciada extends Evento {
    private static final long serialVersionUID = 1L;
    private final String idPartida;
    private final List<JugadorDTO> jugadores;
    private final CartaDTO descarteInicial;
    private final String idJugadorTurnoActual;

    public EventoPartidaIniciada(String idPartida, List<JugadorDTO> jugadores, CartaDTO descarteInicial,
                                 String idJugadorTurnoActual, String idEvento) {
        super(idEvento);
        this.idPartida = idPartida;
        this.jugadores = jugadores;
        this.descarteInicial = descarteInicial;
        this.idJugadorTurnoActual = idJugadorTurnoActual;
    }

    public String getIdPartida() { return idPartida; }
    public List<JugadorDTO> getJugadores() { return jugadores; }
    public CartaDTO getDescarteInicial() { return descarteInicial; }
    public String getIdJugadorTurnoActual() { return idJugadorTurnoActual; }
}
