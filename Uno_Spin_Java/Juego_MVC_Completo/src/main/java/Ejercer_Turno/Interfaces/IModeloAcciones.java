/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Ejercer_Turno.Interfaces;

import Ejercer_Turno.Dominio.CartaDTO;
import java.awt.Color;
/**
 * 
 * @author Luis Rafael
 */
public interface IModeloAcciones {
    void tirarCarta(CartaDTO carta);
    void tirarCartaNegra(CartaDTO carta, Color nuevoColor, String nombreColor);
    void robarCarta();
    void aplicarCastigo();
}