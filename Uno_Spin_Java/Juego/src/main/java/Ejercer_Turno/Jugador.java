/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

public class Jugador {
    private String nombre;
    private int numCartas;
    private String urlAvatar;

    public Jugador(String nombre, int numCartas, String urlAvatar) {
        this.nombre = nombre;
        this.numCartas = numCartas;
        this.urlAvatar = urlAvatar;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumCartas() {
        return numCartas;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumCartas(int numCartas) {
        this.numCartas = numCartas;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }
}
