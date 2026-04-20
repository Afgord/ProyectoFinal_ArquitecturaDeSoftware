/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.util.List;

public class Tablero {
    private Mazo mazo;
    private Descarte descarte;
    private List<Jugador> jugadores;
    private int turnoActual;
    private boolean sentidoReloj;

    public Tablero(List<Jugador> jugadores, int rangoInicio, int rangoFinal, 
                   boolean masDos, boolean prohibido, boolean reversa, 
                   boolean masCuatro, boolean cambioColor) {
        this.sentidoReloj = true;
        this.jugadores = jugadores;
        this.turnoActual = 0;

        System.out.println("[Tablero] Inicializando juego...");
        System.out.println("[Tablero] Jugadores: " + jugadores.size());

        this.mazo = new Mazo(rangoInicio, rangoFinal, masDos, prohibido, reversa, masCuatro, cambioColor);
        
        Carta inicial = mazo.sacarCartaInicialValida();
        System.out.println("[Tablero] Carta inicial: " 
            + inicial.getValor() + " de color " + inicial.getColor());

        this.descarte = new Descarte(inicial);

        System.out.println("[Tablero] Turno inicial: " + getJugadorActual().getNombre());
    }

    public boolean ejecutarJugada(CartaDTO cartaDto) {
        Carta cartaReal = getJugadorActual().getMano().getCartasReales()
                .stream()
                .filter(c -> c.getValor() == cartaDto.getValor() && c.getColor() == cartaDto.getColor())
                .findFirst()
                .orElse(null);

        if (cartaReal != null && descarte.validarJugada(cartaReal)) {
            System.out.println("[DOMINIO] Jugada válida desde DTO: " + cartaDto);
            getJugadorActual().tirarCarta(cartaReal);
            descarte.recibirCarta(cartaReal);
            aplicarEfectos(cartaReal);
            return true;
        }
        System.out.println("[DOMINIO] Jugada INVÁLIDA desde DTO");
        return false;
    }

    private void aplicarEfectos(Carta carta) {
        System.out.println("[Tablero] Aplicando efecto de carta: " + carta.getValor());

        switch (carta.getValor()) {
            case MASDOS:
                System.out.println("[Tablero] Efecto +2");
                castigarSiguiente(2);
                break;

            case MASCUATRO:
                System.out.println("[Tablero] Efecto +4");
                castigarSiguiente(4);
                break;

            case REVERSA:
                System.out.println("[Tablero] Efecto REVERSA");
                cambiarSentido();
                break;

            case PROHIBIDO:
                System.out.println("[Tablero] Efecto PROHIBIDO (salta turno)");
                siguienteTurno();
                break;

            default:
                System.out.println("[Tablero] Sin efecto especial");
                siguienteTurno();
                break;
        }
    }

    private void castigarSiguiente(int cantidad) {
        System.out.println("[Tablero] Castigando al siguiente jugador con " + cantidad + " cartas");

        siguienteTurno();
        Jugador victima = getJugadorActual();

        System.out.println("[Tablero] Jugador castigado: " + victima.getNombre());

        for (int i = 0; i < cantidad; i++) {
            Carta c = mazo.tomarUnaCarta();
            if (c != null) {
                victima.agregarCarta(c);
            }
        }

        System.out.println("[Tablero] Castigo completado");
    }


    public JugadorDTO obtenerGanadorDTO() {
        Jugador ganador = jugadores.stream()
                .filter(j -> j.getNumCartas() == 0)
                .findFirst().orElse(null);

        if (ganador != null) {
            return new JugadorDTO(ganador.getNombre(), new java.util.ArrayList<>());
        }
        return null;
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
        Carta c = mazo.tomarUnaCarta();
        
        if (c != null) {
            actual.agregarCarta(c);
            System.out.println("[DOMINIO] " + actual.getNombre() + " no pudo jugar. Robó: " + c);
        } else {
            System.out.println("[DOMINIO] El mazo está vacío.");
        }
        
        System.out.println("[DOMINIO] Pasando turno automáticamente...");
        siguienteTurno();
    }

    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }
    public Descarte getDescarte() { return descarte; }
    public Mazo getMazo() { return mazo; }
    public List<Jugador> getJugadores() { return jugadores; }
}