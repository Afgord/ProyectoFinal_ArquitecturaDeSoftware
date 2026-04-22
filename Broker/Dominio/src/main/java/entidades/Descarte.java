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

    public Descarte(Carta cartaInicial) {
        this.pila.push(cartaInicial);
    }

    public void recibirCarta(Carta carta) {
        System.out.println("[Descarte] Recibiendo carta: " 
            + carta.getValor() + " de color " + carta.getColor());

        pila.push(carta);

        System.out.println("[Descarte] Nueva carta en cima: " 
            + carta.getValor() + " de color " + carta.getColor());
    }

    public boolean validarJugada(Carta nueva) {
        Carta cima = getCartaCima();
        if (cima == null) return true;
        if (nueva.esComodin()) {
            return true;
        }
        return nueva.getColor() == cima.getColor() || nueva.getValor() == cima.getValor();
    }

    public Carta getCartaCima() {
        return pila.isEmpty() ? null : pila.peek();
    }
}