/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import entidades.Colores;
import entidades.Tablero;
/**
 * 
 * @author lagar
 */
public interface FachadaDominio {
    void robarCarta();
    boolean validarYPlay(CartaDTO carta);
    void cambiarColorDescarte(Colores color); 
    void pasarTurno();
    JugadorDTO verificarGanador();
    Tablero getTablero(); 
}
