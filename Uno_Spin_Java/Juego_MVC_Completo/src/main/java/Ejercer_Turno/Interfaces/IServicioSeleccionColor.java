package Ejercer_Turno.Interfaces;

import entidades.Colores;
import java.awt.Color;
import java.awt.Frame;

/**
 * Servicio que abre el selector visual de color para los comodines y
 * devuelve el Colores elegido vía callback.
 */
public interface IServicioSeleccionColor {
    void solicitarColor(Frame padre, Color[] opciones, IResultadoColor callback);

    interface IResultadoColor {
        void onResultado(Colores resultado);
    }
}
