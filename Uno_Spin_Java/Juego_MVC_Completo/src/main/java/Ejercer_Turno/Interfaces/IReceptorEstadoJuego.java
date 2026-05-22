package Ejercer_Turno.Interfaces;

import org.eventos.ejercer_turno.EventoActualizarTurno;
import org.eventos.ejercer_turno.EventoAnuciarGanador;
import org.eventos.ejercer_turno.EventoFallo;
import org.eventos.ejercer_turno.EventoPartidaIniciada;
import org.eventos.ejercer_turno.EventoResultadoGrito;
import org.eventos.ejercer_turno.EventoResultadoRuleta;
import org.eventos.ejercer_turno.EventoUnirseExitoso;

/**
 * Contrato Inbound del juego.
 * El ReceptorProcesador actualiza el modelo solo a través de esta interfaz.
 */
public interface IReceptorEstadoJuego {
    void aplicarActualizacion(EventoActualizarTurno e);
    void aplicarPartidaIniciada(EventoPartidaIniciada e);
    void aplicarUnirseExitoso(EventoUnirseExitoso e);
    void aplicarFallo(EventoFallo e);
    void aplicarResultadoRuleta(EventoResultadoRuleta e);
    void aplicarResultadoGrito(EventoResultadoGrito e);
    void aplicarGanador(EventoAnuciarGanador e);
}
