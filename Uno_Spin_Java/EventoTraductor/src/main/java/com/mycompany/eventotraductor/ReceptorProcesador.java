package com.mycompany.eventotraductor;

import Ejercer_Turno.Interfaces.IReceptorEstadoJuego;
import Iniciar_Partida.Interfaces.IReceptorEstadoLobby;
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
 * Recibe bytes desde el ComponenteConexion, deserializa eventos
 * y actualiza los modelos MVC vía interfaces de receptor.
 */
public class ReceptorProcesador implements IReceptorExterno {

    public interface EscuchaEstadoInicial {
        void onErrorConexion(String mensaje);
    }

    public interface EscuchaLobby {
        void onUnirseExitoso();
    }

    public interface EscuchaPartidaIniciada {
        void onPartidaIniciada();
    }

    private final IDeserializador<Evento> deserializador;
    private final IReceptorEstadoJuego receptorJuego;
    private final IReceptorEstadoLobby receptorLobby;
    private final String idJugadorLocal;

    private volatile EscuchaEstadoInicial escuchaEstadoInicial;
    private volatile EscuchaLobby escuchaLobby;
    private volatile EscuchaPartidaIniciada escuchaPartidaIniciada;

    private volatile boolean lobbyUiListo;
    private volatile boolean tableroPendiente;

    public ReceptorProcesador(IDeserializador<Evento> deserializador,
                              IReceptorEstadoJuego receptorJuego,
                              IReceptorEstadoLobby receptorLobby,
                              String idJugadorLocal) {
        this.deserializador = deserializador;
        this.receptorJuego = receptorJuego;
        this.receptorLobby = receptorLobby;
        this.idJugadorLocal = idJugadorLocal;
    }

    public void setEscuchaLobby(EscuchaLobby escucha) {
        this.escuchaLobby = escucha;
    }

    public void setEscuchaEstadoInicial(EscuchaEstadoInicial escucha) {
        this.escuchaEstadoInicial = escucha;
    }

    public void setEscuchaPartidaIniciada(EscuchaPartidaIniciada escucha) {
        this.escuchaPartidaIniciada = escucha;
    }

    public void marcarLobbyUiListo() {
        lobbyUiListo = true;
        if (tableroPendiente) {
            tableroPendiente = false;
            notificarPartidaIniciada();
        }
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
            receptorJuego.aplicarActualizacion(e);
            notificarLobbyListo();
            solicitarTransicionTablero();
        } else if (evento instanceof EventoListosIniciar e) {
            receptorLobby.aplicarListosIniciar(e);
        } else if (evento instanceof EventoPartidaIniciada e) {
            receptorJuego.aplicarPartidaIniciada(e);
            receptorLobby.aplicarPartidaIniciada();
            notificarLobbyListo();
            solicitarTransicionTablero();
        } else if (evento instanceof EventoUnirseExitoso e) {
            receptorJuego.aplicarUnirseExitoso(e);
            receptorLobby.aplicarUnirseExitoso(e);
            notificarLobbyListo();
        } else if (evento instanceof EventoFallo e) {
            receptorJuego.aplicarFallo(e);
            receptorLobby.aplicarFallo(e);
            if (escuchaEstadoInicial != null) {
                notificarError("El servidor rechazó la conexión: " + e.getError());
            }
        } else if (evento instanceof EventoResultadoRuleta e) {
            receptorJuego.aplicarResultadoRuleta(e);
        } else if (evento instanceof EventoResultadoGrito e) {
            receptorJuego.aplicarResultadoGrito(e);
        } else if (evento instanceof EventoAnuciarGanador e) {
            receptorJuego.aplicarGanador(e);
        }
    }

    private void solicitarTransicionTablero() {
        if (lobbyUiListo) {
            notificarPartidaIniciada();
        } else {
            tableroPendiente = true;
        }
    }

    private void notificarLobbyListo() {
        EscuchaLobby escucha = escuchaLobby;
        if (escucha != null) {
            escuchaLobby = null;
            escucha.onUnirseExitoso();
        }
    }

    private void notificarPartidaIniciada() {
        EscuchaPartidaIniciada escucha = escuchaPartidaIniciada;
        if (escucha != null) {
            escuchaPartidaIniciada = null;
            escucha.onPartidaIniciada();
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
