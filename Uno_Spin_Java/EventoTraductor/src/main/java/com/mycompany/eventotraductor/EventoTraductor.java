package com.mycompany.eventotraductor;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import Ejercer_Turno.Interfaces.IModelEventos;
import Iniciar_Partida.Interfaces.IModelEventosLobby;
import java.util.UUID;
import org.codedesc.ISerializador;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoGritar;
import org.eventos.ejercer_turno.EventoIniciarPartida;
import org.eventos.ejercer_turno.EventoPasarTurno;
import org.eventos.ejercer_turno.EventoRobarCarta;
import org.eventos.ejercer_turno.EventoTirarCarta;
import org.eventos.ejercer_turno.EventoUnirsePartida;
import salida.IDispatcher;

/**
 * Flujo Outbound.
 *
 * Convierte las intenciones del MVC (IModelEventos) en eventos serializables
 * y los publica al broker vía IDispatcher.
 */
public class EventoTraductor implements IModelEventos, IModelEventosLobby {

    private final IDispatcher dispatcher;
    private final String hostBroker;
    private final int puertoBroker;
    private final ISerializador<Evento> serializador;
    private final String idJugadorLocal;
    private boolean bloqueadoPorRed = false;

    public EventoTraductor(IDispatcher dispatcher, String hostBroker, int puertoBroker,
                           ISerializador<Evento> serializador, String idJugadorLocal) {
        this.dispatcher = dispatcher;
        this.hostBroker = hostBroker;
        this.puertoBroker = puertoBroker;
        this.serializador = serializador;
        this.idJugadorLocal = idJugadorLocal;
    }

    public void setRedHabilitada(boolean habilitada) {
        this.bloqueadoPorRed = !habilitada;
    }

    public void emitirUnirsePartida(JugadorDTO jugador) {
        Evento evt = new EventoUnirsePartida(idJugadorLocal, generarId(), jugador);
        enviar(evt);
    }

    @Override
    public void emitirIniciarPartida() {
        Evento evt = new EventoIniciarPartida(idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirTirarCarta(CartaDTO carta) {
        if (bloqueadoPorRed) return;
        Evento evt = new EventoTirarCarta(carta, idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirRobarCarta() {
        if (bloqueadoPorRed) return;
        Evento evt = new EventoRobarCarta(true, idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirPasarTurno() {
        if (bloqueadoPorRed) return;
        Evento evt = new EventoPasarTurno(true, idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirGritar() {
        if (bloqueadoPorRed) return;
        Evento evt = new EventoGritar(null, idJugadorLocal, generarId());
        enviar(evt);
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }

    private void enviar(Evento evt) {
        byte[] datos = serializador.objetoABytes(evt);
        if (datos != null) {
            dispatcher.dispatch(hostBroker, puertoBroker, datos);
        }
    }
}
