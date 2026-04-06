/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.componentered.entrada;

import org.componentered.comunes.Observador;
import org.componentered.comunes.Sujeto;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 
 * @author lagar
 */
public class ColaEntrada implements Sujeto {
    private final BlockingQueue<byte[]> cola = new LinkedBlockingQueue<>();
    private final List<Observador> observadores = new ArrayList<>();

    public void push(byte[] datos) {
        if (datos != null) {
            cola.offer(datos);
            notificarObservadores();
        }
    }

    public byte[] poll() {
        return cola.poll();
    }

    @Override
    public void agregarObservador(Observador o) { observadores.add(o); }

    @Override
    public void eliminarObservador(Observador o) { observadores.remove(o); }

    @Override
    public void notificarObservadores() {
        for (Observador o : observadores) { o.actualizar(); }
    }
}