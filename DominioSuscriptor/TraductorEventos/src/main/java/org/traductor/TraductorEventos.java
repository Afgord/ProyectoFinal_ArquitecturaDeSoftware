/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.traductor;

import salida.IDispatcher;
import fachadas.FachadaDominio;
import org.codedesc.*;
import org.eventos.ejercer_turno.*;
import dtos.*;
import entidades.TipoEvento;
/**
 * 
 * @author lagar
 */
public class TraductorEventos {
    private final FachadaDominio fachada;
    private final IDispatcher dispatcher;
    private final IDeserializador<EventoAccion> deserializador;
    private final ISerializador<Evento> serializador;
    private final String BROKER_IP = "192.168.100.97";
    private final int BROKER_PUERTO = 5001;

    public TraductorEventos(FachadaDominio fachada, IDispatcher dispatcher) {
        this.fachada = fachada;
        this.dispatcher = dispatcher;
        this.deserializador = CodeDescFactory.crearDeserializador();
        this.serializador = CodeDescFactory.crearSerializador();
    }
    public void procesarEntrada(byte[] bytes) {
        EventoAccion eventoEntrante = deserializador.bytesAObjeto(bytes);
        if (eventoEntrante == null) return;

        Object resultadoDominio = null;
        if (eventoEntrante instanceof EventoTirarCarta) {
            resultadoDominio = fachada.validarYPlay(((EventoTirarCarta) eventoEntrante).getCarta());
        } else if (eventoEntrante instanceof EventoRobarCarta) {
            resultadoDominio = fachada.robarCarta();
        } else if (eventoEntrante instanceof EventoPasarTurno) {
            resultadoDominio = fachada.pasarTurno();
        } else if (eventoEntrante instanceof EventoGritar) {
            resultadoDominio = fachada.gritarUno(((EventoGritar) eventoEntrante).getJugador());
        }

        if (resultadoDominio != null) {
            enviarRespuesta(resultadoDominio);
        }
    }

    private void enviarRespuesta(Object resultado) {
        Evento eventoSalida = null;

        if (resultado instanceof ResultadoJugadaDTO) {
            ResultadoJugadaDTO dto = (ResultadoJugadaDTO) resultado;
            
            if (!dto.isExito()) {
                Errores tipoError = (dto.getEventoTipo() == TipoEvento.ERROR) 
                                    ? Errores.ERROR_DESCARTE 
                                    : Errores.ERROR_GENERICO;
                eventoSalida = new EventoFallo(tipoError, "OPERACION_INVALIDA");
            } 
            else if (dto.getGanador() != null) {
                eventoSalida = new EventoAnuciarGanador(dto.getGanador(), "FIN_PARTIDA");
            }
            else if (dto.getEventoTipo() == TipoEvento.RULETA_ACTIVADA) {
                org.eventos.ejercer_turno.ResultadoRuleta resultadoRuleta = 
                    org.eventos.ejercer_turno.ResultadoRuleta.valueOf(dto.getResultadoRuleta().name());

                eventoSalida = new EventoResultadoRuleta(
                    resultadoRuleta,
                    dto.getEstadoJugadores(),
                    dto.getCartaCima(),
                    dto.getIdJugadorActual(),
                    "RULETA"
                );
            }
            else {
                eventoSalida = new EventoActualizarTurno(
                    dto.getEstadoJugadores(), 
                    dto.getCartaCima(), 
                    dto.getIdJugadorActual(),
                    "ACT_SISTEMA"
                );
            }
        } else if (resultado instanceof ResultadoGritoDTO) {
            ResultadoGritoDTO dto = (ResultadoGritoDTO) resultado;
            
            if (!dto.isExitoGrito()) {
                eventoSalida = new EventoFallo(Errores.GRITO_INVALIDO, "GRITO_NO_VALIDO");
            } else {
                eventoSalida = new EventoResultadoGrito(
                    dto.isExitoGrito(), 
                    dto.getIdCastigado(), 
                    dto.getEstadoJugadores(),
                    "RES_GRITO"
                );
            }
        }

        if (eventoSalida != null) {
            byte[] bytesAEnviar = serializador.objetoABytes(eventoSalida);
            dispatcher.dispatch(BROKER_IP, BROKER_PUERTO, bytesAEnviar);
        }
    }
}