package com.mycompany.eventotraductor;

import entrada.IReceptorExterno;
import java.util.List;
import org.codedesc.IDeserializador;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoAccion;

/**
 * Flujo Inbound.
 *
 * Recibe bytes desde el ComponenteConexion (a traves de Receptor),
 * los deserializa y los ofrece a una lista ordenada de aplicadores
 * (AplicadorEventosLobby, AplicadorEventosJuego, ...). Cada aplicador
 * decide si el evento le concierne y lo traduce a llamadas aplicar*
 * sobre el modelo MVC correspondiente.
 *
 * Antes de propagar, descarta los ecos de eventos de accion propios
 * (el broker podria reenviarnos accidentalmente nuestro propio
 * EventoAccion en algun camino del directorio).
 */
public class ReceptorProcesador implements IReceptorExterno {

    private final IDeserializador<Evento> deserializador;
    private final List<IAplicadorEventos> aplicadores;
    private final String idJugadorLocal;

    public ReceptorProcesador(IDeserializador<Evento> deserializador,
                              List<IAplicadorEventos> aplicadores,
                              String idJugadorLocal) {
        this.deserializador = deserializador;
        this.aplicadores = aplicadores;
        this.idJugadorLocal = idJugadorLocal;
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

        for (IAplicadorEventos a : aplicadores) {
            if (a.aplicar(evento)) {
                return;
            }
        }
    }
}
