/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Ejercer_Turno.Interfaces;

import Ejercer_Turno.Dominio.Carta;
import Ejercer_Turno.Dominio.Tablero;
import java.awt.Color;
/**
 * 
 * @author lagar
 */
public interface IFachadaDominio {
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
