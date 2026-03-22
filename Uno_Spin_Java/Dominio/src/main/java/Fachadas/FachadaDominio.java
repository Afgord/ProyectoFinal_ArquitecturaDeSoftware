/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Fachadas;

import Entidades.Carta;
import Entidades.Tablero;
import java.awt.Color;
/**
 * 
 * @author lagar
 */
public interface FachadaDominio {
    void inicializarPartida(int numeroJugadores);
    void robarCarta();
    boolean validarYPlay(Carta carta);
    void aplicarEfectoCarta(Carta carta, Color colorElegido);
    void pasarTurno();
    Tablero getTablero(); 
    void inyectarTablero(Tablero tablero);
    int getAcumulacionCastigo();
    void limpiarCastigo();
}
