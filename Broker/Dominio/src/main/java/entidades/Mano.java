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

    public void agregarCarta(Carta carta) {
        if (carta != null) {
            System.out.println("[Mano] Agregando carta: " 
                + carta.getValor() + " de color " + carta.getColor());

            cartas.add(carta);

            System.out.println("[Mano] Total de cartas: " + cartas.size());
        } else {
            System.out.println("[Mano] Intento de agregar carta nula");
        }
    }

    public void removerCarta(Carta carta) {
        System.out.println("[Mano] Intentando remover carta: " 
            + carta.getValor() + " de color " + carta.getColor());

        boolean removida = cartas.remove(carta);

        if (removida) {
            System.out.println("[Mano] Carta removida correctamente");
        } else {
            System.out.println("[Mano] La carta no estaba en la mano");
        }

        System.out.println("[Mano] Total de cartas: " + cartas.size());
    }

    public int getSize() {
        return cartas.size();
    }

    public List<Carta> getCartasReales() {
        return cartas;
    }
}