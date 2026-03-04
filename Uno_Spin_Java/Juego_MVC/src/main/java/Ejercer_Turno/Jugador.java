/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.util.ArrayList;
import java.util.List;

/**
 * MODELO: Jugador
 * Implementa IJugadorReadOnly para que la vista lo consulte,
 * y gestiona su propia Mano y sus observadores.
 */
public class Jugador implements IJugadorReadOnly {
    private final String nombre;
    private final String urlAvatar;
    private final Mano mano; // La entidad que acabamos de crear
    private final List<IJugadorObserver> observers;

    public Jugador(String nombre, String urlAvatar) {
        this.nombre = nombre;
        this.urlAvatar = urlAvatar;
        this.mano = new Mano();
        this.observers = new ArrayList<>();
    }

    // --- LÓGICA DE NEGOCIO ---

    public void agregarCarta(Carta carta) {
        mano.agregarCarta(carta);
        notificar(); // Avisa a los paneles que robase una carta
    }

    public void tirarCarta(Carta carta) {
        mano.removerCarta(carta);
        notificar(); // Avisa a los paneles que perdiste una carta
    }

    // --- PATRÓN OBSERVER ---

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

    // --- IMPLEMENTACIÓN DE IJugadorReadOnly (Interfaz de Lectura) ---

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public int getNumCartas() {
        // Llama al método getSize() de la clase Mano
        return mano.getSize();
    }

    @Override
    public String getUrlAvatar() {
        return urlAvatar;
    }

    // --- MÉTODOS PARA LA COMUNICACIÓN ENTRE ENTIDADES ---

    /**
     * Permite que el PanelMano obtenga las cartas reales para crear los PanelCarta.
     */
    public List<Carta> getCartasModelo() {
        return mano.getCartasReales();
    }
}

