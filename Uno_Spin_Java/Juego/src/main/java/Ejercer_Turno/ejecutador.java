/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;

/**
 *
 * @author lagar
 */
public class ejecutador {
    public static void main(String[] args) {
        Color cambioAzul = Color.PINK;
        Color cambioRojo = Color.CYAN;
        Color cambioAmarillo = Color.ORANGE;
        Color cambioVerde = Color.magenta;
        Color cambioNegro = Color.DARK_GRAY;
        int numCartas = 9;
        int rangoInicio = 3;
        int rangoFinal = 7;
        boolean masDos = true;
        boolean prohibido = false;
        boolean reversa = true;
        boolean masCuatro = false;
        boolean cambioColor = true;
        Jugador jugador1 = new Jugador("Xrapayel",numCartas,"/avatares/XD.jpg");
        Jugador jugador2 = new Jugador("Mondongo",numCartas,"/avatares/mondongo.jpg");
        Jugador jugador3 = new Jugador("Verch",numCartas,"/avatares/queHiciste.jpg");
        Jugador jugador4 = new Jugador("Homero",numCartas,"/avatares/revivan.jpg");
        
        FrameTablero f = new FrameTablero(jugador1, jugador2, jugador3, jugador4,rangoInicio, rangoFinal, masDos, prohibido, reversa, masCuatro, cambioColor ,cambioAzul, cambioRojo, cambioAmarillo, cambioVerde, cambioNegro, numCartas);
    }
}
