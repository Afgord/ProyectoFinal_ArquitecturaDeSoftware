/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.traductor;

import entrada.Receptor;
import entrada.ServidorTCP;
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
import salida.DispatcherFactory;
import salida.IDispatcher;
/**
 * 
 * @author lagar
 */
public class MainTraductor {

    public static void main(String[] args) {
        final String MI_IP = "127.0.0.1";
        final int PUERTO = 5000;
        
        System.out.println("=== [SISTEMA] Iniciando Servidor en " + MI_IP + " ===");

        Mazo mazo = new Mazo(0, 9, true, true, true, true, true);
        Carta inicio = mazo.sacarCartaInicialValida();
        Descarte descarte = new Descarte(inicio);
        Ruleta ruleta = new Ruleta();
        
        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(new Jugador("1", "Rafael", "avatar1.png", new Mano()));
        listaJugadores.add(new Jugador("2", "Jugador 2", "avatar2.png", new Mano()));
        listaJugadores.add(new Jugador("3", "Jugador 3", "avatar3.png", new Mano()));
        listaJugadores.add(new Jugador("4", "Jugador 4", "avatar4.png", new Mano()));

        for (Jugador jugador : listaJugadores) {
            for (int i = 0; i < 7; i++) {
                Carta c = mazo.tomarUnaCarta();
                if (c != null) {
                    jugador.agregarCarta(c);
                }
            }
        }

        Tablero tablero = new Tablero(mazo, descarte, listaJugadores, ruleta);
        FachadaDominio fachada = new FachadaJuego(tablero);
        ServidorTCP servidor = new ServidorTCP(PUERTO);
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        TraductorEventos traductor = new TraductorEventos(fachada, dispatcher);
        Receptor receptorPuente = new Receptor(bytes -> traductor.procesarEntrada(bytes));
        servidor.addObserver(receptorPuente);

        try {
            System.out.println("=== [TRADUCTOR] Escuchando en puerto: " + PUERTO + " ===");
            servidor.iniciar();
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            servidor.detener();
        }
    }
}