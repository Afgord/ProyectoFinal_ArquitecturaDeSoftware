/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

/**
 * Evento de respuesta cuando la configuración de la partida fue rechazada.
 */
public class EventoConfiguracionRechazada extends Evento {

    private static final long serialVersionUID = 1L;

    private final String motivo;

    public EventoConfiguracionRechazada(String motivo, String idEvento) {
        super(idEvento);
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }
}
