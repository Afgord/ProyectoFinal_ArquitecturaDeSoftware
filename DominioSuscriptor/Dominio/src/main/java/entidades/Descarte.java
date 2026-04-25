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

    public boolean recibirCarta(Carta carta) {
        if (validarJugada(carta)) {
            System.out.println("[Descarte] Recibiendo carta: " 
                + carta.getValor() + " de color " + carta.getColor());

            pila.push(carta);

            System.out.println("[Descarte] Nueva carta en cima: " 
                + carta.getValor() + " de color " + carta.getColor());
            return true;
        }
        
        System.out.println("[Descarte] Jugada inválida: " + carta.getValor() + " " + carta.getColor());
        return false;
    }
    
    private boolean validarJugada(Carta nueva) {
        Carta cima = getCartaCima();
        if (cima == null) {
            System.out.println("[Descarte] La pila está vacía, validación automática.");
            return true;
        }

        if (nueva.esComodin()) {
            System.out.println("[Descarte] Validación: Comodín detectado (Permitido).");
            return true;
        }

        boolean mismoColor = (nueva.getColor() == cima.getColor());
        boolean mismoValor = (nueva.getValor() == cima.getValor());

        if (mismoColor) System.out.println("[Descarte] Validación exitosa: Coincidencia de COLOR.");
        if (mismoValor) System.out.println("[Descarte] Validación exitosa: Coincidencia de VALOR.");

        return mismoColor || mismoValor;
    }

    public Carta getCartaCima() {
        return pila.isEmpty() ? null : pila.peek();
    }
}