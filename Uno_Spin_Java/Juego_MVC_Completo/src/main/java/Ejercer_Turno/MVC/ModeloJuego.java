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
import javax.swing.SwingUtilities;

public class ModeloJuego implements IModeloAcciones, IModeloDatos {
    private final Tablero tablero;
    private final Mazo mazo;
    private final Descarte descarte;
    private final List<Jugador> jugadores;
    private final List<Observador> observadores = new ArrayList<>();
    private int acumulacionCastigo = 0;

    public ModeloJuego(List<Jugador> jugadores, Mazo mazo, Descarte descarte, Tablero tablero) {
        this.jugadores = jugadores;
        this.mazo = mazo;
        this.descarte = descarte;
        this.tablero = tablero;
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
            notificarObservadores(ContextoEvento.ALERTA_SONIDO_TIRAR);

            String simbolo = carta.getSimbolo();
            
            if (simbolo.equals("+2")) {
                acumulacionCastigo += 2;
                pasarTurno();
            } else if (simbolo.equals("REV")) {
                tablero.cambiarSentido();
                // Si solo son 2 jugadores, Reversa funciona como un Prohibido
                if (jugadores.size() == 2) {
                    tablero.avanzarTurno();
                }
                verificarEstadoVictoria();
            } else if (simbolo.equals("PRO")) {
                tablero.avanzarTurno(); // Salta al siguiente
                verificarEstadoVictoria(); // Luego el flujo normal pasa al que sigue del saltado
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
                notificarObservadores(ContextoEvento.ALERTA_SONIDO_JALAR);
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
                        notificarObservadores(ContextoEvento.ALERTA_SONIDO_JALAR);
                        notificarObservadores(ContextoEvento.MAZO_ACTUALIZADO);
                        notificarObservadores(ContextoEvento.MANO_JUGADOR_ACTUALIZADO);
                    });
                    try { Thread.sleep(500); } catch (InterruptedException e) { break; }
                }
            }
            SwingUtilities.invokeLater(this::pasarTurno);
        }).start();
    }

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
    @Override public void gritarUno() { notificarObservadores(ContextoEvento.ALERTA_SONIDO_UNO); }
    @Override public void notificarError() { notificarObservadores(ContextoEvento.ALERTA_SONIDO_ERROR); }
}