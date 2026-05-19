package com.mycompany.eventotraductor;

import Configurar_Partida.Interfaces.IConfigurarPartidaEventos;
import comunes.IPublicador;
import dtos.CartaDTO;
import java.util.UUID;

import Ejercer_Turno.Interfaces.IModelEventos;
import dtos.ConfiguracionPartidaDTO;
import org.codedesc.ISerializador;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoConfigurarPartida;
import org.eventos.ejercer_turno.EventoGritar;
import org.eventos.ejercer_turno.EventoPasarTurno;
import org.eventos.ejercer_turno.EventoRobarCarta;
import org.eventos.ejercer_turno.EventoTirarCarta;

/**
 * Flujo Outbound.
 *
 * Convierte las intenciones del MVC (IModelEventos) en eventos serializables y
 * los publica vía IPublicador. La selección de color para comodines ocurre
 * antes (en ControlJuego), por lo que la carta que llega aquí ya trae el
 * Colores elegido.
 */
public class EventoTraductor implements IModelEventos, IConfigurarPartidaEventos {

    private final IPublicador publicador;
    private final ISerializador<Evento> serializador;
    private final String idJugadorLocal;
    private boolean bloqueadoPorRed = false;

    public EventoTraductor(IPublicador publicador, ISerializador<Evento> serializador, String idJugadorLocal) {
        this.publicador = publicador;
        this.serializador = serializador;
        this.idJugadorLocal = idJugadorLocal;
    }

    void setBloqueadoPorRed(boolean bloqueadoPorRed) {
        this.bloqueadoPorRed = bloqueadoPorRed;
    }

    @Override
    public void emitirTirarCarta(CartaDTO carta) {
        if (bloqueadoPorRed) {
            return;
        }
        Evento evt = new EventoTirarCarta(carta, idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirRobarCarta() {
        if (bloqueadoPorRed) {
            return;
        }
        Evento evt = new EventoRobarCarta(true, idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirPasarTurno() {
        if (bloqueadoPorRed) {
            return;
        }
        Evento evt = new EventoPasarTurno(true, idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirGritar() {
        if (bloqueadoPorRed) {
            return;
        }
        Evento evt = new EventoGritar(null, idJugadorLocal, generarId());
        enviar(evt);
    }

    @Override
    public void emitirConfigurarPartida(
            int rangoMinimo,
            int rangoMaximo,
            int numeroComodines,
            int numeroCartasAccion,
            int tiempoMaximoMostrarCartas
    ) {
        if (bloqueadoPorRed) {
            return;
        }

        ConfiguracionPartidaDTO configuracion = new ConfiguracionPartidaDTO(
                rangoMinimo,
                rangoMaximo,
                numeroComodines,
                numeroCartasAccion,
                tiempoMaximoMostrarCartas
        );

        Evento evt = new EventoConfigurarPartida(
                configuracion,
                idJugadorLocal,
                generarId()
        );

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
