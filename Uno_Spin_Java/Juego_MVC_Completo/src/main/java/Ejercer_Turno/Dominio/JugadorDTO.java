/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.Dominio;
/**
 * 
 * @author Luis Rafael
 */
public class JugadorDTO {
    private final String id;
    private final String nombre;
    private final int numCartas;
    private final boolean esTurnoActual;

    public JugadorDTO(String id, String nombre, int numCartas, boolean esTurnoActual) {
        this.id = id;
        this.nombre = nombre;
        this.numCartas = numCartas;
        this.esTurnoActual = esTurnoActual;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getNumCartas() { return numCartas; }
    public boolean isEsTurnoActual() { return esTurnoActual; }
}
