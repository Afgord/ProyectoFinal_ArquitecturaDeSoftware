package com.mycompany.eventotraductor;

import org.eventos.ejercer_turno.Evento;

/**
 * Contrato de un adaptador que sabe interpretar un Evento entrante y
 * traducirlo en llamadas al modelo MVC correspondiente. Vive del lado
 * de la frontera de red (EventoTraductor) precisamente para que los
 * modelos MVC permanezcan desacoplados del paquete org.eventos.*.
 */
public interface IAplicadorEventos {
    /**
     * @return true si el evento fue consumido por este aplicador,
     * false para que otros aplicadores lo intenten.
     */
    boolean aplicar(Evento evento);
}
