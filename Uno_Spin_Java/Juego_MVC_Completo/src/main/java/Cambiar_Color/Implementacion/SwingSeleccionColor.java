package Cambiar_Color.Implementacion;

import Cambiar_Color.MVC.ControlColor;
import Cambiar_Color.MVC.ModeloColor;
import Cambiar_Color.MVC.PanelSelectorColor;
import Ejercer_Turno.Interfaces.IServicioSeleccionColor;
import java.awt.Color;
import java.awt.Frame;

public class SwingSeleccionColor implements IServicioSeleccionColor {

    @Override
    public void solicitarColor(Frame padre, Color[] opciones, IResultadoColor callback) {
        ModeloColor modelo = new ModeloColor();
        modelo.registrar(contexto -> callback.onResultado(contexto.getSeleccion()));

        ControlColor control = new ControlColor(modelo, opciones[0], opciones[1], opciones[2], opciones[3]);
        PanelSelectorColor vista = new PanelSelectorColor(padre, control);
        vista.setVisible(true);
    }
}
