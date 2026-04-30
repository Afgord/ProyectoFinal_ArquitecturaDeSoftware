/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

/**
 *
 * @author lagar
 */
public class EventoFallo extends Evento{
    private Errores error;

    public EventoFallo(Errores error, String idEvento) {
        super(idEvento);
        this.error = error;
    }

    public Errores getError() {
        return error;
    }
}
