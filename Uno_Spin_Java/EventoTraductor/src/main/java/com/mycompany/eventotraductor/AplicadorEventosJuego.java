package com.mycompany.eventotraductor;

import Ejercer_Turno.MVC.ModeloJuego;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoActualizarTurno;
import org.eventos.ejercer_turno.EventoAnuciarGanador;
import org.eventos.ejercer_turno.EventoFallo;
import org.eventos.ejercer_turno.EventoResultadoGrito;
import org.eventos.ejercer_turno.EventoResultadoRuleta;

/**
 * Adaptador inbound para el ModeloJuego: traduce los Evento* del flujo
 * de juego en llamadas aplicar* del modelo (con DTOs/primitivos). De
 * este modo el MVC permanece desacoplado del paquete org.eventos.*.
 */
public class AplicadorEventosJuego implements IAplicadorEventos {

    private final ModeloJuego modelo;

    public AplicadorEventosJuego(ModeloJuego modelo) {
        this.modelo = modelo;
    }

    @Override
    public boolean aplicar(Evento evento) {
        if (evento instanceof EventoActualizarTurno e) {
            modelo.aplicarActualizacion(e.getJugadores(), e.getCartaEnCima(), e.getIdJugadorTurnoActual());
            return true;
        }
        if (evento instanceof EventoFallo) {
            modelo.aplicarFallo();
            return true;
        }
        if (evento instanceof EventoResultadoRuleta e) {
            modelo.aplicarResultadoRuleta(e.getJugadores(), e.getCartaEnCima(), e.getIdJugadorTurnoActual());
            return true;
        }
        if (evento instanceof EventoResultadoGrito e) {
            modelo.aplicarResultadoGrito(e.getEstadoJugadores());
            return true;
        }
        if (evento instanceof EventoAnuciarGanador e) {
            modelo.aplicarGanador(e.getGanador() != null ? e.getGanador().getNombre() : null);
            return true;
        }
        return false;
    }
}
