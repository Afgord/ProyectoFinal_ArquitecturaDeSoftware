/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uno.dto;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class JugadorDTO implements Serializable {
    private final String id;
    private final String rutaAvatar; 
    private final String nombre;
    private final int numCartas;
    private final boolean esTurnoActual;
    private final boolean esLocal;
    private final List<CartaDTO> cartas; 

    public JugadorDTO(String id, String rutaAvatar, String nombre, int numCartas, boolean esTurnoActual, boolean esLocal, List<CartaDTO> cartas) {
        this.id = id;
        this.rutaAvatar = rutaAvatar;
        this.nombre = nombre;
        this.numCartas = numCartas;
        this.esTurnoActual = esTurnoActual;
        this.esLocal = esLocal;
        this.cartas = cartas;
    }

    public String getId() { return id; }
    public String getRutaAvatar() { return rutaAvatar; }
    public String getNombre() { return nombre; }
    public int getNumCartas() { return numCartas; }
    public boolean isEsTurnoActual() { return esTurnoActual; }
    public boolean isEsLocal() { return esLocal; }
    public List<CartaDTO> getCartas() { return cartas; }
}
