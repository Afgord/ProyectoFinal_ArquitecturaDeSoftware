/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Dominio.*;
import Ejercer_Turno.Interfaces.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ModeloJuego implements IModeloAcciones, IModeloDatos {
    private final IFachadaDominio fachada;
    private final List<Observador> observadores = new ArrayList<>();
    private boolean ultimaJugadaValida = true;

    public ModeloJuego(List<Jugador> jugadores, Mazo mazo, Descarte descarte, Tablero tablero) {
        this.fachada = new FachadaJuego();
        this.fachada.inyectarTablero(tablero);
    }
    
    @Override
    public void tirarCarta(Carta carta) {
        if (fachada.validarYPlay(carta)) {
            ultimaJugadaValida = true; 
            if (fachada.getTablero().getJugadorActual().getNumCartas() != 0) {
                fachada.pasarTurno();
            }
        } else {
            ultimaJugadaValida = false; 
        }
        notificarObservadores();
    }
    
    @Override
    public boolean isUltimaJugadaValida() {
        return ultimaJugadaValida;
    }

    @Override
    public void tirarCartaNegra(Carta carta, Color nuevoColor, String nombreColor) {
        if (fachada.validarYPlay(carta)) {
            fachada.aplicarEfectoCarta(carta, nuevoColor);
            fachada.pasarTurno();
            notificarObservadores();
        } else {
            notificarObservadores();
        }
    }

    @Override
    public void robarCarta() {
        if (fachada.getAcumulacionCastigo() > 0) {
            aplicarCastigo();
        } else {
            fachada.robarCarta();
            notificarObservadores();
        }
    }

    @Override
    public void aplicarCastigo() {
        int cantidad = fachada.getAcumulacionCastigo();
        fachada.limpiarCastigo();
        for (int i = 0; i < cantidad; i++) {
            fachada.robarCarta();
        }
        fachada.pasarTurno();
        notificarObservadores();
    }

    @Override public Tablero getTablero() { return fachada.getTablero(); }
    @Override public Mazo getMazo() { return fachada.getTablero().getMazo(); }
    @Override public Descarte getDescarte() { return fachada.getTablero().getDescarte(); }
    @Override public List<Jugador> getJugadores() { return fachada.getTablero().getJugadores(); }

    @Override public void registrarObservador(Observador o) { observadores.add(o); }
    
    public void notificarObservadores() { 
        for (Observador o : observadores) {
            o.notificarCambio(this);
        }
    }
    
    public Color[] obtenerColoresConfigurados() {
        Mazo m = fachada.getTablero().getMazo();
        return new Color[]{ m.getcAzul(), m.getcRojo(), m.getcAmarillo(), m.getcVerde() };
    }
}