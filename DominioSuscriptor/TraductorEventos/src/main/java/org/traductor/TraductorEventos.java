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
    private final String brokerIp;
    private final int BROKER_PUERTO = 5001;

    public TraductorEventos(FachadaDominio fachada, IDispatcher dispatcher, String brokerIp) {
        this.fachada = fachada;
        this.dispatcher = dispatcher;
        this.brokerIp = brokerIp;
        this.deserializador = CodeDescFactory.crearDeserializador();
        this.serializador = CodeDescFactory.crearSerializador();
    }

    public void procesarEntrada(byte[] bytes) {
        EventoAccion eventoEntrante = deserializador.bytesAObjeto(bytes);
        if (eventoEntrante == null) return;

        Object resultadoDominio = null;

        if (eventoEntrante instanceof EventoTirarCarta e) {
            resultadoDominio = fachada.validarYPlay(e.getIdJugador(), e.getCarta());

        } else if (eventoEntrante instanceof EventoRobarCarta e) {
            resultadoDominio = fachada.robarCarta(e.getIdJugador());

        } else if (eventoEntrante instanceof EventoPasarTurno e) {
            resultadoDominio = fachada.pasarTurno(e.getIdJugador());

        } else if (eventoEntrante instanceof EventoGritar) {
            resultadoDominio = fachada.gritarUno(((EventoGritar) eventoEntrante).getJugador());

        } else if (eventoEntrante instanceof EventoUnirsePartida) {
            resultadoDominio = fachada.unirseAPartida(
                ((EventoUnirsePartida) eventoEntrante).getJugador()
            );

        } else if (eventoEntrante instanceof EventoIniciarPartida e) {
            resultadoDominio = fachada.iniciarPartida(e.getIdJugador());
        }

        if (resultadoDominio != null) {
            enviarRespuestas(resultadoDominio);
        }
    }

    private void enviarRespuestas(Object resultado) {
        if (resultado instanceof ResultadoUnirseDTO dto) {
            enviarEvento(traducirUnirse(dto));
            if (dto.hayInicioAutomatico()) {
                enviarEvento(traducirInicio(dto.getInicioAutomatico()));
            }
            return;
        }

        Evento eventoSalida = traducirResultado(resultado);
        enviarEvento(eventoSalida);
    }

    private Evento traducirResultado(Object resultado) {
        if (resultado instanceof ResultadoJugadaDTO jugada) {
            return traducirJugada(jugada);
        }
        if (resultado instanceof ResultadoGritoDTO grito) {
            return traducirGrito(grito);
        }
        if (resultado instanceof ResultadoIniciarPartidaDTO inicio) {
            return traducirInicio(inicio);
        }
        return null;
    }

    private void enviarEvento(Evento evento) {
        if (evento == null) {
            return;
        }
        byte[] bytesAEnviar = serializador.objetoABytes(evento);
        dispatcher.dispatch(brokerIp, BROKER_PUERTO, bytesAEnviar);
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
            case UNIRSE_EXITOSO -> {
                if (dto.getCartaCima() != null && dto.getIdJugadorTurnoActual() != null) {
                    yield new EventoActualizarTurno(
                        dto.getJugadoresEnSala(),
                        dto.getCartaCima(),
                        dto.getIdJugadorTurnoActual(),
                        "SYNC"
                    );
                }
                yield new EventoUnirseExitoso(
                    dto.getJugadorUnido(),
                    dto.getJugadoresEnSala(),
                    "SALA_ACTUALIZADA"
                );
            }

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

    private Evento traducirInicio(ResultadoIniciarPartidaDTO dto) {
        if (dto.getEventoTipo() == TipoEvento.INICIO_PENDIENTE) {
            return new EventoListosIniciar(
                dto.getJugadoresListos(),
                dto.getTotalJugadoresEnSala(),
                "LISTOS_INICIAR"
            );
        }

        if (!dto.isExito()) {
            Errores error = switch (dto.getEventoTipo()) {
                case PARTIDA_EN_CURSO -> Errores.PARTIDA_YA_INICIADA;
                case INICIO_RECHAZADO -> Errores.JUGADORES_INSUFICIENTES;
                default -> Errores.ERROR_GENERICO;
            };

            return new EventoFallo(error, "INICIO_RECHAZADO");
        }

        return new EventoPartidaIniciada(
            dto.getEstadoJugadores(),
            dto.getCartaCima(),
            dto.getIdJugadorTurnoActual(),
            "PARTIDA_INICIADA"
        );
    }
}