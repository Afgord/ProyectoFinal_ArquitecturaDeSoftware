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
    private final boolean sentidoHorario;
    private final String nombreJugadorActual;

    public TableroDTO(Color colorActual, boolean sentidoHorario, String nombreJugadorActual) {
        this.colorActual = colorActual;
        this.sentidoHorario = sentidoHorario;
        this.nombreJugadorActual = nombreJugadorActual;
    }

    public Color getColorActual() { return colorActual; }
    public boolean isSentidoHorario() { return sentidoHorario; }
    public String getNombreJugadorActual() { return nombreJugadorActual; }
}