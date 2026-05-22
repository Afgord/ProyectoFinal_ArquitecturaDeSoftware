package com.mycompany.eventotraductor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Comprueba que un servicio TCP esté escuchando antes de arrancar el cliente.
 */
public final class VerificadorConexion {

    private static final int TIMEOUT_MS = 3000;

    private VerificadorConexion() {}

    public static boolean puedeConectar(String host, int puerto) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, puerto), TIMEOUT_MS);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
