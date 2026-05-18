/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoGritoDTO;
import dtos.ResultadoJugadaDTO;
import dtos.ResultadoUnirseDTO;
import java.util.List;
import java.util.stream.Collectors;

import static entidades.Valor.*;
/**
 * 
 * @author lagar
 */
public class Tablero {

    private static final int CAPACIDAD_MAXIMA = 4;

    private final Mazo mazo;
    private final Descarte descarte;
    private final List<Jugador> jugadores;
    private Ruleta ruleta;

    private int turnoActual;
    private boolean sentidoReloj;
    private TipoEvento estadoPendienteRuleta = null;

    private EstadoPartida estadoPartida;

    public Tablero(Mazo mazo, Descarte descarte, List<Jugador> jugadores, Ruleta ruleta) {
        this.sentidoReloj = true;
        this.jugadores = jugadores;
        this.turnoActual = 0;
        this.mazo = mazo;
        this.descarte = descarte;
        this.ruleta = ruleta;
        this.estadoPartida = EstadoPartida.EN_ESPERA;
    }

    public ResultadoUnirseDTO unirseAPartida(JugadorDTO jugadorDTO) {
        System.out.println("[Tablero] Solicitud de unirse: " + jugadorDTO.getNombre());

        if (estadoPartida == EstadoPartida.EN_CURSO) {
            System.out.println("[Tablero] Rechazo: partida ya en curso.");

            return new ResultadoUnirseDTO(
                false,
                TipoEvento.PARTIDA_EN_CURSO,
                null,
                generarEstadoSalaDTO()
            );
        }

        if (jugadores.size() >= CAPACIDAD_MAXIMA) {
            System.out.println("[Tablero] Rechazo: sala llena (" + CAPACIDAD_MAXIMA + " jugadores).");

            return new ResultadoUnirseDTO(
                false,
                TipoEvento.PARTIDA_LLENA,
                null,
                generarEstadoSalaDTO()
            );
        }

        Jugador nuevoJugador = new Jugador(
            jugadorDTO.idJugador(),
            jugadorDTO.getNombre(),
            jugadorDTO.getUrlAvatar(),
            new Mano()
        );

        jugadores.add(nuevoJugador);

        System.out.println(
            "[Tablero] " + nuevoJugador.getNombre()
            + " se unió. Jugadores en sala: "
            + jugadores.size() + "/" + CAPACIDAD_MAXIMA
        );

        JugadorDTO jugadorUnidoDTO = new JugadorDTO(
            nuevoJugador.getIdJugador(),
            nuevoJugador.getNombre()
        );

        return new ResultadoUnirseDTO(
            true,
            TipoEvento.UNIRSE_EXITOSO,
            jugadorUnidoDTO,
            generarEstadoSalaDTO()
        );
    }

    private List<JugadorDTO> generarEstadoSalaDTO() {
        return jugadores.stream()
            .map(j -> new JugadorDTO(j.getIdJugador(), j.getNombre()))
            .collect(Collectors.toList());
    }

    public ResultadoJugadaDTO ejecutarJugada(CartaDTO cartaDto) {
        System.out.println("[Tablero] Solicitud de jugada: " + cartaDto.getValor());

        if (ejecutarLogicaJugada(buscarCartaEnMano(cartaDto), cartaDto)) {
            JugadorDTO ganador = verificarGanador();
            TipoEvento eventoFinal = TipoEvento.DESCARTE_EXITOSO;

            if (estadoPendienteRuleta != null) {
                eventoFinal = estadoPendienteRuleta;
            }

            if (ganador != null) {
                eventoFinal = TipoEvento.GANADOR;
            }

            return new ResultadoJugadaDTO(
                true,
                eventoFinal,
                ganador,
                generarEstadoDTO(),
                obtenerCartaCimaDTO(),
                getJugadorActual().getIdJugador(),
                (eventoFinal == TipoEvento.RULETA_ACTIVADA)
                    ? ruleta.getUltimoResultado()
                    : null
            );
        }

        return new ResultadoJugadaDTO(
            false,
            TipoEvento.ERROR,
            null,
            generarEstadoDTO(),
            obtenerCartaCimaDTO(),
            getJugadorActual().getIdJugador(),
            null
        );
    }

    private boolean ejecutarLogicaJugada(Carta cartaReal, CartaDTO cartaDto) {
        if (cartaReal == null) {
            return false;
        }

        if (cartaReal.esComodin()) {
            cartaReal.setColor(cartaDto.getColor());
        }

        if (descarte.recibirCarta(cartaReal)) {
            getJugadorActual().tirarCarta(cartaReal);

            if (cartaReal.esSpin()) {
                siguienteTurno();
                this.estadoPendienteRuleta = ruleta.girarYAplicar(this);

            } else {
                this.estadoPendienteRuleta = null;

                if (!cartaReal.esNumerica()) {
                    ejecutarEfecto(cartaReal);
                } else {
                    siguienteTurno();
                }
            }

            return true;
        }

        return false;
    }

