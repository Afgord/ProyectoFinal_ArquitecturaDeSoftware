package com.mycompany.eventotraductor;

import dtos.CartaDTO;
import java.awt.Color;
import java.util.UUID;

import Ejercer_Turno.Interfaces.IModeloAcciones;;
import org.code.infraestructura.red.ISerializador;
import org.code.infraestructura.red.IDeserializador;
import org.eventos.ejercer_turno.*;


/**
 * Clase 1: EventoTraductor (Flujo Outbound)
 * Convierte las acciones locales en eventos de red.
 */
public class EventoTraductor implements IModelEvents {

    private final IPublicador publicador;
    private final ISerializador<Evento> serializador;
    private final String idJugadorLocal; // Identificador de ESTE cliente
    private boolean bloqueadoPorRed = false;

    public EventoTraductor(IPublicador publicador, ISerializador<Evento> serializador, String idJugadorLocal) {
        this.publicador = publicador;
        this.serializador = serializador;
        this.idJugadorLocal = idJugadorLocal;
    }

    @Override
    public void emitirTirarCarta(CartaDTO carta) {
        if (!bloqueadoPorRed) {
            String idEvento = generarId();
            Evento evt = new EventoTirarCarta(carta, idJugadorLocal, idEvento);
            enviar(evt);
        }
    }

    @Override
    public void emitirTirarCartaNegra(CartaDTO carta, Color color, String nombreColor) {
        if (!bloqueadoPorRed) {
            // Como afirmas que CartaDTO tiene el color, reutilizamos EventoTirarCarta
            // Si esto falla en el futuro, es porque tu DTO no encapsulaba el color realmente.
            String idEvento = generarId();
            Evento evt = new EventoTirarCarta(carta, idJugadorLocal, idEvento);
            enviar(evt);
        }
    }

    @Override
    public void emitirRobarCarta() {
        if (!bloqueadoPorRed) {
            String idEvento = generarId();
            Evento evt = new EventoRobarCarta(true, idJugadorLocal, idEvento);
            enviar(evt);
        }
    }

    @Override
    public void emitirAplicarCastigo() {
        if (!bloqueadoPorRed) {
            System.err.println("Falta implementar EventoAplicarCastigo en el paquete de eventos.");
        }
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
