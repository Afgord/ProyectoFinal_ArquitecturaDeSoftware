/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.List;

/**
 * * @author lagar
 */
public class Tablero {

    private Mazo mazo;
    private Descarte descarte;
    private List<Jugador> jugadores;
    private int turnoActual;
    private boolean sentidoReloj = true;

    public Tablero(List<Jugador> jugadores, int rangoInicio, int rangoFinal, 
                   boolean masDos, boolean prohibido, boolean reversa, 
                   boolean masCuatro, boolean cambioColor) {
        
        this.jugadores = jugadores;
        this.turnoActual = 0;
        this.mazo = new Mazo(rangoInicio, rangoFinal, masDos, prohibido, reversa, 
                             masCuatro, cambioColor);
        
        Carta inicial = mazo.sacarCartaInicialValida();
        this.descarte = new Descarte(inicial);
    }

    public void eliminarJugador(Jugador jugador) {
        int indiceEliminado = jugadores.indexOf(jugador);
        if (indiceEliminado == -1) return;

        jugadores.remove(indiceEliminado);

        if (jugadores.isEmpty()) return;

        if (turnoActual >= indiceEliminado) {
            if (turnoActual > 0) {
                turnoActual = (turnoActual - 1) % jugadores.size();
            } else {
                turnoActual = 0;
            }
        } else {
            turnoActual = turnoActual % jugadores.size();
        }
    }

    public void siguienteTurno() {
        if (jugadores.isEmpty()) return;
        if (sentidoReloj) {
            turnoActual = (turnoActual + 1) % jugadores.size();
        } else {
            turnoActual = (turnoActual - 1 + jugadores.size()) % jugadores.size();
        }
    }

    public void avanzarTurno() {
        siguienteTurno();
    }

    public void cambiarSentido() {
        this.sentidoReloj = !this.sentidoReloj;
    }
    
    public boolean isSentidoReloj() {
        return sentidoReloj;
    }
    public Mazo getMazo() { return mazo; }
    public Descarte getDescarte() { return descarte; }
    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }
    public List<Jugador> getJugadores() { return jugadores; }
    public int getIndiceTurnoActual() { return turnoActual; }
}