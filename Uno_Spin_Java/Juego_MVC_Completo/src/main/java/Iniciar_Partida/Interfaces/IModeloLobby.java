package Iniciar_Partida.Interfaces;

import dtos.JugadorDTO;
import java.util.List;
import java.util.Set;

public interface IModeloLobby {
    void registrarObservador(ObservadorLobby o);
    String getIdJugadorLocal();
    List<JugadorDTO> getJugadoresEnSala();
    int getCapacidadMaxima();
    int getJugadoresMinimos();
    boolean isPartidaIniciada();
    String getMensajeEstado();
    Set<String> getJugadoresListos();
    boolean isJugadorLocalListo();
    boolean isJugadorListo(String id);
}
