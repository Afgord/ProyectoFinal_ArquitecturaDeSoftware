/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cambiar_Color.MVC;

import Cambiar_Color.Dominio.*;
import Cambiar_Color.Interfaces.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloColor implements IColorAcciones {
    private SeleccionColor seleccion;
    private final List<ObservadorColor> observadores = new ArrayList<>();
    private final FachadaColor fachada;

    public ModeloColor() {
        this.fachada = new FachadaSelectorColor();
    }

    @Override
    public void confirmarSeleccion(ColorDTO dto) {
        fachada.procesarSeleccion(dto.getColor(), dto.getNombre());
        this.seleccion = new SeleccionColor(dto.getColor(), dto.getNombre());
        notificar();
    }

    @Override
    public void cancelar() {}

    public void registrar(ObservadorColor o) { 
        observadores.add(o); 
    }

    public ColorDTO getDatosColor() {
        return (seleccion != null) ? new ColorDTO(seleccion.getColor(), seleccion.getNombre()) : null;
    }

    private void notificar() {
        for (ObservadorColor o : observadores) {
            o.colorElegido(this);
        }
    }
}