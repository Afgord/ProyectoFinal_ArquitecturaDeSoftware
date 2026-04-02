/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.componentered.entrada;

import com.mycompany.componentered.comunes.Observer;

/**
 *
 * @author Afgord
 */
public class Receptor implements Observer {

    private final ColaEntrada cola;
    private final IReceptorExterno receptorExterno;

    public Receptor(ColaEntrada cola, IReceptorExterno receptorExterno) {
        this.cola = cola;
        this.receptorExterno = receptorExterno;
    }

    @Override
    public void update() {
        byte[] datos = cola.poll();

        if (datos != null) {
            System.out.println("[Receptor] Datos recibidos de la cola");
            receptorExterno.recibir(datos);
        }
    }
}
