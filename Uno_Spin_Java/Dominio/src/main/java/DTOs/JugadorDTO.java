/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class JugadorDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private final String idJugador;
    private final String nombre;
    private List<CartaDTO> mano; 
    private boolean grito;
    
    public JugadorDTO(String idJugador, String nombre) {
        this.idJugador = idJugador;
        this.nombre = nombre;
    }
    
    public JugadorDTO(String idJugador, String nombre, List<CartaDTO> mano, boolean grito) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.mano = mano;
    }
    
    public boolean isGrito() { return grito; }
    public String idJugador(){ return idJugador; }
    public String getNombre() { return nombre; }
    public List<CartaDTO> getMano() { return mano; }
    public int getNumCartas() { return mano.size(); }
}