package Cambiar_Color.MVC;

import Cambiar_Color.Interfaces.IColorAcciones;
import entidades.Colores;
import java.awt.Color;

public class ControlColor {

    private final IColorAcciones acciones;
    private final Color cAzul;
    private final Color cRojo;
    private final Color cAmarillo;
    private final Color cVerde;

    public ControlColor(IColorAcciones acciones, Color azul, Color rojo, Color amarillo, Color verde) {
        this.acciones = acciones;
        this.cAzul = azul;
        this.cRojo = rojo;
        this.cAmarillo = amarillo;
        this.cVerde = verde;
    }

    public void seleccionarColor(Colores color) {
        acciones.confirmarSeleccion(color);
    }

    public Color getAzul() { return cAzul; }
    public Color getRojo() { return cRojo; }
    public Color getAmarillo() { return cAmarillo; }
    public Color getVerde() { return cVerde; }
}
