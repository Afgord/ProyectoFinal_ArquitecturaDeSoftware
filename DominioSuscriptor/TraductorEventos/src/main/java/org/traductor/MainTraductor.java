/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.traductor;

import entrada.Receptor;
import fachadas.FachadaDominio;
import fachadas.FachadaJuego;
import entrada.ServidorTCP;
import salida.DispatcherFactory;
import salida.IDispatcher;
/**
 * Punto de entrada del subscriptor de dominio.
 *
 * Cablea el ServidorTCP del puerto del dominio con un TraductorEventos
 * que delega en una FachadaJuego "vacia" (sin Tablero). El estado real
 * de la partida lo construye la fachada cuando llega EventoCrearPartida
 * + EventoUnirsePartida + EventoSolicitarInicio + todas las
 * EventoResponderSolicitudInicio del lobby (caso de uso Iniciar Partida).
 */
public class MainTraductor {

    public static void main(String[] args) {
        final String MI_IP = "192.168.100.97";
        final int PUERTO = 5000;

        System.out.println("=== [SISTEMA] Iniciando Servidor en " + MI_IP + " ===");

        FachadaDominio fachada = new FachadaJuego();
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
