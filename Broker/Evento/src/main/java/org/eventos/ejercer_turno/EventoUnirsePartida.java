/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

import dtos.JugadorDTO;

/**
 *
 * @author lagar
 */
public class EventoUnirsePartida extends EventoAccion {
 
    private static final long serialVersionUID = 1L;
 
    private final JugadorDTO jugador;

    public EventoUnirsePartida(String idJugador, String idEvento, JugadorDTO jugador) {
        super(idJugador, idEvento);
        this.jugador = jugador;
    }
 
    public JugadorDTO getJugador() {
        return jugador;
    }
}
