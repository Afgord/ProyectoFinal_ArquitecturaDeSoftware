/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Ejecutador;

import entidades.Carta;
import entidades.Descarte;
import entidades.Jugador;
import entidades.Mano;
import entidades.Mazo;
import entidades.Ruleta;
import entidades.Tablero;
import fachadas.FachadaDominio;
import fachadas.FachadaJuego;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lagar
 */
public class Ejecutador {
    
    public static void main(String[] args) {
        System.out.println("=== Iniciando Configuración del Juego ===");
        Mano mano1 = new Mano();
        Mano mano2 = new Mano();
        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(new Jugador("12345", "Jugador 1", "avatar1.png", mano1));
        listaJugadores.add(new Jugador("54312","Jugador 2", "avatar2.png", mano2));
        Mazo mazo = new Mazo(0, 9, true, true, true, true, true);
        Carta inicio = mazo.sacarCartaInicialValida();
        Descarte descarte = new Descarte(inicio);
        Ruleta ruleta = new Ruleta();
        Tablero tablero = new Tablero(mazo, descarte, listaJugadores, ruleta);
        for (Jugador jugador : listaJugadores) {
            for (int i = 0; i < 7; i++) {
                Carta c = mazo.tomarUnaCarta();
                if (c != null) {
                    jugador.agregarCarta(c);
                }
            }
        }
        FachadaDominio fachada = new FachadaJuego(tablero);
        System.out.println("=== Configuración Exitosa ===");

    }   
}
