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
public class ResultadoGritoDTO {
    private final boolean exitoGrito; 
    private final String mensaje;      
    private final String idCastigado;  
    private final List<JugadorDTO> estadoJugadores; 
    
    public ResultadoGritoDTO(boolean exitoGrito, String mensaje, String idCastigado, List<JugadorDTO> estadoJugadores) {
        this.exitoGrito = exitoGrito;
        this.mensaje = mensaje;
        this.idCastigado = idCastigado;
        this.estadoJugadores = estadoJugadores;
    }

    public boolean isExitoGrito() {
        return exitoGrito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getIdCastigado() {
        return idCastigado;
    }

    public List<JugadorDTO> getEstadoJugadores() {
        return estadoJugadores;
    }
    
    
}
