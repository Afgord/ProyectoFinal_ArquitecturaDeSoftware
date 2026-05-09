/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.broker;

import entrada.ServidorTCP;
import entrada.Receptor;
import salida.DispatcherFactory;
import salida.IDispatcher;
/**
 * 
 * @author lagar
 */
public class MainBroker {

    public static void main(String[] args) {
        final int PUERTO_BROKER = 5001;
        ServidorTCP servidor = new ServidorTCP(PUERTO_BROKER);
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        BrokerOrquestador orquestador = new BrokerOrquestador(dispatcher);
        // Las conexiones de los jugadores se registran dinamicamente cuando cada cliente
        // envia EventoRegistroConexion al arrancar (caso de uso Iniciar Partida).
        Receptor receptorPuente = new Receptor(bytes -> orquestador.rutarEvento(bytes));
        servidor.addObserver(receptorPuente);

        try {
            System.out.println("=== [BROKER] Escuchando en puerto: " + PUERTO_BROKER + " ===");
            servidor.iniciar();
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            servidor.detener();
        }
    }
}