/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachadas;

import entidades.Carta;
import entidades.Colores;
import entidades.Jugador;
import entidades.Tablero;

/**
 * 
 * @author lagar
 */
public class FachadaJuego implements FachadaDominio {
    private Tablero tablero;

    public FachadaJuego(Tablero tablero) {
        this.tablero = tablero;
    }

    @Override
    public boolean validarYPlay(Carta carta) {
        return tablero.ejecutarJugada(carta);
    }

    @Override
    public void robarCarta() {
        tablero.realizarRobo();
    }

    @Override
    public void cambiarColorDescarte(Colores color) {
        tablero.getDescarte().setColorActivo(color);
    }

    @Override
    public Jugador verificarGanador() {
        return tablero.obtenerGanador();
    }

    @Override
    public void pasarTurno() {
        tablero.siguienteTurno();
    }

    @Override
    public Tablero getTablero() { return this.tablero; }
}