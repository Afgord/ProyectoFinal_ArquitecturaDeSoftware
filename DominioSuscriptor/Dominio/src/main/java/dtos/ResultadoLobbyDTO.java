package dtos;

import java.io.Serializable;
import java.util.List;

/**
 * Resultado de una operacion sobre el lobby (crear / unirse / abandonar).
 */
public class ResultadoLobbyDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean exito;
    private final String mensajeError;
    private final String idPartida;
    private final String idHost;
    private final List<JugadorDTO> jugadores;

    public ResultadoLobbyDTO(boolean exito, String mensajeError, String idPartida,
                             String idHost, List<JugadorDTO> jugadores) {
        this.exito = exito;
        this.mensajeError = mensajeError;
        this.idPartida = idPartida;
        this.idHost = idHost;
        this.jugadores = jugadores;
    }

    public boolean isExito() { return exito; }
    public String getMensajeError() { return mensajeError; }
    public String getIdPartida() { return idPartida; }
    public String getIdHost() { return idHost; }
    public List<JugadorDTO> getJugadores() { return jugadores; }
}
