/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import dtos.CartaDTO;
import static entidades.Valor.CAMBIOCOLOR;
import static entidades.Valor.MASCUATRO;
import static entidades.Valor.MASDOS;
import static entidades.Valor.PROHIBIDO;
import static entidades.Valor.REVERSA;
import java.util.List;
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

        System.out.println("[Tablero] Turno inicial: " + getJugadorActual().getNombre());
    }

    public boolean ejecutarJugada(CartaDTO cartaDto) {
        Carta cartaReal = buscarCartaEnMano(cartaDto);

        if (descarte.validarJugada(cartaReal)) {
            getJugadorActual().tirarCarta(cartaReal);

            if (cartaReal.esComodin()) {
                cartaReal.setColor(cartaDto.getColor());
            }
            descarte.recibirCarta(cartaReal);

            if (cartaReal.esSpin()) {
                siguienteTurno();
                ruleta.girarYAplicar(this);
            } else {
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
    
    public void robarYPasar() {
        Jugador actual = getJugadorActual();
        darCartaAJugador(actual);
        System.out.println("[DOMINIO] Pasando turno tras intento de robo...");
        siguienteTurno();
    }
    
    private Carta buscarCartaEnMano(CartaDTO cartaDto) {
        return getJugadorActual().getMano().getCartasReales()
                .stream()
                .filter(c -> c.getValor() == cartaDto.getValor() && 
                       (c.getColor() == Colores.NEGRO || c.getColor() == cartaDto.getColor()))
                .findFirst()
                .orElse(null);
    }
    
    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }
    public Descarte getDescarte() { return descarte; }
    public Mazo getMazo() { return mazo; }
    public List<Jugador> getJugadores() { return jugadores; }
    public boolean isSentidoReloj() { return sentidoReloj;}
}