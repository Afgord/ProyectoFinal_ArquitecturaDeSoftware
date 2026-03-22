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
public class FachadaSelectorColor implements FachadaColor {
    @Override
    public void procesarSeleccion(Color color, String nombre) {
        if (color == null || nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("Seleccion de color invalida");
        }
        System.out.println("Logica de dominio: Color validado -> " + nombre);
    }
}
