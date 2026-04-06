/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.componentered.salida;
/**
 * 
 * @author lagar
 */
public class Dispatcher implements IDispatcher {
    private final ColaSalida cola;

    public Dispatcher(ColaSalida cola) {
        this.cola = cola;
    }

    @Override
    public void dispatch(byte[] datos) {
        if (datos != null) {
            cola.push(datos);
        }
    }
}