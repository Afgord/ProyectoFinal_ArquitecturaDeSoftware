/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.util.ArrayList;
import java.util.List;

public class Jugador implements IJugadorReadOnly {
    private final String nombre;
    private final String urlAvatar;
    private final Mano mano;
    private final List<IJugadorObserver> observers;

    public Jugador(String nombre, String urlAvatar) {
        this.nombre = nombre;
        this.urlAvatar = urlAvatar;
        this.mano = new Mano();
        this.observers = new ArrayList<>();
    }

    public void agregarCarta(Carta carta) {
        mano.agregarCarta(carta);
        notificar();
    }

    public void tirarCarta(Carta carta) {
        mano.removerCarta(carta);
        notificar(); 
    }

    public void addObserver(IJugadorObserver obs) {
        if (!observers.contains(obs)) {
            observers.add(obs);
        }
    }

    private void notificar() {
        for (IJugadorObserver obs : observers) {
            obs.actualizar();
        }
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public int getNumCartas() {
        return mano.getSize();
    }

    @Override
    public String getUrlAvatar() {
        return urlAvatar;
    }

    public List<Carta> getCartasModelo() {
        return mano.getCartasReales();
    }
}

