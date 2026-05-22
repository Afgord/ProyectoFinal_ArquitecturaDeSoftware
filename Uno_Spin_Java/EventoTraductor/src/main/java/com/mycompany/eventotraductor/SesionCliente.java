package com.mycompany.eventotraductor;

import Ejercer_Turno.MVC.ModeloJuego;
import Iniciar_Partida.MVC.ModeloLobby;

/**
 * Cableado de red del nodo publicador/consumidor listo para el MVC.
 */
public final class SesionCliente {

    private final ModeloJuego modelo;
    private final ModeloLobby modeloLobby;
    private final EventoTraductor traductor;
    private final ReceptorProcesador procesador;

    SesionCliente(ModeloJuego modelo, ModeloLobby modeloLobby,
                  EventoTraductor traductor, ReceptorProcesador procesador) {
        this.modelo = modelo;
        this.modeloLobby = modeloLobby;
        this.traductor = traductor;
        this.procesador = procesador;
    }

    public ModeloJuego getModelo() {
        return modelo;
    }

    public ModeloLobby getModeloLobby() {
        return modeloLobby;
    }

    public EventoTraductor getTraductor() {
        return traductor;
    }

    public ReceptorProcesador getProcesador() {
        return procesador;
    }
}
