/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.awt.Color;
/**
 * 
 * @author lagar
 */
public class ColorDTO {
    private final Color color;
    private final String nombre;

    public ColorDTO(Color color, String nombre) {
        this.color = color;
        this.nombre = nombre;
    }

    public Color getColor() { return color; }
    public String getNombre() { return nombre; }
}
