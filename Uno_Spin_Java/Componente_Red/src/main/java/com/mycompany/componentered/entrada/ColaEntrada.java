/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.componentered.entrada;

import com.mycompany.componentered.comunes.Observer;
import com.mycompany.componentered.comunes.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Afgord
 */
public class ColaEntrada implements Subject {

    private BlockingQueue<byte[]> cola;
    private List<Observer> observers;

    public ColaEntrada() {
        this.cola = new LinkedBlockingQueue<>();
        this.observers = new ArrayList<>();
    }

    public void push(byte[] datos) {
        cola.offer(datos);
        notifyObservers();
    }

    public byte[] poll() {
        return cola.poll();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

}
