/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Dominio.*;
import Ejercer_Turno.Interfaces.*;
import audio.AudioModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

public class ModeloJuego implements IModeloAcciones, IModeloDatos {
    private final Tablero tablero;
    private final Mazo mazo;
    private final Descarte descarte;
    private final List<Jugador> jugadores;
    private final List<Observador> observadores = new ArrayList<>();
    private final AudioModel audio;
    private int acumulacionCastigo = 0;

    public ModeloJuego(List<Jugador> jugadores, Mazo mazo, Descarte descarte, Tablero tablero, AudioModel audioModel) {
        this.jugadores = jugadores;
        this.mazo = mazo;
        this.descarte = descarte;
        this.tablero = tablero;
        this.audio = audioModel;
    }

    @Override
    public void registrarObservador(Observador o) { observadores.add(o); }

    public void notificarObservadores(ContextoEvento evento) {
        for (Observador o : observadores) o.notificarCambio(evento);
    }

    @Override
    public void tirarCarta(Carta carta) {
        if (acumulacionCastigo > 0 && !carta.getSimbolo().equals("+2")) {
            notificarError();
            return;
        }

        if (descarte.validarJugada(carta)) {
            tablero.getJugadorActual().tirarCarta(carta);
            descarte.recibirCarta(carta);
            audio.playEffect("tirar");

            notificarObservadores(ContextoEvento.DESCARTE_ACTUALIZADO);
            notificarObservadores(ContextoEvento.MANO_JUGADOR_ACTUALIZADO);

            String simbolo = carta.getSimbolo();
            if (simbolo.equals("+2")) {
                acumulacionCastigo += 2;
                pasarTurno();
            } else if (simbolo.equals("REV")) {
                tablero.cambiarSentido();
                if (jugadores.size() == 2) tablero.avanzarTurno();
                verificarEstadoVictoria();
            } else if (simbolo.equals("PRO")) {
                tablero.avanzarTurno();
                verificarEstadoVictoria();
            } else {
                verificarEstadoVictoria();
            }
        } else {
            notificarError();
        }
    }

    @Override
    public void tirarCartaNegra(Carta carta, Color nuevoColor, String nombreColor) {
        if (acumulacionCastigo > 0 && !carta.getSimbolo().equals("+4")) {
            notificarError();
            return;
        }

        carta.setColorExterno(nuevoColor);
        carta.setColorNombre(nombreColor); 
        descarte.recibirCarta(carta);
        tablero.getJugadorActual().getCartasModelo().remove(carta);
        audio.playEffect("tirar");
        
        if (carta.getSimbolo().equals("+4")) {
            acumulacionCastigo += 4;
        }
        pasarTurno();
    }

    @Override
    public void robarCarta() {
        if (acumulacionCastigo > 0) {
            aplicarCastigo();
        } else {
            if (!mazo.estaVacio()) {
                tablero.getJugadorActual().agregarCarta(mazo.tomarUnaCarta());
                audio.playEffect("jalar");
                notificarObservadores(ContextoEvento.MAZO_ACTUALIZADO);
                notificarObservadores(ContextoEvento.MANO_JUGADOR_ACTUALIZADO);
            }
        }
    }

    @Override
    public void aplicarCastigo() {
        int cantidadFinal = acumulacionCastigo;
        acumulacionCastigo = 0;
        new Thread(() -> {
            Jugador victima = tablero.getJugadorActual();
            for (int i = 0; i < cantidadFinal; i++) {
                Carta robada = mazo.tomarUnaCarta();
                if (robada != null) {
                    victima.agregarCarta(robada);
                    SwingUtilities.invokeLater(() -> {
                        audio.playEffect("jalar");
                        notificarObservadores(ContextoEvento.MAZO_ACTUALIZADO);
                        notificarObservadores(ContextoEvento.MANO_JUGADOR_ACTUALIZADO);
                    });
                    try { Thread.sleep(500); } catch (InterruptedException e) { break; }
                }
            }
            SwingUtilities.invokeLater(this::pasarTurno);
        }).start();
    }

    public void reproducirMusica() { audio.playMusic(); }
    public void detenerMusica() { audio.stopMusic(); }
    public void reproducirEfecto(String nombre) { audio.playEffect(nombre); }

    public void pasarTurno() {
        tablero.avanzarTurno();
        notificarObservadores(ContextoEvento.TURNO_CAMBIADO);
        notificarObservadores(ContextoEvento.DESCARTE_ACTUALIZADO);
        notificarObservadores(ContextoEvento.MANO_JUGADOR_ACTUALIZADO);
    }

    private void verificarEstadoVictoria() {
        if (tablero.getJugadorActual().getNumCartas() == 0) {
            notificarObservadores(ContextoEvento.FIN_JUEGO);
        } else {
            pasarTurno();
        }
    }

    @Override public Tablero getTablero() { return tablero; }
    @Override public Mazo getMazo() { return mazo; }
    @Override public Descarte getDescarte() { return descarte; }
    @Override public List<Jugador> getJugadores() { return jugadores; }
    @Override public void gritarUno() { audio.playEffect("uno"); }
    @Override public void notificarError() { audio.playEffect("alerta"); }
}