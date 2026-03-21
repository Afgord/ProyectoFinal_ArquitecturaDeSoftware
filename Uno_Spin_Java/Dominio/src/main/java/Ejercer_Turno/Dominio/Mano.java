/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.Dominio;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class Mano {

    private final List<Carta> cartas;

    public Mano() {
        this.cartas = new ArrayList<>();
    }

    public void agregarCarta(Carta carta) {
        if (carta != null) {
            cartas.add(carta);
        }
    }

    public void removerCarta(Carta carta) {
        cartas.remove(carta);
    }

    public int getSize() {
        return cartas.size();
    }

    public List<Carta> getCartasReales() {
        return cartas;
    }
}