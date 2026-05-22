/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.traductor;

import entidades.*;
import entrada.Receptor;
import fachadas.FachadaDominio;
import fachadas.FachadaJuego;
import entrada.ServidorTCP;
import salida.DispatcherFactory;
import salida.IDispatcher;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class MainTraductor {

    public static void main(String[] args) {
        final String HOST = System.getProperty("uno.host", "127.0.0.1");
        final int PUERTO = 5000;

        System.out.println("=== [SISTEMA] Iniciando Servidor en " + HOST + " ===");

        Mazo mazo = new Mazo(0, 9, true, true, true, true, true);
        Descarte descarte = new Descarte();
        Ruleta ruleta = new Ruleta();

        List<Jugador> listaJugadores = new ArrayList<>();

        Tablero tablero = new Tablero(mazo, descarte, listaJugadores, ruleta);
        FachadaDominio fachada = new FachadaJuego(tablero);
        ServidorTCP servidor = new ServidorTCP(PUERTO);
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        TraductorEventos traductor = new TraductorEventos(fachada, dispatcher, HOST);
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