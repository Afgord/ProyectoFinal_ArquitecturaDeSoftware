package Iniciar_Partida.Interfaces;

import org.eventos.ejercer_turno.EventoFallo;
import org.eventos.ejercer_turno.EventoListosIniciar;
import org.eventos.ejercer_turno.EventoUnirseExitoso;

/**
 * Contrato Inbound del lobby.
 * El ReceptorProcesador actualiza el modelo solo a través de esta interfaz.
 */
public interface IReceptorEstadoLobby {
    void aplicarListosIniciar(EventoListosIniciar e);
    void aplicarUnirseExitoso(EventoUnirseExitoso e);
    void aplicarPartidaIniciada();
    void aplicarFallo(EventoFallo e);
}
