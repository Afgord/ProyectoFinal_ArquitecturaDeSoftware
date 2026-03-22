/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import DTOs.ColorDTO;
import Cambiar_Color.MVC.ControlColor;
import Cambiar_Color.MVC.ModeloColor;
import Cambiar_Color.MVC.PanelSelectorColor;
import DTOs.CartaDTO;
import Ejercer_Turno.Interfaces.IModeloAcciones;
import java.awt.Color;
import java.awt.Frame;
/**
 * 
 * @author Luis Rafael
 */
public class ControlJuego {
    private final IModeloAcciones modeloAcciones;

    public ControlJuego(IModeloAcciones modeloAcciones) {
        this.modeloAcciones = modeloAcciones;
    }
    
    

    public void solicitarRobarCarta() {
        modeloAcciones.robarCarta();
    }

    public void solicitarTirarCarta(CartaDTO carta) {
        modeloAcciones.tirarCarta(carta);
    }

    public void solicitarTirarCartaNegra(CartaDTO carta, Color nuevoColor, String nombreColor) {
        modeloAcciones.tirarCartaNegra(carta, nuevoColor, nombreColor);
    }

    public void solicitarAplicarCastigo() {
        modeloAcciones.aplicarCastigo();
    }

    public void solicitarSeleccionColor(CartaDTO carta, Frame padre) {
        Color[] colores = modeloAcciones.obtenerColoresConfigurados();
        
        ModeloColor mColor = new ModeloColor();
        
        mColor.registrar(contexto -> {
            ColorDTO seleccion = contexto.getDatosColor();
            this.solicitarTirarCartaNegra(carta, seleccion.getColor(), seleccion.getNombre());
        });

        ControlColor cColor = new ControlColor(mColor, colores[0], colores[1], colores[2], colores[3]);
        
        PanelSelectorColor vista = new PanelSelectorColor(padre, cColor);
        vista.setVisible(true);
    }
}