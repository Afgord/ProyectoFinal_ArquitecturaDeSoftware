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
    private Mano mano;

    public Jugador(String nombre, String urlAvatar, Mano mano) {
        this.nombre = nombre;
        this.urlAvatar = urlAvatar;
        this.mano = mano;
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

    
    
    public Mano getMano() {
        return mano;
    }
    
    public void setMano(Mano nuevaMano) {
        this.mano = nuevaMano;
    }
}