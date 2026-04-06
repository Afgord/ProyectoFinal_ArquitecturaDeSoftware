/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.componentered.salida;
/**
 * 
 * @author lagar
 */
public class DispatcherFactory {

    public static IDispatcher crearDispatcher(String hostDestino, int puertoDestino) {
        ColaSalida cola = new ColaSalida();
        ClienteTCP clienteTCP = new ClienteTCP(hostDestino, puertoDestino, cola);
        cola.agregarObservador(clienteTCP);
        
        return new Dispatcher(cola);
    }
}