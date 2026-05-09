package dtos;

import java.io.Serializable;
import java.util.List;

/**
 * Resultado de una operacion sobre la solicitud de inicio (solicitar /
 * responder). Si la respuesta dispara el inicio de partida, partida no
 * es null.
 */
public class ResultadoSolicitudDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean exito;
    private final String mensajeError;
    private final boolean nuevaSolicitud;
    private final String idJugadorSolicitante;
    private final String nombreSolicitante;
    private final List<AceptacionDTO> aceptaciones;
    private final EstadoPartidaInicialDTO partida;

    public ResultadoSolicitudDTO(boolean exito, String mensajeError, boolean nuevaSolicitud,
                                 String idJugadorSolicitante, String nombreSolicitante,
                                 List<AceptacionDTO> aceptaciones, EstadoPartidaInicialDTO partida) {
        this.exito = exito;
        this.mensajeError = mensajeError;
        this.nuevaSolicitud = nuevaSolicitud;
        this.idJugadorSolicitante = idJugadorSolicitante;
        this.nombreSolicitante = nombreSolicitante;
        this.aceptaciones = aceptaciones;
        this.partida = partida;
    }

    public boolean isExito() { return exito; }
    public String getMensajeError() { return mensajeError; }
    public boolean isNuevaSolicitud() { return nuevaSolicitud; }
    public String getIdJugadorSolicitante() { return idJugadorSolicitante; }
    public String getNombreSolicitante() { return nombreSolicitante; }
    public List<AceptacionDTO> getAceptaciones() { return aceptaciones; }
    public EstadoPartidaInicialDTO getPartida() { return partida; }
}
