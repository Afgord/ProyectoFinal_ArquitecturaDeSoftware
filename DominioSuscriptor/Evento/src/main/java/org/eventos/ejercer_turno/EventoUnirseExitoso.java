/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

import dtos.JugadorDTO;
import java.util.List;

/**
 *
 * @author lagar
 */
public class EventoUnirseExitoso extends Evento{
    private static final long serialVersionUID = 1L;
 
    private final JugadorDTO jugadorNuevo;
    private final List<JugadorDTO> jugadoresEnSala;
 
    public EventoUnirseExitoso(JugadorDTO jugadorNuevo,
                                List<JugadorDTO> jugadoresEnSala,
                                String topico) {
        super(topico);
        this.jugadorNuevo    = jugadorNuevo;
        this.jugadoresEnSala = jugadoresEnSala;
    }
 
    public JugadorDTO getJugadorNuevo()             { return jugadorNuevo; }
    public List<JugadorDTO> getJugadoresEnSala()    { return jugadoresEnSala; }
}
