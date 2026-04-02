/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.componentered.entrada;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Afgord
 */
public class ServidorTCP {

    private final int puerto;
    private final ColaEntrada cola;

    public ServidorTCP(int puerto, ColaEntrada cola) {
        this.puerto = puerto;
        this.cola = cola;
    }

    public void escuchar() {
        System.out.println("[ServidorTCP] Iniciando servidor en puerto " + puerto);

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {

            while (true) {
                System.out.println("[ServidorTCP] Esperando conexión...");
                Socket socket = aceptarConexion(serverSocket);

                if (socket != null) {
                    byte[] datos = recibirDatos(socket);

                    if (datos != null && datos.length > 0) {
                        System.out.println("[ServidorTCP] Datos recibidos, enviando a cola...");
                        cola.push(datos);
                    }

                    socket.close();
                    System.out.println("[ServidorTCP] Conexión cerrada.");
                }
            }

        } catch (IOException e) {
            System.err.println("[ServidorTCP] Error en el servidor: " + e.getMessage());
        }
    }

    private Socket aceptarConexion(ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }

    private byte[] recibirDatos(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        byte[] bloque = new byte[1024];
        int bytesLeidos;

        while ((bytesLeidos = in.read(bloque)) != -1) {
            buffer.write(bloque, 0, bytesLeidos);
        }

        return buffer.toByteArray();
    }

}
