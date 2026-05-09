package com.mycompany.eventotraductor;

import Crear_Partida_Lobby.MVC.ModeloLobby;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoEstadoAceptacionActualizado;
import org.eventos.ejercer_turno.EventoLobbyActualizado;
import org.eventos.ejercer_turno.EventoPartidaIniciada;
import org.eventos.ejercer_turno.EventoSolicitudInicioRecibida;

/**
 * Adaptador inbound para el ModeloLobby: traduce los Evento* del flujo
 * de lobby en llamadas aplicar* del modelo (con DTOs/primitivos). De
 * este modo el MVC del lobby no conoce el paquete org.eventos.*.
 */
public class AplicadorEventosLobby implements IAplicadorEventos {

    private final ModeloLobby modelo;

    public AplicadorEventosLobby(ModeloLobby modelo) {
        this.modelo = modelo;
    }

    @Override
    public boolean aplicar(Evento evento) {
        if (evento instanceof EventoLobbyActualizado e) {
            modelo.aplicarLobbyActualizado(e.getIdPartida(), e.getIdHost(), e.getJugadores());
            return true;
        }
        if (evento instanceof EventoSolicitudInicioRecibida e) {
            modelo.aplicarSolicitudInicio(e.getIdJugadorSolicitante(), e.getNombreSolicitante(),
                    e.getAceptaciones());
            return true;
        }
        if (evento instanceof EventoEstadoAceptacionActualizado e) {
            modelo.aplicarEstadoAceptacion(e.getAceptaciones());
            return true;
        }
        if (evento instanceof EventoPartidaIniciada e) {
            modelo.aplicarPartidaIniciada(e.getIdPartida(), e.getJugadores(),
                    e.getDescarteInicial(), e.getIdJugadorTurnoActual());
            return true;
        }
        return false;
    }
}
