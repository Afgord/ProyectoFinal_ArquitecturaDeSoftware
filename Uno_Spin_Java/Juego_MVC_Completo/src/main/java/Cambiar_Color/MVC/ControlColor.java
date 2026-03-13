/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cambiar_Color.MVC;

import java.awt.Color;
/**
 * 
 * @author lagar
 */
public class ControlColor {
    private ModeloColor modelo;
    private Color cAzul, cRojo, cAmarillo, cVerde;

    public ControlColor(ModeloColor modelo, Color azul, Color rojo, Color amarillo, Color verde) {
        this.modelo = modelo;
        this.cAzul = azul;
        this.cRojo = rojo;
        this.cAmarillo = amarillo;
        this.cVerde = verde;
    }

    public void seleccionarColor(Color color, String nombre) {
        modelo.confirmarSeleccion(color, nombre);
    }

    public Color getAzul() { return cAzul; }
    public Color getRojo() { return cRojo; }
    public Color getAmarillo() { return cAmarillo; }
    public Color getVerde() { return cVerde; }
}