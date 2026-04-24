/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;
/**
 * 
 * @author lagar
 */
public class ResultadoJugadaDTO {
    private final boolean exito;
    private final String eventoTipo; 
    private final JugadorDTO ganador; 
    private final List<JugadorDTO> estadoJugadores;
    private final CartaDTO cartaCima;

    public ResultadoJugadaDTO(boolean exito, String eventoTipo, JugadorDTO ganador, 
                              List<JugadorDTO> estadoJugadores, CartaDTO cartaCima) {
        this.exito = exito;
        this.eventoTipo = eventoTipo;
        this.ganador = ganador;
        this.estadoJugadores = estadoJugadores;
        this.cartaCima = cartaCima;
    }
    
    public boolean isExito() { return exito; }
    public String getEventoTipo() { return eventoTipo; }
    public JugadorDTO getGanador() { return ganador; }
    public List<JugadorDTO> getEstadoJugadores() { return estadoJugadores; }
    public CartaDTO getCartaCima() { return cartaCima; }
}