/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.componentered.salida;

import com.mycompany.componentered.comunes.Observer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Afgord
 */
public class ClienteTCP implements Observer {

    private final String host;
    private final int puerto;
    private final ColaSalida cola;

    public ClienteTCP(String host, int puerto, ColaSalida cola) {
        this.host = host;
        this.puerto = puerto;
        this.cola = cola;
    }

    @Override
    public void update() {
        byte[] datos = cola.poll();

        if (datos != null) {
            enviar(datos);
        }
    }

    private void enviar(byte[] datos) {
        try (Socket socket = conectar(); OutputStream out = socket.getOutputStream()) {

            out.write(datos);
            out.flush();
            System.out.println("[ClienteTCP] Datos enviados correctamente.");

        } catch (IOException e) {
            System.err.println("[ClienteTCP] Error al enviar datos: " + e.getMessage());
        }
    }

    private Socket conectar() throws IOException {
        System.out.println("[ClienteTCP] Conectando a " + host + ":" + puerto);
        return new Socket(host, puerto);
    }

}
