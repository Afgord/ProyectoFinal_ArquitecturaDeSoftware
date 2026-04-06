/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachadas;

import entidades.Carta;
import entidades.Jugador;
import entidades.Mazo;
import entidades.Tablero;
import java.util.List;

/**
 * * @author lagar
 */
public class FachadaJuego implements FachadaDominio {

    private Tablero tablero;
    private int acumulacionCastigo = 0;

    @Override
    public void inyectarTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public void abandonarPartida(Jugador jugador) {
        if (tablero != null) {
            tablero.eliminarJugador(jugador);
        }
    }

    public Jugador verificarGanador() {
        if (tablero != null && tablero.getJugadores().size() == 1) {
            return tablero.getJugadores().get(0);
        }
        return null;
    }

    @Override
    public boolean validarYPlay(Carta carta) {
        if (acumulacionCastigo > 0) {
            if (!carta.getSimbolo().equals("+2") && !carta.getSimbolo().equals("+4")) {
                return false;
            }
        }

        if (tablero.getDescarte().validarJugada(carta)) {
            tablero.getJugadorActual().tirarCarta(carta);
            tablero.getDescarte().recibirCarta(carta);
            procesarEfectos(carta);
            return true;
        }
        return false;
    }

    private void procesarEfectos(Carta carta) {
        String simbolo = carta.getSimbolo();
        if (simbolo.equals("+2")) {
            acumulacionCastigo += 2;
        } else if (simbolo.equals("+4")) {
            acumulacionCastigo += 4;
        } else if (simbolo.equals("REV")) {
            tablero.cambiarSentido();
        } else if (simbolo.equals("PRO")) {
            tablero.avanzarTurno();
        }
    }

    @Override
    public void aplicarEfectoCarta(Carta carta, String nombreColorElegido) {
        if (carta.esComodin() && nombreColorElegido != null) {
            carta.setColorNombre(nombreColorElegido);
            tablero.getDescarte().setColorActivo(nombreColorElegido);
        }
    }

    @Override
    public void robarCarta() {
        if (acumulacionCastigo > 0) {
            for (int i = 0; i < acumulacionCastigo; i++) {
                Carta c = tablero.getMazo().tomarUnaCarta();
                if (c != null) {
                    tablero.getJugadorActual().agregarCarta(c);
                }
            }
            limpiarCastigo();
            pasarTurno();
        } else {
            Carta c = tablero.getMazo().tomarUnaCarta();
            if (c != null) {
                tablero.getJugadorActual().agregarCarta(c);
            }
        }
    }

    @Override
    public void pasarTurno() {
        tablero.avanzarTurno();
    }

    @Override
    public Tablero getTablero() {
        return this.tablero;
    }

    @Override
    public int getAcumulacionCastigo() {
        return acumulacionCastigo;
    }

    @Override
    public void limpiarCastigo() {
        this.acumulacionCastigo = 0;
    }

    @Override
    public void inicializarPartida(int num) {
    }
}