package Cambiar_Color.MVC;

import Cambiar_Color.Interfaces.IColorAcciones;
import Cambiar_Color.Interfaces.ObservadorColor;
import entidades.Colores;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo del selector de color, simplificado: solo guarda el Colores
 * elegido y notifica a sus observadores. Sin dependencia con FachadaSelectorColor.
 */
public class ModeloColor implements IColorAcciones {

    private Colores seleccion;
    private final List<ObservadorColor> observadores = new ArrayList<>();

    @Override
    public void confirmarSeleccion(Colores color) {
        this.seleccion = color;
        notificar();
    }

    @Override
    public void cancelar() {
        this.seleccion = null;
        notificar();
    }

    public void registrar(ObservadorColor o) {
        if (o != null && !observadores.contains(o)) {
            observadores.add(o);
        }
    }

    public Colores getSeleccion() {
        return seleccion;
    }

    private void notificar() {
        for (ObservadorColor o : observadores) {
            o.colorElegido(this);
        }
    }
}
