/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.mvc_dominio;

import org.evento.dto.JugadorDTO;

/**
 *
 * @author lagar
 */
public class EventoGritar extends EventoAccion{
    private JugadorDTO jugador;

    public EventoGritar(JugadorDTO jugador, String idJugador, String idEvento) {
        super(idJugador, idEvento);
        this.jugador = jugador;
    }

    public JugadorDTO getJugador() {
        return jugador;
    }  
}
