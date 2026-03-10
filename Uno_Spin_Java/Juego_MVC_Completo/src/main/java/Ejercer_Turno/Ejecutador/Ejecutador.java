/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.Ejecutador;

import Ejercer_Turno.Dominio.*;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.FrameTablero;
import Ejercer_Turno.MVC.ModeloJuego;
import audio.AudioController;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Ejecutador {
    public static void main(String[] args) {
        Color cAzul = Color.CYAN;
        Color cRojo = Color.white;
        Color cAmarillo = Color.PINK;
        Color cVerde = Color.ORANGE;
        Color cNegro = Color.BLACK;
        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(new Jugador("Xrapayel", "/avatares/XD.jpg"));
        listaJugadores.add(new Jugador("Mondongo", "/avatares/mondongo.jpg"));
        listaJugadores.add(new Jugador("Verch", "/avatares/queHiciste.jpg"));
        listaJugadores.add(new Jugador("Gilberto", "/avatares/gilberto.jpg"));
        Tablero tablero = new Tablero(
            listaJugadores, 0, 9,
            true, true, true, true, true, 
            cAzul, cRojo, cAmarillo, cVerde, cNegro
        );
        Mazo mazo = tablero.getMazo();
        Descarte descarte = tablero.getDescarte();
        int numCartasInicial = 7;
        for (Jugador j : listaJugadores) {
            for (int i = 0; i < numCartasInicial; i++) {
                j.agregarCarta(mazo.tomarUnaCarta());
            }
        }
        try {
            AudioController.init();
        } catch (Exception e) {
            System.err.println("Error Audio: " + e.getMessage());
        }
        ModeloJuego modeloReal = new ModeloJuego(listaJugadores, mazo, descarte, tablero);
        ControlJuego control = new ControlJuego(modeloReal, modeloReal);
        new FrameTablero(control, modeloReal);
    }
}
