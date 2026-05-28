package entidades;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoIniciarPartidaDTO;
import dtos.ResultadoUnirseDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Partida {

    public static final int JUGADORES_MINIMOS = 2;
    public static final int CARTAS_POR_JUGADOR_DEFAULT = 7;

    private final Tablero tablero;
    private EstadoPartida estado;
    private int cartasPorJugador;
    private final Set<String> jugadoresListos = new HashSet<>();

    public Partida(Mazo mazo, Descarte descarte, List<Jugador> jugadores, Ruleta ruleta) {
        this.estado = EstadoPartida.EN_ESPERA;
        this.cartasPorJugador = CARTAS_POR_JUGADOR_DEFAULT;
        this.tablero = new Tablero(mazo, descarte, jugadores, ruleta, this);
    }

    public Tablero getTablero() {
        return tablero;
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    void marcarEnCurso() {
        System.out.println("[Partida] Estado de partida → EN_CURSO");
        this.estado = EstadoPartida.EN_CURSO;
    }

    public int getCartasPorJugador() {
        return cartasPorJugador;
    }

    public void setCartasPorJugador(int cartasPorJugador) {
        this.cartasPorJugador = cartasPorJugador;
    }

    public ResultadoUnirseDTO unirseAPartida(JugadorDTO jugadorDTO) {
        ResultadoUnirseDTO resultado = tablero.registrarJugadorEnSala(jugadorDTO, estado);
        if (!resultado.isExito() || resultado.getCartaCima() != null) {
            return resultado;
        }

        ResultadoIniciarPartidaDTO inicioAutomatico = evaluarInicioAutomatico();
        if (inicioAutomatico == null) {
            return resultado;
        }

        return new ResultadoUnirseDTO(
            resultado.isExito(),
            resultado.getEventoTipo(),
            resultado.getJugadorUnido(),
            resultado.getJugadoresEnSala(),
            inicioAutomatico
        );
    }

    public ResultadoIniciarPartidaDTO registrarListoParaIniciar(String idJugador) {
        if (estado == EstadoPartida.EN_CURSO) {
            System.out.println("[Partida] Rechazo: la partida ya está en curso.");
            return rechazo(TipoEvento.PARTIDA_EN_CURSO);
        }

        boolean jugadorEnSala = tablero.getJugadores().stream()
            .anyMatch(j -> j.getIdJugador().equals(idJugador));

        if (!jugadorEnSala) {
            System.out.println("[Partida] Rechazo: jugador desconocido " + idJugador);
            return rechazo(TipoEvento.INICIO_RECHAZADO);
        }

        int jugadoresEnSala = tablero.getJugadores().size();
        if (!permiteInicioManual(jugadoresEnSala)) {
            System.out.println(
                "[Partida] Rechazo: inicio manual no permitido ("
                + jugadoresEnSala + "/" + Tablero.CAPACIDAD_MAXIMA + " jugadores)"
            );
            return rechazo(TipoEvento.INICIO_RECHAZADO);
        }

        jugadoresListos.add(idJugador);
        System.out.println(
            "[Partida] Jugador " + idJugador + " listo para iniciar ("
            + jugadoresListos.size() + "/" + jugadoresEnSala + ")"
        );

        boolean todosListos = tablero.getJugadores().stream()
            .allMatch(j -> jugadoresListos.contains(j.getIdJugador()));

        if (todosListos) {
            jugadoresListos.clear();
            return iniciar();
        }

        return new ResultadoIniciarPartidaDTO(
            true,
            TipoEvento.INICIO_PENDIENTE,
            null,
            null,
            null,
            new ArrayList<>(jugadoresListos),
            jugadoresEnSala
        );
    }

    public ResultadoIniciarPartidaDTO evaluarInicioAutomatico() {
        int jugadoresEnSala = tablero.getJugadores().size();
        if (!debeIniciarAutomaticamente(jugadoresEnSala)) {
            return null;
        }

        System.out.println(
            "[Partida] Sala completa (" + jugadoresEnSala + "/"
            + Tablero.CAPACIDAD_MAXIMA + "). Iniciando partida automáticamente..."
        );
        return iniciar();
    }

    public ResultadoIniciarPartidaDTO iniciar() {
        if (estado == EstadoPartida.EN_CURSO) {
            System.out.println("[Partida] Rechazo: la partida ya está en curso.");
            return rechazo(TipoEvento.PARTIDA_EN_CURSO);
        }

        int numJugadores = tablero.getJugadores().size();
        if (!puedeIniciar(numJugadores)) {
            System.out.println(
                "[Partida] Rechazo: no se puede iniciar (estado="
                + estado + ", jugadores=" + numJugadores + ")"
            );
            return rechazo(TipoEvento.INICIO_RECHAZADO);
        }

        tablero.repartirManosIniciales(cartasPorJugador);
        marcarEnCurso();

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

    private boolean debeIniciarAutomaticamente(int jugadoresEnSala) {
        return estado == EstadoPartida.EN_ESPERA
            && jugadoresEnSala == Tablero.CAPACIDAD_MAXIMA;
    }

    private boolean permiteInicioManual(int jugadoresEnSala) {
        return estado == EstadoPartida.EN_ESPERA
            && jugadoresEnSala >= JUGADORES_MINIMOS
            && jugadoresEnSala < Tablero.CAPACIDAD_MAXIMA;
    }

    private boolean puedeIniciar(int jugadoresEnSala) {
        return debeIniciarAutomaticamente(jugadoresEnSala)
            || permiteInicioManual(jugadoresEnSala);
    }

    private ResultadoIniciarPartidaDTO rechazo(TipoEvento tipo) {
        return new ResultadoIniciarPartidaDTO(false, tipo, null, null, null);
    }
}
