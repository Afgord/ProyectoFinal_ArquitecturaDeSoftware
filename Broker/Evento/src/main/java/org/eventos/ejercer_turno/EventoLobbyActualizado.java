package org.eventos.ejercer_turno;

import dtos.JugadorDTO;
import java.util.List;

/**
 * Notificación broadcast: el dominio publica el estado actual del lobby
 * (lista de jugadores conectados) cada vez que cambia.
 */
public class EventoLobbyActualizado extends Evento {
    private static final long serialVersionUID = 1L;
    private final String idPartida;
    private final List<JugadorDTO> jugadores;
    private final String idHost;

    public EventoLobbyActualizado(String idPartida, List<JugadorDTO> jugadores, String idHost, String idEvento) {
        super(idEvento);
        this.idPartida = idPartida;
        this.jugadores = jugadores;
        this.idHost = idHost;
    }

    public String getIdPartida() { return idPartida; }
    public List<JugadorDTO> getJugadores() { return jugadores; }
    public String getIdHost() { return idHost; }
}
