package com.mycompany.eventotraductor;

import comunes.IPublicador;
import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.util.UUID;

import Crear_Partida_Lobby.Interfaces.IEventosLobby;
import Ejercer_Turno.Interfaces.IModelEventos;
import org.codedesc.ISerializador;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoAbandonarLobby;
import org.eventos.ejercer_turno.EventoBajaConexion;
import org.eventos.ejercer_turno.EventoCrearPartida;
import org.eventos.ejercer_turno.EventoGritar;
import org.eventos.ejercer_turno.EventoPasarTurno;
import org.eventos.ejercer_turno.EventoRegistroConexion;
import org.eventos.ejercer_turno.EventoResponderSolicitudInicio;
import org.eventos.ejercer_turno.EventoRobarCarta;
import org.eventos.ejercer_turno.EventoSolicitarInicio;
import org.eventos.ejercer_turno.EventoTirarCarta;
import org.eventos.ejercer_turno.EventoUnirsePartida;

/**
 * Flujo Outbound.
 *
 * Convierte las intenciones del MVC (IModelEventos del juego en curso e
 * IEventosLobby del lobby) en eventos serializables y los publica via
 * IPublicador. La seleccion de color para comodines ocurre antes (en
 * ControlJuego), por lo que la carta que llega aqui ya trae el Colores
 * elegido.
 */
public class EventoTraductor implements IModelEventos, IEventosLobby {

    private final IPublicador publicador;
    private final ISerializador<Evento> serializador;
    private final String idJugadorLocal;
    private boolean bloqueadoPorRed = false;
    private String idPartidaActual;

    public EventoTraductor(IPublicador publicador, ISerializador<Evento> serializador, String idJugadorLocal) {
        this.publicador = publicador;
        this.serializador = serializador;
        this.idJugadorLocal = idJugadorLocal;
    }

    void setBloqueadoPorRed(boolean bloqueadoPorRed) {
        this.bloqueadoPorRed = bloqueadoPorRed;
    }

    public void setIdPartidaActual(String idPartidaActual) {
        this.idPartidaActual = idPartidaActual;
    }

    public String getIdJugadorLocal() {
        return idJugadorLocal;
    }

    // === IModelEventos (juego en curso) ===

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

    // === IEventosLobby ===

    @Override
    public void emitirRegistroConexion(String nombre, String urlAvatar, String ip, int puerto) {
        Evento evt = new EventoRegistroConexion(idJugadorLocal, nombre, ip, puerto, urlAvatar, generarId());
        enviar(evt);
    }

    @Override
    public void emitirCrearPartida(String nombre, String urlAvatar) {
        JugadorDTO host = new JugadorDTO(idJugadorLocal, nombre, urlAvatar);
        Evento evt = new EventoCrearPartida(host, idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirUnirsePartida(String idPartida, String nombre, String urlAvatar) {
        JugadorDTO jugador = new JugadorDTO(idJugadorLocal, nombre, urlAvatar);
        this.idPartidaActual = idPartida;
        Evento evt = new EventoUnirsePartida(idPartida, jugador, idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirSolicitarInicio() {
        Evento evt = new EventoSolicitarInicio(idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirResponderSolicitud(boolean acepta) {
        Evento evt = new EventoResponderSolicitudInicio(acepta, idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirAbandonarLobby() {
        Evento evt = new EventoAbandonarLobby(idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirBajaConexion() {
        Evento evt = new EventoBajaConexion(idJugadorLocal, generarId());
        enviar(evt);
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }

    private void enviar(Evento evt) {
        byte[] datos = serializador.objetoABytes(evt);
        if (datos != null) {
            publicador.enviar(datos);
        }
    }
}
