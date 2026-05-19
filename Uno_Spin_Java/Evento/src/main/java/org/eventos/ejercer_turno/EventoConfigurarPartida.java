/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

import dtos.ConfiguracionPartidaDTO;

/**
 * Evento enviado por el Publisher para solicitar la configuración de una partida.
 */
public class EventoConfigurarPartida extends EventoAccion {

    private static final long serialVersionUID = 1L;

    private final ConfiguracionPartidaDTO configuracion;

    public EventoConfigurarPartida(
            ConfiguracionPartidaDTO configuracion,
            String idJugador,
            String idEvento
    ) {
        super(idJugador, idEvento);
        this.configuracion = configuracion;
    }

    public ConfiguracionPartidaDTO getConfiguracion() {
        return configuracion;
    }
}
