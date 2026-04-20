/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

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
        this.mazo = new Mazo(rangoInicio, rangoFinal, masDos, prohibido, reversa, masCuatro, cambioColor);
        this.descarte = new Descarte(mazo.sacarCartaInicialValida());
    }

    public boolean ejecutarJugada(Carta carta) {
        if (descarte.validarJugada(carta)) {
            getJugadorActual().tirarCarta(carta);
            descarte.recibirCarta(carta);
            aplicarEfectos(carta);
            return true;
        }
        return false;
    }

    private void aplicarEfectos(Carta carta) {
        switch (carta.getValor()) {
            case MASDOS: castigarSiguiente(2); break;
            case MASCUATRO: castigarSiguiente(4); break;
            case REVERSA: cambiarSentido(); break;
            case PROHIBIDO: siguienteTurno(); break;
            default: break;
        }
    }

    private void castigarSiguiente(int cantidad) {
        siguienteTurno();
        Jugador victima = getJugadorActual();
        for (int i = 0; i < cantidad; i++) {
            Carta c = mazo.tomarUnaCarta();
            if (c != null) victima.agregarCarta(c);
        }
    }

    public void realizarRobo() {
        Carta c = mazo.tomarUnaCarta();
        if (c != null) getJugadorActual().agregarCarta(c);
    }

    public Jugador obtenerGanador() {
        return jugadores.stream()
                .filter(j -> j.getNumCartas() == 0)
                .findFirst().orElse(null);
    }

    public void siguienteTurno() {
        if (jugadores.isEmpty()) return;
        int size = jugadores.size();
        turnoActual = sentidoReloj ? (turnoActual + 1) % size : (turnoActual - 1 + size) % size;
    }

    public void cambiarSentido() { this.sentidoReloj = !this.sentidoReloj; }
    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }
    public Descarte getDescarte() { return descarte; }
    public Mazo getMazo() { return mazo; }
    public List<Jugador> getJugadores() { return jugadores; }
}