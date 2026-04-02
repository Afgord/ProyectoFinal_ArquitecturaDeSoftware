/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.componentered.salida;

/**
 *
 * @author Afgord
 */
public class Dispatcher implements IDispatcher {

    private ColaSalida cola;

    public Dispatcher(ColaSalida cola) {
        this.cola = cola;
    }

    @Override
    public void dispatch(byte[] datos) {
        cola.push(datos);
    }

}
