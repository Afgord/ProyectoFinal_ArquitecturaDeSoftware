package dtos;

import java.io.Serializable;
import java.util.List;

/**
 * Snapshot del estado inicial de la partida tras repartir cartas y
 * elegir descarte inicial. Se broadcastea como EventoPartidaIniciada.
 */
public class EstadoPartidaInicialDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String idPartida;
    private final List<JugadorDTO> jugadores;
    private final CartaDTO descarteInicial;
    private final String idJugadorTurnoActual;

    public EstadoPartidaInicialDTO(String idPartida, List<JugadorDTO> jugadores,
                                   CartaDTO descarteInicial, String idJugadorTurnoActual) {
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
