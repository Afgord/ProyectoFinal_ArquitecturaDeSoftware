/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uno.dto;

import java.awt.Color;
import java.io.Serializable;
/**
 * 
 * @author lagar
 */
public class TableroDTO implements Serializable {
    private final Color colorActual;
    private final boolean sentidoReloj; 
    private final String idJugadorActual;

    public TableroDTO(Color colorActual, boolean sentidoReloj, String idJugadorActual) {
        this.colorActual = colorActual;
        this.sentidoReloj = sentidoReloj;
        this.idJugadorActual = idJugadorActual;
    }

    public Color getColorActual() { return colorActual; }
    public boolean isSentidoReloj() { return sentidoReloj; }
    public String getIdJugadorActual() { return idJugadorActual; }
}
