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
    private final TipoEvento mensaje;      
    private final String idCastigado;  
    private final List<JugadorDTO> estadoJugadores; 
    
    public ResultadoGritoDTO(boolean exitoGrito, TipoEvento mensaje, String idCastigado, List<JugadorDTO> estadoJugadores) {
        this.exitoGrito = exitoGrito;
        this.mensaje = mensaje;
        this.idCastigado = idCastigado;
        this.estadoJugadores = estadoJugadores;
    }

    public boolean isExitoGrito() {
        return exitoGrito;
    }

    public TipoEvento getMensaje() {
        return mensaje;
    }

    public String getIdCastigado() {
        return idCastigado;
    }

    public List<JugadorDTO> getEstadoJugadores() {
        return estadoJugadores;
    }
    
    
}
