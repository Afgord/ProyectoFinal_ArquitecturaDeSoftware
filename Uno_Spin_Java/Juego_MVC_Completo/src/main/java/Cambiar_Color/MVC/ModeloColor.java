/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cambiar_Color.MVC;

import Cambiar_Color.Dominio.FachadaColor;
import Cambiar_Color.Dominio.SeleccionColor;
import Cambiar_Color.Dominio.IFachadaColor;
import Cambiar_Color.Interfaces.ObservadorColor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class ModeloColor {
    private SeleccionColor seleccion;
    private final List<ObservadorColor> observadores = new ArrayList<>();
    private final IFachadaColor fachada;

    public ModeloColor() {
        this.fachada = new FachadaColor();
    }

    public void registrar(ObservadorColor o) { 
        observadores.add(o); 
    }

    public void confirmarSeleccion(Color color, String nombre) {
        fachada.procesarSeleccion(color, nombre);
        this.seleccion = new SeleccionColor(color, nombre);
        notificar();
    }

    private void notificar() {
        for (ObservadorColor o : observadores) {
            o.colorElegido(seleccion.getColor(), seleccion.getNombre());
        }
    }
}