/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Dominio.Carta;
import Ejercer_Turno.Interfaces.IModeloAcciones;
import java.awt.Color;
/**
 * 
 * @author Luis Rafael
 */
public class ControlJuego {
    private final IModeloAcciones modeloAcciones;
    
    public ControlJuego(IModeloAcciones acciones) {
        this.modeloAcciones = acciones;
    }

    public void solicitarRobarCarta() {
        modeloAcciones.robarCarta();
    }

    public void solicitarTirarCarta(Carta carta) {
        modeloAcciones.tirarCarta(carta);
    }

    public void solicitarTirarCartaNegra(Carta carta, Color nuevoColor, String nombreColor) {
        modeloAcciones.tirarCartaNegra(carta, nuevoColor, nombreColor);
    }

    public void solicitarAplicarCastigo() {
        modeloAcciones.aplicarCastigo();
    }

    public void solicitarSeleccionColor(Carta carta, java.awt.Frame padre) {
        Color[] colores = ((ModeloJuego) modeloAcciones).obtenerColoresConfigurados();
        Cambiar_Color.MVC.ModeloColor mColor = new Cambiar_Color.MVC.ModeloColor();
        Cambiar_Color.MVC.ControlColor cColor = new Cambiar_Color.MVC.ControlColor(
            mColor, colores[0], colores[1], colores[2], colores[3]  
        );
        mColor.registrar((color, nombre) -> {
            this.solicitarTirarCartaNegra(carta, color, nombre);
        });

        Cambiar_Color.MVC.PanelSelectorColor vista = new Cambiar_Color.MVC.PanelSelectorColor(padre, cColor);
        vista.setVisible(true);
    }
}