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

        } else if (eventoEntrante instanceof EventoUnirsePartida) {
            resultadoDominio = fachada.unirseAPartida(
                ((EventoUnirsePartida) eventoEntrante).getJugador()
            );
        }

        if (resultadoDominio != null) {
            enviarRespuesta(resultadoDominio);
        }
    }

    private void enviarRespuesta(Object resultado) {
        Evento eventoSalida = null;

        if (resultado instanceof ResultadoJugadaDTO) {
            eventoSalida = traducirJugada((ResultadoJugadaDTO) resultado);

        } else if (resultado instanceof ResultadoGritoDTO) {
            eventoSalida = traducirGrito((ResultadoGritoDTO) resultado);

        } else if (resultado instanceof ResultadoUnirseDTO) {
            eventoSalida = traducirUnirse((ResultadoUnirseDTO) resultado);
        }

        if (eventoSalida != null) {
            byte[] bytesAEnviar = serializador.objetoABytes(eventoSalida);
            dispatcher.dispatch(BROKER_IP, BROKER_PUERTO, bytesAEnviar);
        }
    }

    private Evento traducirJugada(ResultadoJugadaDTO dto) {
        if (!dto.isExito()) {
            Errores tipoError = (dto.getEventoTipo() == TipoEvento.ERROR)
                ? Errores.ERROR_DESCARTE
                : Errores.ERROR_GENERICO;

            return new EventoFallo(tipoError, "OPERACION_INVALIDA");
        }

        if (dto.getGanador() != null) {
            return new EventoAnuciarGanador(dto.getGanador(), "FIN_PARTIDA");
        }

        if (dto.getEventoTipo() == TipoEvento.RULETA_ACTIVADA) {
            org.eventos.ejercer_turno.ResultadoRuleta resultadoRuleta =
                org.eventos.ejercer_turno.ResultadoRuleta.valueOf(
                    dto.getResultadoRuleta().name()
                );

            return new EventoResultadoRuleta(
                resultadoRuleta,
                dto.getEstadoJugadores(),
                dto.getCartaCima(),
                dto.getIdJugadorActual(),
                "RULETA"
            );
        }

        return new EventoActualizarTurno(
            dto.getEstadoJugadores(),
            dto.getCartaCima(),
            dto.getIdJugadorActual(),
            "ACT_SISTEMA"
        );
    }

    private Evento traducirGrito(ResultadoGritoDTO dto) {
        if (!dto.isExitoGrito()) {
            return new EventoFallo(Errores.GRITO_INVALIDO, "GRITO_NO_VALIDO");
        }

        return new EventoResultadoGrito(
            dto.isExitoGrito(),
            dto.getIdCastigado(),
            dto.getEstadoJugadores(),
            "RES_GRITO"
        );
    }

    private Evento traducirUnirse(ResultadoUnirseDTO dto) {
        return switch (dto.getEventoTipo()) {
            case UNIRSE_EXITOSO -> new EventoUnirseExitoso(
                dto.getJugadorUnido(),
                dto.getJugadoresEnSala(),
                "SALA_ACTUALIZADA"
            );

            case PARTIDA_LLENA -> new EventoFallo(
                Errores.SALA_LLENA,
                "SALA_LLENA"
            );

            case PARTIDA_EN_CURSO -> new EventoFallo(
                Errores.PARTIDA_EN_CURSO,
                "PARTIDA_EN_CURSO"
            );

            default -> new EventoFallo(
                Errores.ERROR_GENERICO,
                "ERROR_UNIRSE"
            );
        };
    }
}