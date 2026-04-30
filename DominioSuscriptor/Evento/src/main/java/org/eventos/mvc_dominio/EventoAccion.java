/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.mvc_dominio;

/**
 *
 * @author lagar
 */
public class EventoAccion extends Evento{
    private String idJugador;

    public EventoAccion(String idJugador, String idEvento) {
        super(idEvento);
        this.idJugador = idJugador;
    }

    public String getIdJugador() {
        return idJugador;
    }
}
