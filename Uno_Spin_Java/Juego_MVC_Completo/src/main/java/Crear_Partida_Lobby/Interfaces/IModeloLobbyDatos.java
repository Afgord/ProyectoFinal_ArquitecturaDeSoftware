package Crear_Partida_Lobby.Interfaces;

import dtos.AceptacionDTO;
import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.util.List;

/**
 * Contrato de lectura del modelo del lobby. Las vistas lo consultan
 * para pintar; el modelo solo se modifica via aplicar* desde la red.
 */
public interface IModeloLobbyDatos {

    enum Estado { ACCESO, EN_LOBBY, SOLICITANDO_INICIO, INICIADA, ABANDONADO }

    String getIdJugadorLocal();
    String getIdPartida();
    String getIdHost();
    boolean isSoyHost();
    List<JugadorDTO> getJugadores();
    Estado getEstado();
    String getNombreSolicitante();
    String getIdJugadorSolicitante();
    List<AceptacionDTO> getAceptaciones();
    List<JugadorDTO> getJugadoresIniciales();
    CartaDTO getDescarteInicial();
    String getIdJugadorTurnoInicial();
    String getMensajeError();

    void registrarObservador(ObservadorLobby o);
}
