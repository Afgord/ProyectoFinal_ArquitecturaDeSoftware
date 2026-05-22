package org.eventos.ejercer_turno;

import java.util.List;

public class EventoListosIniciar extends Evento {

    private static final long serialVersionUID = 1L;

    private final List<String> jugadoresListos;
    private final int totalJugadoresEnSala;

    public EventoListosIniciar(
            List<String> jugadoresListos,
            int totalJugadoresEnSala,
            String idEvento) {
        super(idEvento);
        this.jugadoresListos = jugadoresListos;
        this.totalJugadoresEnSala = totalJugadoresEnSala;
    }

    public List<String> getJugadoresListos() {
        return jugadoresListos;
    }

    public int getTotalJugadoresEnSala() {
        return totalJugadoresEnSala;
    }
}
