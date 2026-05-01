/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import entidades.ResultadoRuleta;
import entidades.TipoEvento;
import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class ResultadoJugadaDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private final boolean exito;
    private final TipoEvento eventoTipo; 
    private final JugadorDTO ganador; 
    private final List<JugadorDTO> estadoJugadores;
    private final CartaDTO cartaCima;
    private String idJugadorActual;
    private ResultadoRuleta resultadoRuleta;

    public ResultadoJugadaDTO(boolean exito, TipoEvento eventoTipo, JugadorDTO ganador, 
                                   List<JugadorDTO> estadoJugadores, CartaDTO cartaCima, 
                                   String idJugadorActual, ResultadoRuleta resultadoRuleta) {
        this.exito = exito;
        this.eventoTipo = eventoTipo;
        this.ganador = ganador;
        this.estadoJugadores = estadoJugadores;
        this.cartaCima = cartaCima;
        this.idJugadorActual = idJugadorActual;
        this.resultadoRuleta = resultadoRuleta; 
    }
    
    public boolean isExito() { return exito; }
    public TipoEvento getEventoTipo() { return eventoTipo; }
    public JugadorDTO getGanador() { return ganador; }
    public List<JugadorDTO> getEstadoJugadores() { return estadoJugadores; }
    public CartaDTO getCartaCima() { return cartaCima; }
    public String getIdJugadorActual() { return idJugadorActual;}  
    public ResultadoRuleta getResultadoRuleta() { return resultadoRuleta; }
}