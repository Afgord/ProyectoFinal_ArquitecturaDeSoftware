/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Dominio.Carta;
import Ejercer_Turno.Interfaces.IModeloAcciones;
import Ejercer_Turno.Interfaces.IModeloDatos;
import java.awt.Color;

public class ControlJuego {

    private final IModeloAcciones modeloAcciones;
    private final IModeloDatos modeloDatos;

    public ControlJuego(IModeloAcciones acciones, IModeloDatos datos) {
        this.modeloAcciones = acciones;
        this.modeloDatos = datos;
    }

    public void solicitarRobarCarta() {
        modeloAcciones.robarCarta();
    }

    public void solicitarTirarCarta(Carta carta) {
        modeloAcciones.tirarCarta(carta);
    }

    public void solicitarTirarCartaNegra(Carta carta, Color nuevoColor, String nombreColor) {
        modeloAcciones.tirarCartaNegra(carta, nuevoColor, nombreColor);
    }

    public void solicitarGritarUno() {
        modeloAcciones.gritarUno();
    }

    public void reproducirSonidoError() {
        modeloAcciones.notificarError();
    }

    public void solicitarAplicarCastigo() {
        modeloAcciones.aplicarCastigo();
    }

    public void reproducirMusica() {
        ((ModeloJuego) modeloAcciones).reproducirMusica();
    }

    public void pararMusica() {
        ((ModeloJuego) modeloAcciones).detenerMusica();
    }

    public void alertaSonidoTirar() {
        ((ModeloJuego) modeloAcciones).reproducirEfecto("tirar");
    }

    public void alertaSonidoJalar() {
        ((ModeloJuego) modeloAcciones).reproducirEfecto("jalar");
    }

    public void alertaSonidoUno() {
        ((ModeloJuego) modeloAcciones).reproducirEfecto("uno");
    }

    public void alertaSonidoError() {
        ((ModeloJuego) modeloAcciones).reproducirEfecto("alerta");
    }

    public IModeloDatos getModelo() {
        return modeloDatos;
    }
}
