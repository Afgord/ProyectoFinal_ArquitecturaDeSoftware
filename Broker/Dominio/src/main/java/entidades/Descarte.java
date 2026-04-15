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
    private String colorActivo;

    public Descarte(Carta cartaInicial) {
        cartaInicial.setLado(true);
        this.pila.push(cartaInicial);
        this.colorActivo = cartaInicial.getColorInterno();
    }

    public void recibirCarta(Carta carta) {
        pila.push(carta);
        this.colorActivo = carta.getColorInterno();
    }

    public boolean validarJugada(Carta nueva) {
        Carta cima = getCartaCima();
        if (cima == null) return true;
        
        if (nueva.getColorInterno().equalsIgnoreCase("negro")) {
            return true;
        }
        
        if (nueva.getColorInterno().equalsIgnoreCase(this.colorActivo)) {
            return true;
        }
        
        if (nueva.getValor().equals(cima.getValor())) {
            return true;
        }
        
        return false;
    }

    public Carta getCartaCima() {
        return pila.isEmpty() ? null : pila.peek();
    }

    public String getColorActivo() {
        return colorActivo;
    }
    
    public void setColorActivo(String color){
        this.colorActivo = color;
    }
}