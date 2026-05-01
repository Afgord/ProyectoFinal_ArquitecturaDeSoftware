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
public class EventoAnuciarGanador extends Evento{
    private JugadorDTO ganador;

    public EventoAnuciarGanador(JugadorDTO ganador, String idEvento) {
        super(idEvento);
        this.ganador = ganador;
    }

    public JugadorDTO getGanador() {
        return ganador;
    }
}
