package org.eventos.ejercer_turno;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.util.List;

public class EventoPartidaIniciada extends Evento {

    private static final long serialVersionUID = 1L;

    private List<JugadorDTO> jugadores;
    private CartaDTO cartaEnCima;
    private String idJugadorTurnoActual;

    public EventoPartidaIniciada(
            List<JugadorDTO> jugadores,
            CartaDTO cartaEnCima,
            String idJugadorTurnoActual,
            String idEvento) {
        super(idEvento);
        this.jugadores = jugadores;
        this.cartaEnCima = cartaEnCima;
        this.idJugadorTurnoActual = idJugadorTurnoActual;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public CartaDTO getCartaEnCima() {
        return cartaEnCima;
    }

    public String getIdJugadorTurnoActual() {
        return idJugadorTurnoActual;
    }
}
