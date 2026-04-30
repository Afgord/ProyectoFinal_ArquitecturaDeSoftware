/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import entidades.TipoEvento;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class ResultadoGritoDTO {
    private final boolean exitoGrito; 
    private final TipoEvento evento;      
    private final String idCastigado;  
    private final List<JugadorDTO> estadoJugadores; 
    
    public ResultadoGritoDTO(boolean exitoGrito, TipoEvento evento, String idCastigado, List<JugadorDTO> estadoJugadores) {
        this.exitoGrito = exitoGrito;
        this.evento = evento;
        this.idCastigado = idCastigado;
        this.estadoJugadores = estadoJugadores;
    }

    public boolean isExitoGrito() { return exitoGrito; }
    public TipoEvento getEvento() { return evento; }
    public String getIdCastigado() { return idCastigado; }
    public List<JugadorDTO> getEstadoJugadores() { return estadoJugadores; }
}