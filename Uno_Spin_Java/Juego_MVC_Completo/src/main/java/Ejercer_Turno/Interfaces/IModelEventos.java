package Ejercer_Turno.Interfaces;

import DTOS.*;
import java.awt.Color;

/**
 * Contrato Outbound.
 * Define las acciones que el ModeloJuego notifica cuando ocurren localmente,
 * para que el traductor (u otro listener) las procese.
 */
public interface IModelEventos {
    void emitirTirarCarta(CartaDTO carta);
    void emitirTirarCartaNegra(CartaDTO carta, Color color, String nombreColor);
    void emitirRobarCarta();
    void emitirAplicarCastigo();
}
