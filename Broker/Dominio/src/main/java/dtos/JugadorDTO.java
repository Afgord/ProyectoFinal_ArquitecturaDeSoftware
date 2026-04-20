/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;

public class JugadorDTO {
    private final String nombre;
    private final List<CartaDTO> mano; 

    public JugadorDTO(String nombre, List<CartaDTO> mano) {
        this.nombre = nombre;
        this.mano = mano;
    }

    public String getNombre() { return nombre; }
    public List<CartaDTO> getMano() { return mano; }
    public int getNumCartas() { return mano.size(); }
}
