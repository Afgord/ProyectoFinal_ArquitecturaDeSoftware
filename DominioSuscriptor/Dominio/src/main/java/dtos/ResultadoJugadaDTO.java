/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import entidades.TipoEvento;
import java.util.List;

/**
 * @author lagar
 */
public class ResultadoJugadaDTO {
    private final boolean exito;
    private final TipoEvento eventoTipo; 
    private final JugadorDTO ganador; 
    private final List<JugadorDTO> estadoJugadores;
    private final CartaDTO cartaCima;
    private final String mensaje; 

    public ResultadoJugadaDTO(boolean exito, TipoEvento eventoTipo, JugadorDTO ganador, 
                               List<JugadorDTO> estadoJugadores, CartaDTO cartaCima, String mensaje) {
        this.exito = exito;
        this.eventoTipo = eventoTipo;
        this.ganador = ganador;
        this.estadoJugadores = estadoJugadores;
        this.cartaCima = cartaCima;
        this.mensaje = mensaje;
    }
    public ResultadoJugadaDTO(boolean exito, TipoEvento eventoTipo, JugadorDTO ganador, 
                               List<JugadorDTO> estadoJugadores, CartaDTO cartaCima) {
        this(exito, eventoTipo, ganador, estadoJugadores, cartaCima, "");
    }
    
    public boolean isExito() { return exito; }
    public TipoEvento getEventoTipo() { return eventoTipo; }
    public JugadorDTO getGanador() { return ganador; }
    public List<JugadorDTO> getEstadoJugadores() { return estadoJugadores; }
    public CartaDTO getCartaCima() { return cartaCima; }
    public String getMensaje() { return mensaje; }
}