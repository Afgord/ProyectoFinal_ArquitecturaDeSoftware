package Ejercer_Turno.Interfaces;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.util.List;

/**
 * Contrato de lectura del modelo del MVC.
 * Las vistas lo consumen para pintar; el modelo se actualiza únicamente
 * a partir de eventos entrantes desde la red.
 */
public interface IModeloDatos {
    CartaDTO getCartaDescarteDTO();
    List<JugadorDTO> getJugadoresDTO();
    boolean isUltimaJugadaValida();
    String getIdJugadorLocal();
    String getIdJugadorTurnoActual();
    String getGanador();
    void registrarObservador(Observador o);
}
