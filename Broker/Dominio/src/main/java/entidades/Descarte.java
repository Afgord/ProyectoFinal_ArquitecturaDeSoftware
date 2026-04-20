/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Stack;
/**
 * 
 * @author lagar
 */
public class Descarte {
    private Stack<Carta> pila = new Stack<>();
    private Colores colorActivo;

    public Descarte(Carta cartaInicial) {
        this.pila.push(cartaInicial);
        this.colorActivo = cartaInicial.getColor();
    }

    public void recibirCarta(Carta carta) {
        pila.push(carta);
        this.colorActivo = carta.getColor();
    }

    public boolean validarJugada(Carta nueva) {
        Carta cima = getCartaCima();
        if (cima == null) return true;

        if (nueva.getColor() == Colores.NEGRO) {
            return true;
        }
        if (nueva.getColor() == this.colorActivo || nueva.getValor() == cima.getValor()) {
            return true;
        }
        
        return false;
    }

    public Carta getCartaCima() {
        return pila.isEmpty() ? null : pila.peek();
    }

    public Colores getColorActivo() {
        return colorActivo;
    }
    
    public void setColorActivo(Colores color){
        this.colorActivo = color;
    }
}