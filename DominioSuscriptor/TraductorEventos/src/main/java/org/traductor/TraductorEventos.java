/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.traductor;

import dtos.ResultadoConfiguracionDTO;
import dtos.ResultadoGritoDTO;
import dtos.ResultadoJugadaDTO;
import entidades.TipoEvento;
import fachadas.FachadaDominio;
import org.codedesc.CodeDescFactory;
import org.codedesc.IDeserializador;
import org.codedesc.ISerializador;
import org.eventos.ejercer_turno.Errores;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoAccion;
import org.eventos.ejercer_turno.EventoActualizarTurno;
import org.eventos.ejercer_turno.EventoAnuciarGanador;
import org.eventos.ejercer_turno.EventoConfiguracionRechazada;
import org.eventos.ejercer_turno.EventoConfigurarPartida;
import org.eventos.ejercer_turno.EventoFallo;
import org.eventos.ejercer_turno.EventoGritar;
import org.eventos.ejercer_turno.EventoPartidaConfigurada;
import org.eventos.ejercer_turno.EventoPasarTurno;
import org.eventos.ejercer_turno.EventoResultadoGrito;
import org.eventos.ejercer_turno.EventoResultadoRuleta;
import org.eventos.ejercer_turno.EventoRobarCarta;
import org.eventos.ejercer_turno.EventoTirarCarta;
import salida.IDispatcher;

/**
 *
 * @author lagar
 */
public class TraductorEventos {

    private final FachadaDominio fachada;
    private final IDispatcher dispatcher;
    private final IDeserializador<EventoAccion> deserializador;
    private final ISerializador<Evento> serializador;
    private final String BROKER_IP = "127.0.0.1";
    private final int BROKER_PUERTO = 5001;

    public TraductorEventos(FachadaDominio fachada, IDispatcher dispatcher) {
        this.fachada = fachada;
        this.dispatcher = dispatcher;
        this.deserializador = CodeDescFactory.crearDeserializador();
        this.serializador = CodeDescFactory.crearSerializador();
    }

    public void procesarEntrada(byte[] bytes) {
        EventoAccion eventoEntrante = deserializador.bytesAObjeto(bytes);
        if (eventoEntrante == null) {
            return;
        }

        Object resultadoDominio = null;
        if (eventoEntrante instanceof EventoTirarCarta) {
            resultadoDominio = fachada.validarYPlay(((EventoTirarCarta) eventoEntrante).getCarta());
        } else if (eventoEntrante instanceof EventoRobarCarta) {
            resultadoDominio = fachada.robarCarta();
        } else if (eventoEntrante instanceof EventoPasarTurno) {
            resultadoDominio = fachada.pasarTurno();
        } else if (eventoEntrante instanceof EventoGritar) {
            resultadoDominio = fachada.gritarUno(((EventoGritar) eventoEntrante).getJugador());
        } else if (eventoEntrante instanceof EventoConfigurarPartida) {
            EventoConfigurarPartida eventoConfigurar
                    = (EventoConfigurarPartida) eventoEntrante;

            resultadoDominio = fachada.configurarPartida(
                    eventoConfigurar.getConfiguracion()
            );
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
            } else if (dto.getGanador() != null) {
                eventoSalida = new EventoAnuciarGanador(dto.getGanador(), "FIN_PARTIDA");
            } else if (dto.getEventoTipo() == TipoEvento.RULETA_ACTIVADA) {
                org.eventos.ejercer_turno.ResultadoRuleta resultadoRuleta
                        = org.eventos.ejercer_turno.ResultadoRuleta.valueOf(dto.getResultadoRuleta().name());

                eventoSalida = new EventoResultadoRuleta(
                        resultadoRuleta,
                        dto.getEstadoJugadores(),
                        dto.getCartaCima(),
                        dto.getIdJugadorActual(),
                        "RULETA"
                );
            } else {
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
        } else if (resultado instanceof ResultadoConfiguracionDTO) {
            ResultadoConfiguracionDTO dto
                    = (ResultadoConfiguracionDTO) resultado;

            if (dto.isExito()) {
                eventoSalida = new EventoPartidaConfigurada(
                        "PARTIDA_CONFIGURADA"
                );
            } else {
                eventoSalida = new EventoConfiguracionRechazada(
                        dto.getMotivo(),
                        "CONFIGURACION_RECHAZADA"
                );
            }
        }

        if (eventoSalida != null) {
            byte[] bytesAEnviar = serializador.objetoABytes(eventoSalida);
            dispatcher.dispatch(BROKER_IP, BROKER_PUERTO, bytesAEnviar);
        }
    }
}
