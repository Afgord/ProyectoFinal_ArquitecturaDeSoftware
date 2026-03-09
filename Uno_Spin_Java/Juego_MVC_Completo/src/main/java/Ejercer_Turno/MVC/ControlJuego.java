/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Dominio.Carta;
import audio.AudioController;
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
    
    public void alertaSonidoTirar(){
        AudioController.playEffect("tirar");
    }
    
    public void alertaSonidoJalar(){
        AudioController.playEffect("jalar");
    }
    
    public void alertaSonidoUno(){
        AudioController.playEffect("uno");
    }
    
    public void alertaSonidoError(){
        AudioController.playEffect("alerta");
    }
    
    public void reproducirMusica(){
        AudioController.playMusic();
    }
    
    public void pararMusica(){
        AudioController.stopMusic();
    }
}