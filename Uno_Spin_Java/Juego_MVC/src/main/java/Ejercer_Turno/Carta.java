/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;

public class Carta implements ICartaReadOnly {
    private final String simbolo;
    private Color colorExterno; 
    private String colorInterno; 
    private final String rutaImagen;
    private boolean lado; // Estado: ¿está boca arriba?

    public Carta(String simbolo, Color colorExterno, String colorInterno, String rutaImagen) {
        this.simbolo = simbolo;
        this.colorExterno = colorExterno;
        this.colorInterno = colorInterno;
        this.rutaImagen = rutaImagen;
        this.lado = true; // Por defecto se crean viendo al frente
    }

    // Getters de la Interfaz
    @Override public String getSimbolo() { return simbolo; }
    @Override public Color getColorExterno() { return colorExterno; }
    @Override public String getColorInterno() { return colorInterno; }
    @Override public String getRutaImagen() { return rutaImagen; }
    @Override public boolean isLado() { return lado; }

    // Lógica del Modelo
    public void setLado(boolean lado) {
        this.lado = lado;
    }
    // En Carta.java
    public void setColorExterno(Color nuevoColor) { this.colorExterno = nuevoColor; }
    public void setColorNombre(String nuevoNombre) { this.colorInterno = nuevoNombre; }
}
