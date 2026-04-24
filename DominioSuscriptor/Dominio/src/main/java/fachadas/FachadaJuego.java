/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import entidades.Tablero;

public class FachadaJuego implements FachadaDominio {
    private Tablero tablero;

    public FachadaJuego(Tablero tablero) {
        this.tablero = tablero;
    }

    @Override
    public Object validarYPlay(CartaDTO carta) {
        System.out.println("[Fachada] Procesando jugada...");
        return tablero.ejecutarJugadaConRetorno(carta);
    }

    @Override
    public Object robarCarta() {
        System.out.println("[Fachada] Procesando robo...");
        return tablero.robarYPasarConRetorno();
    }

    @Override
    public void pasarTurno() {
        tablero.siguienteTurno();
    }
    
    @Override
    public Object gritarUno(JugadorDTO datosGrito) {
        System.out.println("[Fachada] Procesando grito de UNO...");
        return tablero.procesarGritoUno(datosGrito);
    }
    
    @Override
    public Object aplicarDescarteEspecial(String idJugador, CartaDTO cartaElegida) {
        System.out.println("[Fachada] Procesando descarte especial de Ruleta para: " + idJugador);
        return tablero.procesarDescarteEspecial(idJugador, cartaElegida);
    }
}