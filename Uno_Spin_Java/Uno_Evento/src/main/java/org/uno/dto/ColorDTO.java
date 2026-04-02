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
public class ColorDTO implements Serializable {
    private final Color color;
    private final String nombre;

    public ColorDTO(Color color, String nombre) {
        this.color = color;
        this.nombre = nombre;
    }

    public Color getColor() { return color; }
    public String getNombre() { return nombre; }
}