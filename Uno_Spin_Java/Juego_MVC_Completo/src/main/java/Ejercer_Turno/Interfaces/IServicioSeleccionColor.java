/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Ejercer_Turno.Interfaces;

import org.uno.dto.ColorDTO;
import java.awt.Color;
import java.awt.Frame;

/**
 * @author Luis Rafael
 */
public interface IServicioSeleccionColor {
    void solicitarColor(Frame padre, Color[] opciones, IResultadoColor callback);

    interface IResultadoColor {
        void onResultado(ColorDTO resultado);
    }
}