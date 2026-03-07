/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.Dominio;

import java.util.List;

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
        mano.agregarCarta(carta);
    }

    public void tirarCarta(Carta carta) {
        mano.removerCarta(carta);
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
