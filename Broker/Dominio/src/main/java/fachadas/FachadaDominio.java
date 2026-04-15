/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachadas;

import entidades.Carta;
import entidades.Jugador;
import entidades.Tablero;
import java.awt.Color;
import java.util.List;
/**
 * 
 * @author lagar
 */
public interface FachadaDominio {
    void robarCarta();
    boolean validarYPlay(Carta carta);
    void aplicarEfectoCarta(Carta carta, String colorElegido);
    void pasarTurno();
    Tablero getTablero(); 
    void inyectarTablero(Tablero tablero);
    int getAcumulacionCastigo();
    void limpiarCastigo();
    void procesarSeleccion(Color color, String nombre);
    void inicializarPartida(List<Jugador> jugadores, int rangoInicio, int rangoFinal, 
                            boolean masDos, boolean prohibido, boolean reversa, 
                            boolean masCuatro, boolean cambioColor);
}
