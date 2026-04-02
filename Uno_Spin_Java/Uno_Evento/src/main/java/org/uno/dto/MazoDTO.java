/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uno.dto;

import java.io.Serializable;
/**
 * 
 * @author lagar
 */
public class MazoDTO implements Serializable {
    private final int cantidadCartas;

    public MazoDTO(int cantidadCartas) {
        this.cantidadCartas = cantidadCartas;
    }

    public int getCantidadCartas() { return cantidadCartas; }
}
