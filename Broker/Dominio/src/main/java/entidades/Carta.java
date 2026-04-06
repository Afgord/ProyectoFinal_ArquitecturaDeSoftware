/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.awt.Color;
/**
 * 
 * @author lagar
 */
public class Carta {
    private final String simbolo;
    private Color colorExterno;
    private String colorInterno;
    private final String rutaImagen;
    private boolean lado;

    public Carta(String simbolo, Color colorExterno, String colorInterno, String rutaImagen) {
        this.simbolo = simbolo;
        this.colorExterno = colorExterno;
        this.colorInterno = colorInterno;
        this.rutaImagen = rutaImagen;
        this.lado = true;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public Color getColorExterno() {
        return colorExterno;
    }

    public String getColorInterno() {
        return colorInterno;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public boolean isLado() {
        return lado;
    }

    public void setLado(boolean lado) {
        this.lado = lado;
    }

    public void setColorExterno(Color nuevoColor) {
        this.colorExterno = nuevoColor;
    }

    public void setColorNombre(String nuevoNombre) {
        this.colorInterno = nuevoNombre;
    }
    
    public boolean esComodin() {
        return "negro".equalsIgnoreCase(this.colorInterno);
    }
}
