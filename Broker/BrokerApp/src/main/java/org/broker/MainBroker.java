/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.broker;

import entrada.ServidorTCP;
import entrada.Receptor;
import salida.DispatcherFactory;
import salida.IDispatcher;
import org.directorios.Conexion;
/**
 * 
 * @author lagar
 */
public class MainBroker {

    public static void main(String[] args) {
        final String HOST = System.getProperty("uno.host", "127.0.0.1");
        final int PUERTO_BROKER = 5001;
        ServidorTCP servidor = new ServidorTCP(PUERTO_BROKER);
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        BrokerOrquestador orquestador = new BrokerOrquestador(dispatcher, HOST);
        orquestador.getDirectorio().registrarConexion(new Conexion("1", HOST, 5002));
        orquestador.getDirectorio().registrarConexion(new Conexion("2", HOST, 5003));
        orquestador.getDirectorio().registrarConexion(new Conexion("3", HOST, 5004));
        orquestador.getDirectorio().registrarConexion(new Conexion("4", HOST, 5005));
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