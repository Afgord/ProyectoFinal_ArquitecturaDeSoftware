/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Ejercer_Turno.Interfaces;

import Ejercer_Turno.Dominio.Carta;
import java.awt.Color;
/**
 * 
 * @author lagar
 */
public interface IModeloAcciones {
    void tirarCarta(Carta carta);
    void tirarCartaNegra(Carta carta, Color nuevoColor, String nombreColor);
    void robarCarta();
    void gritarUno();
    void notificarError();
    void aplicarCastigo();
}