/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

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
    private String urlAvatar;
    
    public JugadorDTO(String idJugador, String nombre) {
        this.idJugador = idJugador;
        this.nombre = nombre;
    }
    
    public JugadorDTO(String idJugador, String nombre, List<CartaDTO> mano, boolean grito) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.mano = mano;
    }
    
    public JugadorDTO(String idJugador, String nombre, String urlAvatar) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.urlAvatar = urlAvatar;
    }
    
    public JugadorDTO(String idJugador, String nombre, List<CartaDTO> mano, boolean grito, String urlAvatar) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.mano = mano;
        this.grito = grito;
        this.urlAvatar = urlAvatar;
    }
    
    public boolean isGrito() { return grito; }
    public String idJugador(){ return idJugador; }
    public String getIdJugador(){ return idJugador; }
    public String getNombre() { return nombre; }
    public List<CartaDTO> getMano() { return mano; }
    public int getNumCartas() { return mano == null ? 0 : mano.size(); }
    public String getUrlAvatar() { return urlAvatar; }
    public void setMano(List<CartaDTO> mano) { this.mano = mano; }
    public void setGrito(boolean grito) { this.grito = grito; }
    public void setUrlAvatar(String urlAvatar) { this.urlAvatar = urlAvatar; }
}
