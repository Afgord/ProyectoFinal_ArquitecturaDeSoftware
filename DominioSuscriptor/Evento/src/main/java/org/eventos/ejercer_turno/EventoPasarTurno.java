/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

/**
 *
 * @author lagar
 */
public class EventoPasarTurno extends EventoAccion{
    private boolean pasar;

    public EventoPasarTurno(boolean pasar, String idJugador, String idEvento) {
        super(idJugador, idEvento);
        this.pasar = pasar;
    }

    public boolean isPasar() {
        return pasar;
    }
}
