/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachadas;

import entidades.Carta;
import entidades.Tablero;
/**
 * 
 * @author lagar
 */
public interface FachadaDominio {
    void inicializarPartida(int numeroJugadores);
    void robarCarta();
    boolean validarYPlay(Carta carta);
    void aplicarEfectoCarta(Carta carta, String colorElegido);
    void pasarTurno();
    Tablero getTablero(); 
    void inyectarTablero(Tablero tablero);
    int getAcumulacionCastigo();
    void limpiarCastigo();
}
