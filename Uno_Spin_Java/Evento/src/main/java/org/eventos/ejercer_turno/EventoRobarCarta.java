/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

/**
 *
 * @author lagar
 */
public class EventoRobarCarta extends EventoAccion {
    private boolean robar;

    public EventoRobarCarta(boolean robar, String idJugador, String idEvento) {
        super(idJugador, idEvento);
        this.robar = robar;
    }

    public boolean isRobar() {
        return robar;
    }
}
