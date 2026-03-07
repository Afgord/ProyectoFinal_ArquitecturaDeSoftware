/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Descarte {
    private Stack<Carta> pila = new Stack<>();
    private List<IDescarteObserver> observers = new ArrayList<>();
    private String colorActivo; 

    public Descarte(Carta cartaInicial) {
        cartaInicial.setLado(true);
        this.pila.push(cartaInicial);
        this.colorActivo = cartaInicial.getColorInterno();
    }

    public void recibirCarta(Carta carta) {
        pila.push(carta);
        this.colorActivo = carta.getColorInterno();     
        notificar(); 
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
        if (nueva.getSimbolo().equals(cima.getSimbolo())) {
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
    public void addObserver(IDescarteObserver obs) { observers.add(obs); }
    
    private void notificar() {
        for (IDescarteObserver obs : observers) {
            obs.descarteActualizado();
        }
    }
}