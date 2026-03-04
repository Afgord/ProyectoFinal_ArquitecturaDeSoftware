/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private Mazo mazo;
    private Descarte descarte; 
    private List<Jugador> jugadores;
    private int turnoActual;
    private boolean sentidoReloj = true;
    private List<ITableroObserver> observers = new ArrayList<>();

    public Tablero(List<Jugador> jugadores, int rangoInicio, int rangoFinal, 
                   boolean masDos, boolean prohibido, boolean reversa, 
                   boolean masCuatro, boolean cambioColor, 
                   Color cAzul, Color cRojo, Color cAmarillo, Color cVerde, Color cNegro) {
        
        this.jugadores = jugadores;
        this.turnoActual = 0;
        this.mazo = new Mazo(rangoInicio, rangoFinal, masDos, prohibido, reversa, 
                             masCuatro, cambioColor, cAzul, cRojo, cAmarillo, cVerde, cNegro);
        
        // Inicializamos el descarte con la primera carta del mazo
        this.descarte = new Descarte(mazo.tomarUnaCarta());
    }

    public void avanzarTurno() {
        if (sentidoReloj) {
            turnoActual = (turnoActual + 1) % jugadores.size();
        } else {
            turnoActual = (turnoActual - 1 + jugadores.size()) % jugadores.size();
        }
        notificarTurno();
    }

    public void addObserver(ITableroObserver obs) { observers.add(obs); }
    
    public Mazo getMazo() { return mazo; }
    public Descarte getDescarte() { return descarte; } // Importante para el PanelTablero
    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }
    
    private void notificarTurno() {
        for (ITableroObserver obs : observers) {
            obs.actualizar();
            obs.cambiarTurno();
        }
    }  
}