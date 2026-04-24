/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;
/**
 * 
 * @author lagar
 */
public class Carta {
    private final Valor valor;
    private Colores color;

    public Carta(Valor valor, Colores color) {
        this.valor = valor;
        this.color = color;
    }
    
    public boolean esAccion() {
        return valor == Valor.REVERSA || 
               valor == Valor.PROHIBIDO || 
               valor == Valor.MASDOS || 
               valor == Valor.MASCUATRO;
    }
    
    public boolean esComodin() {
        return valor == Valor.MASCUATRO || 
               valor == Valor.CAMBIOCOLOR;
    }
    
    public boolean esNumerica() {
        return valor.ordinal() <= Valor.NUEVE.ordinal();
    }
    
    public boolean esSpin(){
        int posicion = this.valor.ordinal();
        return posicion >= Valor.UNO.ordinal() && posicion <= Valor.CINCO.ordinal();
    }
    
    public Valor getValor() { return valor; }
    public Colores getColor() { return color; }
    public void setColor(Colores nuevoColor) { this.color = nuevoColor; }
}