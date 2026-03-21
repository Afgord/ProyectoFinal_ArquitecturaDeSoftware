/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cambiar_Color.Dominio;

import java.awt.Color;
/**
 * 
 * @author lagar
 */
public class SeleccionColor {
    private Color color;
    private String nombre;

    public SeleccionColor(Color color, String nombre) {
        this.color = color;
        this.nombre = nombre;
    }
    public Color getColor() { return color; }
    public String getNombre() { return nombre; }
}
