/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Dominio.*;
import Ejercer_Turno.Interfaces.*;
import audio.AudioManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
/**
 * 
 * @author lagar
 */
public class ModeloJuego implements IModeloAcciones, IModeloDatos {
    private final IFachadaDominio fachada;
    private final List<Observador> observadores = new ArrayList<>();
    private final AudioManager audio;

    public ModeloJuego(List<Jugador> jugadores, Mazo mazo, Descarte descarte, Tablero tablero, AudioManager audioModel) {
        this.audio = audioModel;
        this.fachada = new FachadaJuego();
        this.fachada.inyectarTablero(tablero);
    }
    
    @Override
    public void tirarCarta(Carta carta) {
        if (fachada.validarYPlay(carta)) {
            reproducirEfecto("tirar");
            if (fachada.getTablero().getJugadorActual().getNumCartas() == 0) {
                notificarObservadores(); 
            } else {
                fachada.pasarTurno();
                notificarObservadores();
            }
        } else {
            notificarError();
        }
    }

    @Override
    public void tirarCartaNegra(Carta carta, Color nuevoColor, String nombreColor) {
        if (fachada.validarYPlay(carta)) {
            fachada.aplicarEfectoCarta(carta, nuevoColor);
            reproducirEfecto("tirar");
            fachada.pasarTurno();
            notificarObservadores();
        } else {
            notificarError();
        }
    }

    @Override
    public void robarCarta() {
        if (fachada.getAcumulacionCastigo() > 0) {
            aplicarCastigo();
        } else {
            fachada.robarCarta();
            reproducirEfecto("jalar");
            notificarObservadores();
        }
    }

    @Override
    public void aplicarCastigo() {
        int cantidad = fachada.getAcumulacionCastigo();
        fachada.limpiarCastigo();

        new Thread(() -> {
            for (int i = 0; i < cantidad; i++) {
                fachada.robarCarta();
                SwingUtilities.invokeLater(() -> {
                    reproducirEfecto("jalar");
                    notificarObservadores();
                });
                try { Thread.sleep(400); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            SwingUtilities.invokeLater(() -> {
                fachada.pasarTurno();
                notificarObservadores();
            });
        }).start();
    }
    
    public void reproducirMusica() {
        if (audio != null) audio.playMusicLoop();
    }

    public void detenerMusica() {
        if (audio != null) audio.stopMusic();
    }

    public void reproducirEfecto(String nombre) {
       if (audio != null) audio.playEffect(nombre);
    }

    @Override public Tablero getTablero() { return fachada.getTablero(); }
    @Override public Mazo getMazo() { return fachada.getTablero().getMazo(); }
    @Override public Descarte getDescarte() { return fachada.getTablero().getDescarte(); }
    @Override public List<Jugador> getJugadores() { return fachada.getTablero().getJugadores(); }

    @Override public void registrarObservador(Observador o) { observadores.add(o); }
    public void notificarObservadores() { for (Observador o : observadores) o.notificarCambio(this); }
    
    @Override public void gritarUno() { reproducirEfecto("uno"); }
    @Override public void notificarError() { reproducirEfecto("alerta"); }
    
    public Color[] obtenerColoresConfigurados() {
        Mazo m = fachada.getTablero().getMazo();
        return new Color[]{
            m.getcAzul(),
            m.getcRojo(),
            m.getcAmarillo(),
            m.getcVerde()
        };
}
}