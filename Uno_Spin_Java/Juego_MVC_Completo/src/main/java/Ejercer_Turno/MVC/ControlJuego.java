/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Dominio.Carta;
import java.awt.Color;

public class ControlJuego {

    private final ModeloJuego modelo;

    public ControlJuego(ModeloJuego modelo) {
        this.modelo = modelo;
    }

    public void solicitarRobarCarta() {
        modelo.robarCarta();
    }

    public void solicitarTirarCarta(Carta carta) {
        modelo.tirarCarta(carta);
    }

    public void solicitarTirarCartaNegra(Carta carta, Color nuevoColor, String nombreColor) {
        modelo.tirarCartaNegra(carta, nuevoColor, nombreColor);
    }

    public void solicitarGritarUno() {
        modelo.gritarUno();
    }

    public void reproducirSonidoError() {
        modelo.notificarError();
    }

    public ModeloJuego getModelo() {
        return modelo;
    }
}