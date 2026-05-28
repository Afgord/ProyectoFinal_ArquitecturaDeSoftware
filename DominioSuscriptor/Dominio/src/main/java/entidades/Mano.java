/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

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

    public void recibirCartas(List<Carta> nuevasCartas) {
        if (nuevasCartas == null) {
            return;
        }
        for (Carta carta : nuevasCartas) {
            agregarCarta(carta);
        }
    }

    public void agregarCarta(Carta carta) {
        if (carta != null) {
            cartas.add(carta);
            System.out.println("[Mano] [+] Carta añadida: " 
                + carta.getValor() + " [" + carta.getColor() + "]");
            System.out.println("[Mano] Cartas actuales en mano: " + cartas.size());
        } else {
            System.out.println("[Mano] [!] Error: Intento de agregar una carta nula.");
        }
    }

    public void removerCarta(Carta carta) {
        if (carta == null) {
            System.out.println("[Mano] [!] Error: Intento de remover una referencia nula.");
            return;
        }

        System.out.println("[Mano] [-] Intentando descartar: " 
            + carta.getValor() + " [" + carta.getColor() + "]");

        boolean removida = cartas.remove(carta);

        if (removida) {
            System.out.println("[Mano] Descarte exitoso.");
        } else {
            System.out.println("[Mano] [!] Advertencia: La carta no se encontró en la mano.");
        }

        System.out.println("[Mano] Cartas restantes: " + cartas.size());
    }

    public int getSize() {
        return cartas.size();
    }

    public List<Carta> getCartasReales() {
        return cartas;
    }
}