/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.componentered.salida;

/**
 *
 * @author Afgord
 */
public class DispatcherFactory {

    public static IDispatcher crearDispatcher(String host, int puerto) {
        ColaSalida cola = new ColaSalida();
        ClienteTCP clienteTCP = new ClienteTCP(host, puerto, cola);
        cola.addObserver(clienteTCP);
        Dispatcher dispatcher = new Dispatcher(cola);
        return dispatcher;
    }

}
