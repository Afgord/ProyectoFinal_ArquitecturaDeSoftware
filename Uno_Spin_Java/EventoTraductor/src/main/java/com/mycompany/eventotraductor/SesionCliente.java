package com.mycompany.eventotraductor;

import Ejercer_Turno.MVC.ModeloJuego;

/**
 * Cableado de red del nodo publicador/consumidor listo para el MVC.
 */
public final class SesionCliente {

    private final ModeloJuego modelo;
    private final EventoTraductor traductor;
    private final ReceptorProcesador procesador;

    SesionCliente(ModeloJuego modelo, EventoTraductor traductor, ReceptorProcesador procesador) {
        this.modelo = modelo;
        this.traductor = traductor;
        this.procesador = procesador;
    }

    public ModeloJuego getModelo() {
        return modelo;
    }

    public EventoTraductor getTraductor() {
        return traductor;
    }

    public ReceptorProcesador getProcesador() {
        return procesador;
    }
}
