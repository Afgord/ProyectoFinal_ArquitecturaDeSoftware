/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.componentered.salida;

import org.componentered.comunes.Observador;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
/**
 * 
 * @author lagar
 */
public class ClienteTCP implements Observador {
    private final String host;
    private final int puerto;
    private final ColaSalida cola;

    public ClienteTCP(String host, int puerto, ColaSalida cola) {
        this.host = host;
        this.puerto = puerto;
        this.cola = cola;
    }

    @Override
    public void actualizar() {
        byte[] datos = cola.poll();
        if (datos != null) {
            try (Socket socket = new Socket(host, puerto);
                 OutputStream out = socket.getOutputStream()) {
                out.write(datos);
                out.flush();
            } catch (IOException e) {
                System.err.println("Error envio: " + e.getMessage());
            }
        }
    }
}