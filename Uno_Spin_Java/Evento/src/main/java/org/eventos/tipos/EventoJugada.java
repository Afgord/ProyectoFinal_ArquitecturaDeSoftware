/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.tipos;

import org.eventos.comun.Evento;

public class EventoJugada extends Evento {
    private final String color;
    private final String simbolo;
    private Object cartaObjeto;
    private String colorNombre;

    public EventoJugada(String color, String simbolo) {
        this.color = color;
        this.simbolo = simbolo;
    }

    public String getColor() { return color; }
    public String getSimbolo() { return simbolo; }
    public Object getCartaObjeto() { return cartaObjeto; }
    public void setCartaObjeto(Object cartaObjeto) { this.cartaObjeto = cartaObjeto; }
    public String getColorNombre() { return colorNombre; }
}
