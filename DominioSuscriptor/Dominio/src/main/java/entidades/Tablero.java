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
 * 
 * @author lagar
 */
public class Tablero {
    private final Mazo mazo;
    private final Descarte descarte;
    private final List<Jugador> jugadores;
    private Ruleta ruleta;
    private int turnoActual;
    private boolean sentidoReloj;

    public Tablero(Mazo mazo, Descarte descarte, List<Jugador> jugadores, Ruleta ruleta) {
        this.sentidoReloj = true;
        this.jugadores = jugadores;
        this.turnoActual = 0;

        System.out.println("[Tablero] Inicializando juego...");
        System.out.println("[Tablero] Jugadores: " + jugadores.size());

        this.mazo = mazo;

        this.descarte = descarte;
        this.ruleta = ruleta;

        System.out.println("[Tablero] Turno inicial: " + getJugadorActual().getNombre());
    }

    public Object ejecutarJugadaConRetorno(CartaDTO cartaDto) {
        if (ejecutarLogicaJugada(buscarCartaEnMano(cartaDto), cartaDto)) {
            JugadorDTO ganador = verificarGanador();
            String eventoFinal = (estadoPendienteRuleta != null) ? estadoPendienteRuleta : "NORMAL";
            if (ganador != null) eventoFinal = "GANADOR";

            return new ResultadoJugadaDTO(true, eventoFinal, ganador, generarEstadoDTO(), obtenerCartaCimaDTO());
        }
        return new ResultadoJugadaDTO(false, "ERROR", null, generarEstadoDTO(), obtenerCartaCimaDTO());
    }
    
    private String estadoPendienteRuleta = null;

    private boolean ejecutarLogicaJugada(Carta cartaReal, CartaDTO datos) {
        if (cartaReal == null) return false;
        if (cartaReal.esComodin()) {
            cartaReal.setColor(datos.getColor());
        }

        if (descarte.recibirCarta(cartaReal)) {
            getJugadorActual().tirarCarta(cartaReal);

            if (cartaReal.esSpin()) {
                siguienteTurno();
                this.estadoPendienteRuleta = ruleta.girarYAplicar(this);
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
        switch (accion) {
            case REVERSA:
                cambiarSentido();
                if (getJugadores().size() == 2) {
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

        System.out.println("[Tablero] Castigando a: " + victima.getNombre());

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
            System.out.println("[Tablero] El mazo está vacío, " + j.getNombre() + " no recibe carta.");
        }
    }
    
    public void siguienteTurno() {
        if (jugadores.isEmpty()) return;

        int size = jugadores.size();
        int turnoAnterior = turnoActual;

        turnoActual = sentidoReloj 
            ? (turnoActual + 1) % size 
            : (turnoActual - 1 + size) % size;

        System.out.println("[Tablero] Cambio de turno: " 
            + jugadores.get(turnoAnterior).getNombre() 
            + " -> " 
            + getJugadorActual().getNombre());
    }

    public void cambiarSentido() {
        sentidoReloj = !sentidoReloj;

        System.out.println("[Tablero] Sentido cambiado: " 
            + (sentidoReloj ? "Horario" : "Antihorario"));
    }
    
    public Object robarYPasarConRetorno() {
        Jugador actual = getJugadorActual();
        darCartaAJugador(actual);
        siguienteTurno();
        return new ResultadoJugadaDTO(true, "ROBO_Y_PASO", null, generarEstadoDTO(), obtenerCartaCimaDTO());
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
        Jugador actual = getJugadorActual();
        String idCastigado = null;
        String mensaje = "GRITO_INVALIDO";
        boolean exito = false;
        if (datosGrito.idJugador().equals(actual.getIdJugador())) {
            if (actual.getNumCartas() == 1) {
                System.out.println("[Tablero] " + actual.getNombre() + " se ha salvado.");
                mensaje = "SE_SALVO";
                exito = true;
            }
        } 
        else {
            if (actual.getNumCartas() == 1) {
                System.out.println("[Tablero] ¡Atrapado! " + actual.getNombre() + " recibe +2.");
                darCartaAJugador(actual);
                darCartaAJugador(actual);
                idCastigado = actual.getIdJugador();
                mensaje = "ATRAPADO";
                exito = true;
            }
        }
        return new ResultadoGritoDTO(exito, mensaje, idCastigado, generarEstadoDTO());
    }
    
    public Object procesarEleccionRuleta(String idJugador, CartaDTO cartaElegida) {
        Jugador j = jugadores.stream()
                    .filter(jug -> jug.getIdJugador().equals(idJugador))
                    .findFirst().orElse(null);

        if (j != null) {
            Carta cartaReal = j.getMano().getCartasReales().stream()
                    .filter(c -> c.getValor() == cartaElegida.getValor() && c.getColor() == cartaElegida.getColor())
                    .findFirst().orElse(null);

            if (cartaReal != null) {
                j.getMano().getCartasReales().remove(cartaReal);
                return new ResultadoJugadaDTO(true, "ACCION_COMPLETADA", verificarGanador(), generarEstadoDTO(), obtenerCartaCimaDTO());
            }
        }
        return new ResultadoJugadaDTO(false, "CARTA_INVALIDA", null, generarEstadoDTO(), obtenerCartaCimaDTO());
    }
    
    public Object procesarDescarteEspecial(String idJugador, CartaDTO cartaDto) {
        Jugador j = jugadores.stream()
                     .filter(jug -> jug.getIdJugador().equals(idJugador))
                     .findFirst().orElse(null);

        if (j != null) {
            Carta cartaReal = j.getMano().getCartasReales().stream()
                    .filter(c -> c.getValor() == cartaDto.getValor() && c.getColor() == cartaDto.getColor())
                    .findFirst().orElse(null);

            if (cartaReal != null) {
                j.getMano().getCartasReales().remove(cartaReal);
                return new ResultadoJugadaDTO(true, "DESCARTE_EXITOSO", verificarGanador(), generarEstadoDTO(), obtenerCartaCimaDTO());
            }
        }
        return new ResultadoJugadaDTO(false, "ERROR_DESCARTE", null, generarEstadoDTO(), obtenerCartaCimaDTO());
    }
    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }
    public Mazo getMazo() { return mazo; }
    public List<Jugador> getJugadores() { return jugadores; }
    public boolean isSentidoReloj() { return sentidoReloj;}
}