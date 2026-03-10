/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Dominio.Carta;
import Ejercer_Turno.Interfaces.IModeloAcciones;
import Ejercer_Turno.Interfaces.IModeloDatos;
import audio.AudioController;
import java.awt.Color;

public class ControlJuego {
    private final IModeloAcciones modeloAcciones;
    private final IModeloDatos modeloDatos;

    public ControlJuego(IModeloAcciones acciones, IModeloDatos datos) {
        this.modeloAcciones = acciones;
        this.modeloDatos = datos;
    }

    public void solicitarRobarCarta() { modeloAcciones.robarCarta(); }
    public void solicitarTirarCarta(Carta carta) { modeloAcciones.tirarCarta(carta); }
    public void solicitarTirarCartaNegra(Carta carta, Color nuevoColor, String nombreColor) { 
        modeloAcciones.tirarCartaNegra(carta, nuevoColor, nombreColor); 
    }
    public void solicitarGritarUno() { modeloAcciones.gritarUno(); }
    public void reproducirSonidoError() { modeloAcciones.notificarError(); }
    public void solicitarAplicarCastigo() { modeloAcciones.aplicarCastigo(); }

    public IModeloDatos getModelo() { return modeloDatos; }
    
    public void alertaSonidoTirar() { AudioController.playEffect("tirar"); }
    public void alertaSonidoJalar() { AudioController.playEffect("jalar"); }
    public void alertaSonidoUno() { AudioController.playEffect("uno"); }
    public void alertaSonidoError() { AudioController.playEffect("alerta"); }
    public void reproducirMusica() { AudioController.playMusic(); }
    public void pararMusica() { AudioController.stopMusic(); }
}