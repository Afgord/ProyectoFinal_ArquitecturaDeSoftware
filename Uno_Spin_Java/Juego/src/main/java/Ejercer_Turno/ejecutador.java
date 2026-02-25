/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Ejercer_Turno;

import audio.SoundManager;
import java.awt.Color;

/**
 *
 * @author lagar
 */
public class ejecutador {
    public static SoundManager sound;
    
    public static SoundManager getSound() {
        return sound;
    }   
    public static void main(String[] args) {
        Color cambioAzul = Color.RED;
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
        Jugador jugador4 = new Jugador("Gilberto",numCartas,"/avatares/gilberto.jpg");
        sound = new SoundManager();
        sound.loadMusic("/sound/s.wav");
        sound.loadEffect("tirar", "/sound/tirar.wav", 5);
        sound.loadEffect("jalar", "/sound/jalar.wav", 5);
        sound.loadEffect("uno", "/sound/uno.wav", 5);
        sound.loadEffect("alerta", "/sound/alerta.wav", 5);
        FrameTablero f = new FrameTablero(jugador1, jugador2, jugador3, jugador4,rangoInicio, rangoFinal, masDos, prohibido, reversa, masCuatro, cambioColor ,cambioAzul, cambioRojo, cambioAmarillo, cambioVerde, cambioNegro, numCartas);
        
    }
}
