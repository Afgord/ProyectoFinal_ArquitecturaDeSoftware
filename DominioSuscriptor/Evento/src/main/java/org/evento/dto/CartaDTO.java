/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.evento.dto;

/**
 *
 * @author lagar
 */
public class CartaDTO {
    private final Valor valor;
    private final Colores color;

    public CartaDTO(Valor valor, Colores color) {
        this.valor = valor;
        this.color = color;
    }
    
    public Valor getValor() { return valor; }
    public Colores getColor() { return color; }
}
