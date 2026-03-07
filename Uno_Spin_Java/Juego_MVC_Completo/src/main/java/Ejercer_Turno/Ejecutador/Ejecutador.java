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
        // 1. Definición de Colores
        Color cAzul = new Color(0, 100, 255);
        Color cRojo = new Color(220, 20, 60);
        Color cAmarillo = new Color(255, 215, 0);
        Color cVerde = new Color(34, 139, 34);
        Color cNegro = Color.BLACK;
        
        // 2. Configuración de Jugadores
        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(new Jugador("Xrapayel", "/avatares/XD.jpg"));
        listaJugadores.add(new Jugador("Mondongo", "/avatares/mondongo.jpg"));
        listaJugadores.add(new Jugador("Verch", "/avatares/queHiciste.jpg"));
        listaJugadores.add(new Jugador("Gilberto", "/avatares/gilberto.jpg"));

        // 3. Inicialización Manual de Entidades (Inyección de dependencias)
        // Creamos el tablero pasando la configuración completa
        Tablero tablero = new Tablero(
            listaJugadores, 0, 9, // rangoInicio, rangoFinal
            true, true, true, true, true, // masDos, prohibido, reversa, masCuatro, cambioColor
            cAzul, cRojo, cAmarillo, cVerde, cNegro
        );

        // Extraemos las entidades que el Tablero creó internamente (o podrías crearlas fuera)
        Mazo mazo = tablero.getMazo();
        Descarte descarte = tablero.getDescarte();

        // 4. Reparto inicial de cartas (Lógica que ocurre antes de empezar el MVC)
        int numCartasInicial = 7;
        for (Jugador j : listaJugadores) {
            for (int i = 0; i < numCartasInicial; i++) {
                j.agregarCarta(mazo.tomarUnaCarta());
            }
        }

        // 5. Inicialización de Audio
        try {
            AudioController.init();
        } catch (Exception e) {
            System.err.println("Error Audio: " + e.getMessage());
        }

        // 6. Montaje del MVC
        // El ModeloJuego recibe las entidades ya listas y pobladas
        ModeloJuego modelo = new ModeloJuego(listaJugadores, mazo, descarte, tablero);
        ControlJuego control = new ControlJuego(modelo);
        
        // 7. Lanzamiento de la Vista
        new FrameTablero(control, modelo);
    }
}
