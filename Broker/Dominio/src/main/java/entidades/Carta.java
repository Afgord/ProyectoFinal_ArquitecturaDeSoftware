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
    
    public Valor getValor() { 
        return valor; 
    }

    public Colores getColor() { 
        return color; 
    }

    public void setColor(Colores nuevoColor) { 
        this.color = nuevoColor; 
    }

    @Override
    public String toString() {
        return "Carta{" + "valor=" + valor + ", color=" + color + '}';
    }
}