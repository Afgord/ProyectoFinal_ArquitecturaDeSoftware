/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Ejercer_Turno;
import audio.AudioController;
import java.awt.Color;

/**
 *
 * @author lagar
 */
public class ejecutador {
    public static void main(String[] args) {
        Color cambioAzul = Color.BLUE;
        Color cambioRojo = Color.RED;
        Color cambioAmarillo = Color.YELLOW;
        Color cambioVerde = Color.GREEN;
        Color cambioNegro = Color.BLACK;
        int numCartas = 7;
        int rangoInicio = 0;
        int rangoFinal = 9;
        boolean masDos = true;
        boolean prohibido = false;
        boolean reversa = true;
        boolean masCuatro = false;
        boolean cambioColor = true;
        Jugador jugador1 = new Jugador("Xrapayel",numCartas,"/avatares/XD.jpg");
        Jugador jugador2 = new Jugador("Mondongo",numCartas,"/avatares/mondongo.jpg");
        Jugador jugador3 = new Jugador("Verch",numCartas,"/avatares/queHiciste.jpg");
        Jugador jugador4 = new Jugador("Gilberto",numCartas,"/avatares/gilberto.jpg");
        AudioController.init();
        FrameTablero f = new FrameTablero(jugador1, jugador2, jugador3, jugador4,rangoInicio, rangoFinal, masDos, prohibido, reversa, masCuatro, cambioColor ,cambioAzul, cambioRojo, cambioAmarillo, cambioVerde, cambioNegro, numCartas);
        
    }
}
