/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.componentered.entrada;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 
 * @author lagar
 */
public class ServidorTCP extends Thread {
    private final int puerto;
    private final ColaEntrada cola;
    private volatile boolean activo = true;

    public ServidorTCP(int puerto, ColaEntrada cola) {
        this.puerto = puerto;
        this.cola = cola;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            while (activo) {
                try (Socket socket = serverSocket.accept();
                     InputStream in = socket.getInputStream();
                     ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                    
                    byte[] bloque = new byte[1024];
                    int leidos;
                    while ((leidos = in.read(bloque)) != -1) {
                        buffer.write(bloque, 0, leidos);
                    }
                    
                    byte[] resultado = buffer.toByteArray();
                    if (resultado.length > 0) {
                        cola.push(resultado);
                    }
                } catch (IOException e) {
                    System.err.println("Error en socket: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error servidor: " + e.getMessage());
        }
    }

    public void detener() { this.activo = false; }
}