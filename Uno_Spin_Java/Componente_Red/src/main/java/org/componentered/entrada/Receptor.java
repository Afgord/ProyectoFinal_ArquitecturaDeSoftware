/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.componentered.entrada;
import org.componentered.comunes.Observador;
/**
 * 
 * @author lagar
 */
public class Receptor implements Observador {
    private final ColaEntrada cola;
    private final IReceptorExterno receptorExterno;

    public Receptor(ColaEntrada cola, IReceptorExterno receptorExterno) {
        this.cola = cola;
        this.receptorExterno = receptorExterno;
    }

    @Override
    public void actualizar() {
        byte[] datos = cola.poll();
        if (datos != null) {
            receptorExterno.recibir(datos);
        }
    }
}