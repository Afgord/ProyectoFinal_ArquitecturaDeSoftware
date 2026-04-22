/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import entidades.Colores;
import entidades.Valor;
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
    public boolean esComodin() {
        return valor == Valor.MASCUATRO || valor == Valor.CAMBIOCOLOR;
    }
    
    public boolean esAccion() {
        return valor == Valor.REVERSA || 
               valor == Valor.PROHIBIDO || 
               valor == Valor.MASDOS || 
               valor == Valor.MASCUATRO;
    }
    
    public boolean esSpin(){
        int posicion = this.valor.ordinal();
        return posicion >= Valor.UNO.ordinal() && posicion <= Valor.CINCO.ordinal();
    }
    
    public boolean esNumerica() {
        return valor.ordinal() <= Valor.NUEVE.ordinal();
    }
}
