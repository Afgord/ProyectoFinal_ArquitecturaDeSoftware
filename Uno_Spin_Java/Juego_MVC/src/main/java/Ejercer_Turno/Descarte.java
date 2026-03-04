/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/**
 * MODELO: Gestiona la lógica de la pila de descarte y las reglas de validación.
 */
public class Descarte {
    private Stack<Carta> pila = new Stack<>();
    private List<IDescarteObserver> observers = new ArrayList<>();
    private String colorActivo; // Color que rige el juego en este momento

    public Descarte(Carta cartaInicial) {
        // Forzamos que la carta inicial sea visible
        cartaInicial.setLado(true);
        this.pila.push(cartaInicial);
        
        // El color activo inicial es el color de la carta que salió del mazo
        this.colorActivo = cartaInicial.getColorInterno();
    }

    public void recibirCarta(Carta carta) {
        pila.push(carta);
        
        // CRÍTICO: Actualizamos el color activo. 
        // Si es una carta normal, será su color (Rojo, Azul, etc).
        // Si es un comodín, será el color que el jugador eligió en el PanelMano.
        this.colorActivo = carta.getColorInterno();
        
        notificar(); 
    }

    public boolean validarJugada(Carta nueva) {
        Carta cima = getCartaCima();
        if (cima == null) return true;

        // 1. REGLA COMODÍN: Las cartas negras (+4 o CC) siempre son válidas.
        if (nueva.getColorInterno().equalsIgnoreCase("negro")) {
            return true;
        }

        // 2. REGLA COLOR: Si coincide con el color que manda en la mesa.
        if (nueva.getColorInterno().equalsIgnoreCase(this.colorActivo)) {
            return true;
        }

        // 3. REGLA SÍMBOLO/NÚMERO: Si coincide el valor (7 con 7, PRO con PRO, etc).
        if (nueva.getSimbolo().equals(cima.getSimbolo())) {
            return true;
        }

        // Si no cumple ninguna, la jugada no se permite
        return false;
    }

    public Carta getCartaCima() {
        return pila.isEmpty() ? null : pila.peek();
    }

    public String getColorActivo() {
        return colorActivo;
    }

    // --- SISTEMA DE OBSERVADORES ---
    public void addObserver(IDescarteObserver obs) { observers.add(obs); }
    
    private void notificar() {
        for (IDescarteObserver obs : observers) {
            obs.descarteActualizado();
        }
    }
}