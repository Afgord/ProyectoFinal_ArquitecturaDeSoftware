/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cambiar_Color.Implementacion;

import Cambiar_Color.MVC.*;
import Ejercer_Turno.Interfaces.IServicioSeleccionColor;
import java.awt.Color;
import java.awt.Frame;
/**
 * 
 * @author Luis Rafael
 */
public class SwingSeleccionColor implements IServicioSeleccionColor {
    @Override
    public void solicitarColor(Frame padre, Color[] opciones, IResultadoColor callback) {
        ModeloColor modelo = new ModeloColor();
        
        modelo.registrar((contexto) -> {
            if (contexto.getDatosColor() != null) {
                callback.onResultado(contexto.getDatosColor());
            }
        });

        ControlColor control = new ControlColor(
            modelo, 
            opciones[0], 
            opciones[1], 
            opciones[2], 
            opciones[3]  
        );

        PanelSelectorColor vista = new PanelSelectorColor(padre, control);
        vista.setVisible(true);
    }
}