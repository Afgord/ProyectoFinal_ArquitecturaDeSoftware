/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoGritoDTO;
import dtos.ResultadoJugadaDTO;
import static entidades.Valor.MASCUATRO;
import static entidades.Valor.MASDOS;
import static entidades.Valor.PROHIBIDO;
import static entidades.Valor.REVERSA;
import java.util.List;
import java.util.stream.Collectors;

public class Tablero {
    private final Mazo mazo;
    private final Descarte descarte;
    private final List<Jugador> jugadores;
    private Ruleta ruleta;
    private int turnoActual;
    private boolean sentidoReloj;
    private TipoEvento estadoPendienteRuleta = null;

    public Tablero(Mazo mazo, Descarte descarte, List<Jugador> jugadores, Ruleta ruleta) {
        this.sentidoReloj = true;
        this.jugadores = jugadores;
        this.turnoActual = 0;
        this.mazo = mazo;
        this.descarte = descarte;
        this.ruleta = ruleta;
    }

    public Object ejecutarJugada(CartaDTO cartaDto) {
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

            return new ResultadoJugadaDTO(true, eventoFinal, ganador, generarEstadoDTO(), obtenerCartaCimaDTO(),getJugadorActual().getIdJugador());
        }

        return new ResultadoJugadaDTO(false, TipoEvento.ERROR, null, generarEstadoDTO(), obtenerCartaCimaDTO(),getJugadorActual().getIdJugador());
    }

    private boolean ejecutarLogicaJugada(Carta cartaReal, CartaDTO cartaDto) {
        if (cartaReal == null) return false;
        if (cartaReal.esComodin()) {
            System.out.println("[Tablero] Aplicando color elegido al comodín: " + cartaDto.getColor());
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
        Valor accion = carta.getValor();
        switch (accion) {
            case REVERSA:
                cambiarSentido();
                if (jugadores.size() == 2) siguienteTurno();
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
        for (int i = 0; i < cantidad; i++) darCartaAJugador(victima); 
        siguienteTurno();
    }

    public void darCartaAJugador(Jugador j) {
        if (!mazo.estaVacio()) {
            Carta c = mazo.tomarUnaCarta();
            if (c != null) j.agregarCarta(c);
        }
    }

    public void siguienteTurno() {
        if (jugadores.isEmpty()) return;
        int size = jugadores.size();
        turnoActual = sentidoReloj 
            ? (turnoActual + 1) % size 
            : (turnoActual - 1 + size) % size;
    }

    public void cambiarSentido() {
        sentidoReloj = !sentidoReloj;
    }

    public Object robarYPasar() {
        darCartaAJugador(getJugadorActual());
        siguienteTurno();
        return new ResultadoJugadaDTO(true, TipoEvento.ROBO_Y_PASO, null, generarEstadoDTO(), obtenerCartaCimaDTO(),getJugadorActual().getIdJugador());
    }

    private JugadorDTO verificarGanador() {
        for (Jugador j : jugadores) {
            if (j.getNumCartas() == 0) return new JugadorDTO(j.getIdJugador(), j.getNombre());
        }
        return null;
    }

    private List<JugadorDTO> generarEstadoDTO() {
        return jugadores.stream().map(j -> {
            List<CartaDTO> manoDto = j.getMano().getCartasReales().stream()
                .map(c -> new CartaDTO(c.getValor(), c.getColor()))
                .collect(Collectors.toList());
            return new JugadorDTO(j.getIdJugador(), j.getNombre(), manoDto, false);
        }).collect(Collectors.toList());
    }

    private CartaDTO obtenerCartaCimaDTO() {
        Carta cima = descarte.getCartaCima();
        return new CartaDTO(cima.getValor(), cima.getColor());
    }

    private Carta buscarCartaEnMano(CartaDTO cartaDto) {
        return getJugadorActual().getMano().getCartasReales().stream()
                .filter(c -> c.getValor() == cartaDto.getValor() && 
                       (c.getColor() == Colores.NEGRO || c.getColor() == cartaDto.getColor()))
                .findFirst().orElse(null);
    }

    public Object procesarGritoUno(JugadorDTO datosGrito) {
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
        return new ResultadoGritoDTO(exito, evento, idCastigado, generarEstadoDTO());
    }
    
    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }
    public Mazo getMazo() { return mazo; }
    public List<Jugador> getJugadores() { return jugadores; }
    public boolean isSentidoReloj() { return sentidoReloj;}
}