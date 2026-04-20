/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.List;
/**
 * 
 * @author lagar
 */
public class Jugador {
    private final String nombre;
    private final String urlAvatar;
    private final Mano mano;

    public Jugador(String nombre, String urlAvatar) {
        this.nombre = nombre;
        this.urlAvatar = urlAvatar;
        this.mano = new Mano();
    }

    public void agregarCarta(Carta carta) {
        System.out.println("[Jugador: " + nombre + "] Agregando carta: " 
            + carta.getValor() + " de color " + carta.getColor());

        mano.agregarCarta(carta);

        System.out.println("[Jugador: " + nombre + "] Total de cartas ahora: " + mano.getSize());
    }

    public void tirarCarta(Carta carta) {
        System.out.println("[Jugador: " + nombre + "] Tirando carta: " 
            + carta.getValor() + " de color " + carta.getColor());

        mano.removerCarta(carta);

        System.out.println("[Jugador: " + nombre + "] Cartas restantes: " + mano.getSize());
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumCartas() {
        return mano.getSize();
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public List<Carta> getCartasModelo() {
        return mano.getCartasReales();
    }
    
    public Mano getMano() {
        return mano;
    }
}