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

        System.out.println("[Descarte] Validando jugada...");
        System.out.println("[Descarte] Carta nueva: " 
            + nueva.getValor() + " de color " + nueva.getColor());

        if (cima == null) {
            System.out.println("[Descarte] No hay carta en la pila, jugada válida");
            return true;
        }

        System.out.println("[Descarte] Carta en cima: " 
            + cima.getValor() + " de color " + cima.getColor());
        
        if (nueva.getColor() == Colores.NEGRO) {
            System.out.println("[Descarte] Carta negra, jugada válida");
            return true;
        }

        if (nueva.getColor() == cima.getColor()) {
            System.out.println("[Descarte] Coincide color, jugada válida");
            return true;
        }

        if (nueva.getValor() == cima.getValor()) {
            System.out.println("[Descarte] Coincide valor, jugada válida");
            return true;
        }
        
        System.out.println("[Descarte] Jugada inválida");
        return false;
    }

    public Carta getCartaCima() {
        return pila.isEmpty() ? null : pila.peek();
    }
    
    public void cambiarColorCartaCima(Colores nuevoColor) {
        Carta cima = getCartaCima();

        if (cima != null) {
            System.out.println("[Descarte] Cambiando color de la carta en cima de " 
                + cima.getColor() + " a " + nuevoColor);

            cima.setColor(nuevoColor);
        } else {
            System.out.println("[Descarte] No hay carta en la cima para cambiar color");
        }
    }
}