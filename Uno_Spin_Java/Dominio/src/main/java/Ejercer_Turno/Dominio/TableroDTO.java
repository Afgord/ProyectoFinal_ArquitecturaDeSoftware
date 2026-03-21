/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.Dominio;

import java.awt.Color;
/**
 * 
 * @author Luis Rafael
 */
public class TableroDTO {
    private final Color colorActual;
    private final boolean sentidoReloj; 
    private final String nombreJugadorActual;

    public TableroDTO(Color colorActual, boolean sentidoReloj, String nombreJugadorActual) {
        this.colorActual = colorActual;
        this.sentidoReloj = sentidoReloj;
        this.nombreJugadorActual = nombreJugadorActual;
    }

    public Color getColorActual() { return colorActual; }
    public boolean isSentidoReloj() { return sentidoReloj; }
    public String getNombreJugadorActual() { return nombreJugadorActual; }
}