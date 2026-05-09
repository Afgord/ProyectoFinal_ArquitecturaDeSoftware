package org.eventos.ejercer_turno;

import dtos.AceptacionDTO;
import java.util.List;

/**
 * Notificación broadcast: cambia el estado de aceptación de algún jugador
 * tras la solicitud de inicio. Permite que todos los clientes refresquen
 * el modal "El jugador X ha solicitado iniciar partida".
 */
public class EventoEstadoAceptacionActualizado extends Evento {
    private static final long serialVersionUID = 1L;
    private final List<AceptacionDTO> aceptaciones;

    public EventoEstadoAceptacionActualizado(List<AceptacionDTO> aceptaciones, String idEvento) {
        super(idEvento);
        this.aceptaciones = aceptaciones;
    }

    public List<AceptacionDTO> getAceptaciones() { return aceptaciones; }
}
