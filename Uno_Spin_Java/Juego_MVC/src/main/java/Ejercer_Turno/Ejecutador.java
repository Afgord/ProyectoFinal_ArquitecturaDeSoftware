/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Ejercer_Turno;

import audio.AudioController;
import java.awt.Color;
public class Ejecutador {
    public static void main(String[] args) {
        Color cAzul = new Color(0, 100, 255);
        Color cRojo = new Color(220, 20, 60);
        Color cAmarillo = new Color(255, 215, 0);
        Color cVerde = new Color(34, 139, 34);
        Color cNegro = Color.BLACK;

        int numCartasInicial = 7;
        int rangoInicio = 0;
        int rangoFinal = 9;
        boolean masDos = true;
        boolean prohibido = true;
        boolean reversa = true;
        boolean masCuatro = true;
        boolean cambioColor = true;

        Jugador j1 = new Jugador("Xrapayel", "/avatares/XD.jpg");
        Jugador j2 = new Jugador("Mondongo", "/avatares/mondongo.jpg");
        Jugador j3 = new Jugador("Verch", "/avatares/queHiciste.jpg");
        Jugador j4 = new Jugador("Gilberto", "/avatares/gilberto.jpg");

        try {
            AudioController.init();
        } catch (Exception e) {
            System.err.println("Aviso: No se pudo cargar el controlador de audio.");
        }

        new FrameTablero(j1, j2, j3, j4, 
                         rangoInicio, rangoFinal, 
                         masDos, prohibido, reversa, masCuatro, cambioColor, 
                         cAzul, cRojo, cAmarillo, cVerde, cNegro, 
                         numCartasInicial);
    }
}