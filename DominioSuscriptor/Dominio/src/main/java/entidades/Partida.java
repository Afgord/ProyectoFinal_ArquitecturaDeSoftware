package entidades;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoIniciarPartidaDTO;
import java.util.List;

public class Partida {

    public static final int JUGADORES_MINIMOS = PoliticaInicioPartida.JUGADORES_MINIMOS;
    public static final int CARTAS_POR_JUGADOR = 7;

    public boolean puedeIniciar(EstadoPartida estado, int numJugadores) {
        return PoliticaInicioPartida.puedeIniciar(estado, numJugadores);
    }

    public ResultadoIniciarPartidaDTO iniciar(Tablero tablero) {
        EstadoPartida estado = tablero.getEstadoPartida();
        int numJugadores = tablero.getJugadores().size();

        if (estado == EstadoPartida.EN_CURSO) {
            System.out.println("[Partida] Rechazo: la partida ya está en curso.");
            return rechazo(TipoEvento.PARTIDA_EN_CURSO);
        }

        if (!puedeIniciar(estado, numJugadores)) {
            System.out.println(
                "[Partida] Rechazo: no se puede iniciar (estado="
                + estado + ", jugadores=" + numJugadores + ")"
            );
            return rechazo(TipoEvento.INICIO_RECHAZADO);
        }

        tablero.ejecutarRepartoInicial();
        tablero.setEstadoPartida(EstadoPartida.EN_CURSO);

        List<JugadorDTO> estadoJugadores = tablero.generarEstadoDTO();
        CartaDTO cartaCima = tablero.obtenerCartaCimaDTO();
        String idTurno = tablero.getJugadorActual().getIdJugador();

        System.out.println(
            "[Partida] Partida iniciada. Turno inicial: jugador "
            + idTurno + ". Carta en descarte: " + cartaCima.getValor()
        );

        return new ResultadoIniciarPartidaDTO(
            true,
            TipoEvento.PARTIDA_INICIADA,
            estadoJugadores,
            cartaCima,
            idTurno
        );
    }

    private ResultadoIniciarPartidaDTO rechazo(TipoEvento tipo) {
        return new ResultadoIniciarPartidaDTO(false, tipo, null, null, null);
    }
}
