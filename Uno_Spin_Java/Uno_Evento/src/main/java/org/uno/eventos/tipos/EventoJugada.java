/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uno.eventos.tipos;

import org.uno.eventos.comun.Evento;
/**
 * 
 * @author lagar
 */
public class EventoJugada extends Evento {
    private final String color;
    private final String simbolo;

    public EventoJugada(String color, String simbolo) {
        this.color = color;
        this.simbolo = simbolo;
    }

    public String getColor() { return color; }
    public String getSimbolo() { return simbolo; }
}
