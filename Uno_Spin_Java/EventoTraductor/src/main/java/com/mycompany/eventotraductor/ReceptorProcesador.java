package com.mycompany.eventotraductor;



import Ejercer_Turno.MVC.ModeloJuego;

import Iniciar_Partida.MVC.ModeloLobby;

import entrada.IReceptorExterno;

import org.codedesc.IDeserializador;

import org.eventos.ejercer_turno.Evento;

import org.eventos.ejercer_turno.EventoAccion;

import org.eventos.ejercer_turno.EventoActualizarTurno;

import org.eventos.ejercer_turno.EventoAnuciarGanador;

import org.eventos.ejercer_turno.EventoFallo;

import org.eventos.ejercer_turno.EventoListosIniciar;
import org.eventos.ejercer_turno.EventoPartidaIniciada;

import org.eventos.ejercer_turno.EventoResultadoGrito;

import org.eventos.ejercer_turno.EventoResultadoRuleta;

import org.eventos.ejercer_turno.EventoUnirseExitoso;



/**

 * Flujo Inbound.

 *

 * Recibe bytes desde el ComponenteConexion (a través de Receptor),

 * los deserializa y enruta los eventos de estado al ModeloJuego y ModeloLobby.

 */

public class ReceptorProcesador implements IReceptorExterno {



    public interface EscuchaEstadoInicial {

        void onEstadoRecibido();

        void onErrorConexion(String mensaje);

    }



    public interface EscuchaLobby {

        void onUnirseExitoso();

    }



    private final IDeserializador<Evento> deserializador;

    private final ModeloJuego modelo;

    private final ModeloLobby modeloLobby;

    private final String idJugadorLocal;

    private volatile EscuchaEstadoInicial escuchaEstadoInicial;

    private volatile EscuchaLobby escuchaLobby;



    public ReceptorProcesador(IDeserializador<Evento> deserializador,

                              ModeloJuego modelo,

                              ModeloLobby modeloLobby,

                              String idJugadorLocal) {

        this.deserializador = deserializador;

        this.modelo = modelo;

        this.modeloLobby = modeloLobby;

        this.idJugadorLocal = idJugadorLocal;

    }



    public void setEscuchaLobby(EscuchaLobby escucha) {

        this.escuchaLobby = escucha;

    }



    public void setEscuchaEstadoInicial(EscuchaEstadoInicial escucha) {

        this.escuchaEstadoInicial = escucha;

    }



    @Override

    public void recibir(byte[] bytes) {

        Evento evento = deserializador.bytesAObjeto(bytes);

        if (evento == null) return;



        if (evento instanceof EventoAccion accion

                && accion.getIdJugador() != null

                && accion.getIdJugador().equals(idJugadorLocal)) {

            return;

        }



        if (evento instanceof EventoActualizarTurno e) {

            modelo.aplicarActualizacion(e);

            notificarLobbyListo();

            notificarEstadoRecibido();

        } else if (evento instanceof EventoListosIniciar e) {

            modeloLobby.aplicarListosIniciar(e);

        } else if (evento instanceof EventoPartidaIniciada e) {

            modelo.aplicarPartidaIniciada(e);

            modeloLobby.aplicarPartidaIniciada();

            notificarLobbyListo();

            notificarEstadoRecibido();

        } else if (evento instanceof EventoUnirseExitoso e) {

            modelo.aplicarUnirseExitoso(e);

            modeloLobby.aplicarUnirseExitoso(e);

            notificarLobbyListo();

        } else if (evento instanceof EventoFallo e) {

            modelo.aplicarFallo(e);

            modeloLobby.aplicarFallo(e);

            if (escuchaEstadoInicial != null) {

                notificarError("El servidor rechazó la conexión: " + e.getError());

            }

        } else if (evento instanceof EventoResultadoRuleta e) {

            modelo.aplicarResultadoRuleta(e);

            notificarEstadoRecibido();

        } else if (evento instanceof EventoResultadoGrito e) {

            modelo.aplicarResultadoGrito(e);

        } else if (evento instanceof EventoAnuciarGanador e) {

            modelo.aplicarGanador(e);

        }

    }



    private void notificarLobbyListo() {

        EscuchaLobby escucha = escuchaLobby;

        if (escucha != null) {

            escuchaLobby = null;

            escucha.onUnirseExitoso();

        }

    }



    private void notificarEstadoRecibido() {

        EscuchaEstadoInicial escucha = escuchaEstadoInicial;

        if (escucha != null) {

            escuchaEstadoInicial = null;

            escucha.onEstadoRecibido();

        }

    }



    private void notificarError(String mensaje) {

        EscuchaEstadoInicial escucha = escuchaEstadoInicial;

        if (escucha != null) {

            escuchaEstadoInicial = null;

            escucha.onErrorConexion(mensaje);

        }

    }

}


