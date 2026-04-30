/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

import java.util.List;
import org.evento.dto.JugadorDTO;

/**
 *
 * @author lagar
 */
public class EventoResultadoGrito extends Evento{
    private boolean exitoGrito;
    private String idCastigado;
    private List<JugadorDTO> estadoJugadores; 

    public EventoResultadoGrito(boolean exitoGrito, String idCastigado, List<JugadorDTO> estadoJugadores, String idEvento) {
        super(idEvento);
        this.exitoGrito = exitoGrito;
        this.idCastigado = idCastigado;
        this.estadoJugadores = estadoJugadores;
    }
    
    
}
