package com.mycompany.eventotraductor;

import Configurar_Partida.MVC.ModeloConfigurarPartida;
import Ejercer_Turno.MVC.ModeloJuego;
import entrada.IReceptorExterno;
import org.codedesc.IDeserializador;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoAccion;
import org.eventos.ejercer_turno.EventoActualizarTurno;
import org.eventos.ejercer_turno.EventoAnuciarGanador;
import org.eventos.ejercer_turno.EventoConfiguracionRechazada;
import org.eventos.ejercer_turno.EventoFallo;
import org.eventos.ejercer_turno.EventoPartidaConfigurada;
import org.eventos.ejercer_turno.EventoResultadoGrito;
import org.eventos.ejercer_turno.EventoResultadoRuleta;

/**
 * Flujo Inbound.
 *
 * Recibe bytes desde el ComponenteConexion (a través de Receptor), los
 * deserializa y enruta los eventos de estado al ModeloJuego.
 *
 * Solo procesa eventos de estado (los que produce el subscriptor Dominio). Si
 * el broker eventualmente nos reenviase un evento de intención propio, el
 * filtro de eco lo descarta.
 */
public class ReceptorProcesador implements IReceptorExterno {

    private final IDeserializador<Evento> deserializador;
    private final ModeloJuego modelo;
    private final ModeloConfigurarPartida modeloConfiguracion;
    private final String idJugadorLocal;

    public ReceptorProcesador(IDeserializador<Evento> deserializador,
            ModeloJuego modelo,
            ModeloConfigurarPartida modeloConfiguracion,
            String idJugadorLocal) {
        this.deserializador = deserializador;
        this.modelo = modelo;
        this.modeloConfiguracion = modeloConfiguracion;
        this.idJugadorLocal = idJugadorLocal;
    }

    @Override
    public void recibir(byte[] bytes) {
        Evento evento = deserializador.bytesAObjeto(bytes);
        if (evento == null) {
            return;
        }

        if (evento instanceof EventoAccion accion
                && accion.getIdJugador() != null
                && accion.getIdJugador().equals(idJugadorLocal)) {
            return;
        }

        if (evento instanceof EventoActualizarTurno e) {
            modelo.aplicarActualizacion(e);
        } else if (evento instanceof EventoFallo e) {
            modelo.aplicarFallo(e);
        } else if (evento instanceof EventoResultadoRuleta e) {
            modelo.aplicarResultadoRuleta(e);
        } else if (evento instanceof EventoResultadoGrito e) {
            modelo.aplicarResultadoGrito(e);
        } else if (evento instanceof EventoAnuciarGanador e) {
            modelo.aplicarGanador(e);
        } else if (evento instanceof EventoPartidaConfigurada e) {
            modeloConfiguracion.aplicarPartidaConfigurada(e);
        } else if (evento instanceof EventoConfiguracionRechazada e) {
            modeloConfiguracion.aplicarConfiguracionRechazada(e);
        }
    }
}
