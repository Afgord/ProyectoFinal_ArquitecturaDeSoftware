/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.Dominio;

import java.awt.Color;
import java.util.List;

public class Tablero {

    private Mazo mazo;
    private Descarte descarte;
    private List<Jugador> jugadores;
    private int turnoActual;
    private boolean sentidoReloj = true;

    public Tablero(List<Jugador> jugadores, int rangoInicio, int rangoFinal, 
                   boolean masDos, boolean prohibido, boolean reversa, 
                   boolean masCuatro, boolean cambioColor, 
                   Color cAzul, Color cRojo, Color cAmarillo, Color cVerde, Color cNegro) {
        
        this.jugadores = jugadores;
        this.turnoActual = 0;
        this.mazo = new Mazo(rangoInicio, rangoFinal, masDos, prohibido, reversa, 
                             masCuatro, cambioColor, cAzul, cRojo, cAmarillo, cVerde, cNegro);
        
        Carta inicial = mazo.sacarCartaInicialValida();
        this.descarte = new Descarte(inicial);
    }

    public void siguienteTurno() {
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

    public Mazo getMazo() { return mazo; }
    public Descarte getDescarte() { return descarte; }
    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }
    public List<Jugador> getJugadores() { return jugadores; }
    public int getIndiceTurnoActual() { return turnoActual; }
}