    public void ejecutarEfecto(Carta carta) {
        switch (carta.getValor()) {
            case REVERSA:
                cambiarSentido();

                if (jugadores.size() == 2) {
                    siguienteTurno();
                }

                siguienteTurno();
                break;

            case PROHIBIDO:
                siguienteTurno();
                siguienteTurno();
                break;

            case MASDOS:
                castigarSiguiente(2);
                break;

            case MASCUATRO:
                castigarSiguiente(4);
                break;

            default:
                siguienteTurno();
                break;
        }
    }

    private void castigarSiguiente(int cantidad) {
        siguienteTurno();

        Jugador victima = getJugadorActual();

        for (int i = 0; i < cantidad; i++) {
            darCartaAJugador(victima);
        }

        siguienteTurno();
    }

    public void darCartaAJugador(Jugador j) {
        if (!mazo.estaVacio()) {
            Carta c = mazo.tomarUnaCarta();

            if (c != null) {
                j.agregarCarta(c);
            }
        }
    }

    public void siguienteTurno() {
        if (jugadores.isEmpty()) {
            return;
        }

        int size = jugadores.size();

        turnoActual = sentidoReloj
            ? (turnoActual + 1) % size
            : (turnoActual - 1 + size) % size;
    }

    public ResultadoJugadaDTO pasarTurno() {
        System.out.println(
            "[Tablero] El jugador "
            + getJugadorActual().getNombre()
            + " pasa el turno."
        );

        siguienteTurno();

        return new ResultadoJugadaDTO(
            true,
            TipoEvento.CAMBIO_TURNO,
            null,
            generarEstadoDTO(),
            obtenerCartaCimaDTO(),
            getJugadorActual().getIdJugador(),
            null
        );
    }

    public void cambiarSentido() {
        sentidoReloj = !sentidoReloj;
    }

    public ResultadoJugadaDTO robarYPasar() {
        darCartaAJugador(getJugadorActual());
        siguienteTurno();

        return new ResultadoJugadaDTO(
            true,
            TipoEvento.ROBO_Y_PASO,
            null,
            generarEstadoDTO(),
            obtenerCartaCimaDTO(),
            getJugadorActual().getIdJugador(),
            null
        );
    }

    public ResultadoGritoDTO procesarGritoUno(JugadorDTO datosGrito) {
        Jugador actual = getJugadorActual();

        String idCastigado = null;
        TipoEvento evento = TipoEvento.GRITO_INVALIDO;
        boolean exito = false;

        if (datosGrito.idJugador().equals(actual.getIdJugador())) {

            if (actual.getNumCartas() == 1) {
                evento = TipoEvento.SE_SALVO;
                exito = true;
            }

        } else {

            if (actual.getNumCartas() == 1) {
                darCartaAJugador(actual);
                darCartaAJugador(actual);

                idCastigado = actual.getIdJugador();
                evento = TipoEvento.ATRAPADO;
                exito = true;
            }
        }

        return new ResultadoGritoDTO(
            exito,
            evento,
            idCastigado,
            generarEstadoDTO()
        );
    }

    private JugadorDTO verificarGanador() {
        for (Jugador j : jugadores) {

            if (j.getNumCartas() == 0) {
                return new JugadorDTO(
                    j.getIdJugador(),
                    j.getNombre()
                );
            }
        }

        return null;
    }

    private List<JugadorDTO> generarEstadoDTO() {
        return jugadores.stream().map(j -> {

            List<CartaDTO> manoDto = j.getMano().getCartasReales().stream()
                .map(c -> new CartaDTO(c.getValor(), c.getColor()))
                .collect(Collectors.toList());

            return new JugadorDTO(
                j.getIdJugador(),
                j.getNombre(),
                manoDto,
                false
            );

        }).collect(Collectors.toList());
    }

    private CartaDTO obtenerCartaCimaDTO() {
        Carta cima = descarte.getCartaCima();
        return new CartaDTO(cima.getValor(), cima.getColor());
    }

    private Carta buscarCartaEnMano(CartaDTO cartaDto) {
        return getJugadorActual()
            .getMano()
            .getCartasReales()
            .stream()
            .filter(c ->
                c.getValor() == cartaDto.getValor()
                && (
                    c.getColor() == Colores.NEGRO
                    || c.getColor() == cartaDto.getColor()
                )
            )
            .findFirst()
            .orElse(null);
    }

    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public Mazo getMazo() {
        return mazo;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public boolean isSentidoReloj() {
        return sentidoReloj;
    }

    public EstadoPartida getEstadoPartida() {
        return estadoPartida;
    }

    public void setEstadoPartida(EstadoPartida estado) {
        System.out.println("[Tablero] Estado de partida → " + estado);
        this.estadoPartida = estado;
    }
}