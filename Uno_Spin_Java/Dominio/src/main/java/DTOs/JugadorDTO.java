/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.List;

/**
 * * @author Luis Rafael
 */
public class JugadorDTO {
    private final String rutaAvatar; 
    private final String nombre;
    private final int numCartas;
    private final boolean esTurnoActual;
    private final List<CartaDTO> cartas; 

    public JugadorDTO(String rutaAvatar, String nombre, int numCartas, boolean esTurnoActual, List<CartaDTO> cartas) {
        this.rutaAvatar = rutaAvatar;
        this.nombre = nombre;
        this.numCartas = numCartas;
        this.esTurnoActual = esTurnoActual;
        this.cartas = cartas;
    }

    public String getRutaAvatar() { return rutaAvatar; }
    public String getNombre() { return nombre; }
    public int getNumCartas() { return numCartas; }
    public boolean isEsTurnoActual() { return esTurnoActual; }
    public List<CartaDTO> getCartas() { return cartas; }
}