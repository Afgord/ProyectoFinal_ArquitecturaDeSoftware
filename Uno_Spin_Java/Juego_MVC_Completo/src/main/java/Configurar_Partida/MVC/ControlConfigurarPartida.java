/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configurar_Partida.MVC;

import Configurar_Partida.Interfaces.IConfigurarPartidaEventos;

/**
 * Controlador del CU1 - Configurar partida.
 *
 * Recibe la intención de la vista y la delega al traductor de eventos
 * mediante un contrato.
 */
public class ControlConfigurarPartida {

    private final IConfigurarPartidaEventos eventos;

    public ControlConfigurarPartida(IConfigurarPartidaEventos eventos) {
        this.eventos = eventos;
    }

    public void solicitarConfiguracion(
            int rangoMinimo,
            int rangoMaximo,
            int numeroComodines,
            int numeroCartasAccion,
            int tiempoMaximoMostrarCartas
    ) {
        eventos.emitirConfigurarPartida(
                rangoMinimo,
                rangoMaximo,
                numeroComodines,
                numeroCartasAccion,
                tiempoMaximoMostrarCartas
        );
    }
}