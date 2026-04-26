/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoGritoDTO;
import dtos.ResultadoJugadaDTO;
import static entidades.Valor.CAMBIOCOLOR;
import static entidades.Valor.MASCUATRO;
import static entidades.Valor.MASDOS;
import static entidades.Valor.PROHIBIDO;
import static entidades.Valor.REVERSA;
import java.util.List;
import java.util.stream.Collectors;

/**
 * * @author lagar
 */
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

        System.out.println("[Tablero] Inicializando juego...");
        System.out.println("[Tablero] Jugadores registrados: " + jugadores.size());

        this.mazo = mazo;
        this.descarte = descarte;
        this.ruleta = ruleta;

        System.out.println("[Tablero] Turno inicial: " + getJugadorActual().getNombre());
    }

    public Object ejecutarJugadaConRetorno(CartaDTO cartaDto) {
        System.out.println("[Tablero] Solicitud de jugada: " + cartaDto.getValor() + " " + cartaDto.getColor());

        if (ejecutarLogicaJugada(buscarCartaEnMano(cartaDto), cartaDto)) {
            JugadorDTO ganador = verificarGanador();
            TipoEvento eventoFinal = TipoEvento.DESCARTE_EXITOSO;
            if (estadoPendienteRuleta != null) {
                eventoFinal = estadoPendienteRuleta;
            }
            if (ganador != null) {
                eventoFinal = TipoEvento.GANADOR;
            }
            String mensajeExtra = (ruleta.getDetalleUltimoEfecto() != null) ? ruleta.getDetalleUltimoEfecto() : "";
            return new ResultadoJugadaDTO(true, eventoFinal, ganador, generarEstadoDTO(), obtenerCartaCimaDTO(), mensajeExtra);
        }

        System.out.println("[Tablero] Jugada rechazada por el motor de reglas.");
        return new ResultadoJugadaDTO(false, TipoEvento.ERROR, null, generarEstadoDTO(), obtenerCartaCimaDTO(), "Jugada inválida");
    }

    private boolean ejecutarLogicaJugada(Carta cartaReal, CartaDTO datos) {
    if (cartaReal == null) {
        System.out.println("[Tablero] Error: El jugador no posee la carta solicitada.");
        return false;
    }

    if (cartaReal.esComodin()) {
        System.out.println("[Tablero] Comodín detectado. Cambiando color a: " + datos.getColor());
        cartaReal.setColor(datos.getColor());
    }

    if (descarte.recibirCarta(cartaReal)) {
        System.out.println("[Tablero] Carta aceptada en descarte: " + cartaReal.getValor());
        getJugadorActual().tirarCarta(cartaReal);
        if (cartaReal.esSpin()) {
            System.out.println("[Tablero] ¡Efecto SPIN! Pasando turno antes de girar...");
            siguienteTurno();
            this.estadoPendienteRuleta = ruleta.girarYAplicar(this);
            String detalleExtra = ruleta.getDetalleUltimoEfecto();
            System.out.println("[Tablero] Resultado Ruleta: " + estadoPendienteRuleta + " (Detalle: " + detalleExtra + ")");
        } else {
            this.estadoPendienteRuleta = null; 
            ejecutarEfecto(cartaReal);
        }
        return true;
    }
    return false;
}

    public void ejecutarEfecto(Carta carta) {
        Valor accion = carta.getValor();
        System.out.println("[Tablero] Ejecutando efecto de carta: " + accion);
        switch (accion) {
            case REVERSA:
                cambiarSentido();
                if (getJugadores().size() == 2) {
                    siguienteTurno();
                }
                siguienteTurno();
                break;
            case PROHIBIDO:
                System.out.println("[Tablero] Saltando turno del siguiente jugador.");
                siguienteTurno(); 
                siguienteTurno();
                break;
            case MASDOS:
                castigarSiguiente(2);
                break;
            case MASCUATRO:
                castigarSiguiente(4);
                break;
            case CAMBIOCOLOR:
                siguienteTurno();
                break;
            default:
                siguienteTurno();
                break;
        }
    }

    private void castigarSiguiente(int cantidad) {
        siguienteTurno(); 
        Jugador victima = getJugadorActual();
        System.out.println("[Tablero] Castigando a " + victima.getNombre() + " con " + cantidad + " cartas.");

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
        } else {
            System.out.println("[Tablero] ¡MAZO VACÍO! " + j.getNombre() + " no pudo robar.");
        }
    }

    public void siguienteTurno() {
        if (jugadores.isEmpty()) return;
        int size = jugadores.size();
        int turnoAnterior = turnoActual;

        turnoActual = sentidoReloj 
            ? (turnoActual + 1) % size 
            : (turnoActual - 1 + size) % size;

        System.out.println("[Tablero] Cambio de Turno: [" + jugadores.get(turnoAnterior).getNombre() 
                         + "] -> [" + getJugadorActual().getNombre() + "]");
    }

    public void cambiarSentido() {
        sentidoReloj = !sentidoReloj;
        System.out.println("[Tablero] Sentido de juego cambiado. ¿Horario?: " + sentidoReloj);
    }

    public Object robarYPasarConRetorno() {
        System.out.println("[Tablero] " + getJugadorActual().getNombre() + " decide robar y pasar.");
        darCartaAJugador(getJugadorActual());
        siguienteTurno();
        return new ResultadoJugadaDTO(true, TipoEvento.ROBO_Y_PASO, null, generarEstadoDTO(), obtenerCartaCimaDTO());
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
        return getJugadorActual().getMano().getCartasReales()
                .stream()
                .filter(c -> c.getValor() == cartaDto.getValor() && 
                       (c.getColor() == Colores.NEGRO || c.getColor() == cartaDto.getColor()))
                .findFirst()
                .orElse(null);
    }

    public Object procesarGritoUno(JugadorDTO datosGrito) {
        System.out.println("[Tablero] Procesando grito de UNO para: " + datosGrito.getNombre());
        Jugador actual = getJugadorActual();
        String idCastigado = null;
        TipoEvento mensaje = TipoEvento.GRITO_INVALIDO;
        boolean exito = false;

        if (datosGrito.idJugador().equals(actual.getIdJugador())) {
            if (actual.getNumCartas() == 1) {
                System.out.println("[Tablero] Grito legítimo. El jugador está a salvo.");
                mensaje = TipoEvento.SE_SALVO;
                exito = true;
            }
        } else {
            if (actual.getNumCartas() == 1) {
                System.out.println("[Tablero] ¡BOTÓN DE PÁNICO! Atrapado: " + actual.getNombre());
                darCartaAJugador(actual);
                darCartaAJugador(actual);
                idCastigado = actual.getIdJugador();
                mensaje = TipoEvento.ATRAPADO;
                exito = true;
            }
        }
        return new ResultadoGritoDTO(exito, mensaje, idCastigado, generarEstadoDTO());
    }

    public Object procesarDescarteEspecial(String idJugador, CartaDTO cartaDto) {
        System.out.println("[Tablero] Procesando descarte especial (Guerra/Puntos) para: " + idJugador);
        
        Jugador j = jugadores.stream()
                     .filter(jug -> jug.getIdJugador().equals(idJugador))
                     .findFirst().orElse(null);

        if (j != null) {
            Carta cartaReal = j.getMano().getCartasReales().stream()
                    .filter(c -> c.getValor() == cartaDto.getValor() && c.getColor() == cartaDto.getColor())
                    .findFirst().orElse(null);

            if (cartaReal != null) {
                System.out.println("[Tablero] Descarte exitoso: " + cartaReal.getValor() + " removida de " + j.getNombre());
                j.getMano().getCartasReales().remove(cartaReal);
                return new ResultadoJugadaDTO(true, TipoEvento.DESCARTE_EXITOSO, verificarGanador(), generarEstadoDTO(), obtenerCartaCimaDTO());
            }
        }
        System.out.println("[Tablero] Error en descarte especial: Datos no coinciden.");
        return new ResultadoJugadaDTO(false, TipoEvento.ERROR_DESCARTE, null, generarEstadoDTO(), obtenerCartaCimaDTO());
    }
    
    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }
    public Mazo getMazo() { return mazo; }
    public List<Jugador> getJugadores() { return jugadores; }
    public boolean isSentidoReloj() { return sentidoReloj;}
}