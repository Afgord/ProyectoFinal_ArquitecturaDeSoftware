package org.eventos.ejercer_turno;

import dtos.AceptacionDTO;
import java.util.List;

/**
 * Notificación broadcast: el host solicitó iniciar la partida. Lleva la
 * lista inicial de aceptaciones (todos PENDIENTE excepto el solicitante,
 * que ya está ACEPTADO).
 */
public class EventoSolicitudInicioRecibida extends Evento {
    private static final long serialVersionUID = 1L;
    private final String idJugadorSolicitante;
    private final String nombreSolicitante;
    private final List<AceptacionDTO> aceptaciones;

    public EventoSolicitudInicioRecibida(String idJugadorSolicitante, String nombreSolicitante,
                                         List<AceptacionDTO> aceptaciones, String idEvento) {
        super(idEvento);
        this.idJugadorSolicitante = idJugadorSolicitante;
        this.nombreSolicitante = nombreSolicitante;
        this.aceptaciones = aceptaciones;
    }

    public String getIdJugadorSolicitante() { return idJugadorSolicitante; }
    public String getNombreSolicitante() { return nombreSolicitante; }
    public List<AceptacionDTO> getAceptaciones() { return aceptaciones; }
}
