/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachadas;

import entidades.Carta;
import entidades.Colores;
import entidades.Jugador;
import entidades.Tablero;
/**
 * 
 * @author lagar
 */
public interface FachadaDominio {
    void robarCarta();
    boolean validarYPlay(Carta carta);
    void cambiarColorDescarte(Colores color); 
    void pasarTurno();
    Jugador verificarGanador();
    Tablero getTablero(); 
}
