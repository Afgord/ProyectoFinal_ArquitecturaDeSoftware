/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.Dominio;

import Ejercer_Turno.Interfaces.IFachadaDominio;
import java.awt.Color;
/**
 * 
 * @author lagar
 */
public class FachadaJuego implements IFachadaDominio {
    private Tablero tablero;
    private int acumulacionCastigo = 0;

    public void inyectarTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    @Override
    public boolean validarYPlay(Carta carta) {
        if (acumulacionCastigo > 0 && !carta.getSimbolo().equals("+2") && !carta.getSimbolo().equals("+4")) {
            return false;
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
        if (simbolo.equals("+2")) acumulacionCastigo += 2;
        else if (simbolo.equals("+4")) acumulacionCastigo += 4;
        else if (simbolo.equals("REV")) tablero.cambiarSentido();
        else if (simbolo.equals("PRO")) tablero.avanzarTurno();
    }

    @Override
    public void aplicarEfectoCarta(Carta carta, Color colorElegido) {
        if (carta.esComodin() && colorElegido != null) {
            carta.setColorExterno(colorElegido);
            String nombreColor = traducirColorANombre(colorElegido);
            carta.setColorNombre(nombreColor); 
            tablero.getDescarte().setColorActivo(nombreColor);
        }
    }

    private String traducirColorANombre(Color c) {
        Mazo m = tablero.getMazo();
        if (c.equals(m.getcAzul())) return "azul";
        if (c.equals(m.getcRojo())) return "rojo";
        if (c.equals(m.getcVerde())) return "verde";
        if (c.equals(m.getcAmarillo())) return "amarillo";
        return "negro";
    }

    @Override
    public void robarCarta() {
        Carta c = tablero.getMazo().tomarUnaCarta();
        if (c != null) tablero.getJugadorActual().agregarCarta(c);
    }

    @Override
    public void pasarTurno() {
        tablero.avanzarTurno();
    }

    @Override
    public Tablero getTablero() {
        return this.tablero;
    }

    public int getAcumulacionCastigo() { return acumulacionCastigo; }
    public void limpiarCastigo() { this.acumulacionCastigo = 0; }

    @Override
    public void inicializarPartida(int num) {
    }
    
}