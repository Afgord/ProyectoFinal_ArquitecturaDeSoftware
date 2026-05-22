package dtos;

import entidades.TipoEvento;
import java.io.Serializable;
import java.util.List;

public class ResultadoIniciarPartidaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final boolean exito;
    private final TipoEvento eventoTipo;
    private final List<JugadorDTO> estadoJugadores;
    private final CartaDTO cartaCima;
    private final String idJugadorTurnoActual;
    private final List<String> jugadoresListos;
    private final int totalJugadoresEnSala;

    public ResultadoIniciarPartidaDTO(
            boolean exito,
            TipoEvento eventoTipo,
            List<JugadorDTO> estadoJugadores,
            CartaDTO cartaCima,
            String idJugadorTurnoActual) {
        this(exito, eventoTipo, estadoJugadores, cartaCima, idJugadorTurnoActual, null, 0);
    }

    public ResultadoIniciarPartidaDTO(
            boolean exito,
            TipoEvento eventoTipo,
            List<JugadorDTO> estadoJugadores,
            CartaDTO cartaCima,
            String idJugadorTurnoActual,
            List<String> jugadoresListos) {
        this(exito, eventoTipo, estadoJugadores, cartaCima, idJugadorTurnoActual, jugadoresListos, 0);
    }

    public ResultadoIniciarPartidaDTO(
            boolean exito,
            TipoEvento eventoTipo,
            List<JugadorDTO> estadoJugadores,
            CartaDTO cartaCima,
            String idJugadorTurnoActual,
            List<String> jugadoresListos,
            int totalJugadoresEnSala) {
        this.exito = exito;
        this.eventoTipo = eventoTipo;
        this.estadoJugadores = estadoJugadores;
        this.cartaCima = cartaCima;
        this.idJugadorTurnoActual = idJugadorTurnoActual;
        this.jugadoresListos = jugadoresListos;
        this.totalJugadoresEnSala = totalJugadoresEnSala;
    }

    public boolean isExito() {
        return exito;
    }

    public TipoEvento getEventoTipo() {
        return eventoTipo;
    }

    public List<JugadorDTO> getEstadoJugadores() {
        return estadoJugadores;
    }

    public CartaDTO getCartaCima() {
        return cartaCima;
    }

    public String getIdJugadorTurnoActual() {
        return idJugadorTurnoActual;
    }

    public List<String> getJugadoresListos() {
        return jugadoresListos;
    }

    public int getTotalJugadoresEnSala() {
        return totalJugadoresEnSala;
    }
}
