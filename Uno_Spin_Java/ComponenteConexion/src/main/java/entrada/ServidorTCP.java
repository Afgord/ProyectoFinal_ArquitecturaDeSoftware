/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entrada;

import comunes.ContextoConexion;
import comunes.Observer;
import comunes.Subject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Subject del flujo de entrada.
 *
 * Escucha conexiones TCP, recibe bytes, crea un contexto, lo almacena en una
 * cola y notifica a sus observadores.
 */
public class ServidorTCP implements Subject, Runnable {

    /**
     * Puerto local en el que escuchará el servidor.
     */
    private final int puerto;

    /**
     * Cola de contextos recibidos.
     */
    private final Queue<ContextoConexion> cola;

    /**
     * Observadores registrados.
     */
    private final List<Observer> observers;

    /**
     * Último contexto recibido, usado durante la notificación.
     */
    private ContextoConexion contextoActual;

    /**
     * Indica si el servidor se mantiene activo.
     */
    private boolean activo;

    /**
     * Hilo del servidor.
     */
    private Thread hilo;

    /**
     * Socket servidor.
     */
    private ServerSocket serverSocket;

    public ServidorTCP(int puerto) {
        this.puerto = puerto;
        this.cola = new LinkedBlockingQueue<>();
        this.observers = new ArrayList<>();
        this.contextoActual = null;
        this.activo = false;
        this.hilo = null;
        this.serverSocket = null;
    }

    /**
     * Inicia el hilo del servidor.
     */
    public void iniciar() {
        if (!activo) {
            activo = true;
            hilo = new Thread(this, "ServidorTCP-Hilo");
            hilo.setDaemon(true);
            hilo.start();
        }
    }

    /**
     * Detiene el servidor y libera el socket de escucha.
     */
    public void detener() {
        activo = false;

        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("[ServidorTCP] Error al cerrar servidor: " + e.getMessage());
            }
        }

        if (hilo != null) {
            hilo.interrupt();
        }
    }

    /**
     * Ciclo principal del servidor.
     */
    @Override
    public void run() {
        System.out.println("[ServidorTCP] Hilo iniciado.");

        try (ServerSocket ss = new ServerSocket(puerto)) {
            this.serverSocket = ss;
            System.out.println("[ServidorTCP] Escuchando en puerto " + puerto);

            while (activo) {
                try (Socket socket = ss.accept()) {
                    byte[] bytes = recibirBytes(socket);

                    if (bytes != null && bytes.length > 0) {
                        String hostRemoto = socket.getInetAddress().getHostAddress();
                        int puertoRemoto = socket.getPort();

                        contextoActual = new ContextoConexion(hostRemoto, puertoRemoto, bytes);
                        cola.offer(contextoActual);

                        System.out.println("[ServidorTCP] Contexto agregado a la cola.");
                        notifyObservers();
                    }

                } catch (SocketException e) {
                    if (activo) {
                        System.err.println("[ServidorTCP] Error de socket: " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            if (activo) {
                System.err.println("[ServidorTCP] Error en servidor: " + e.getMessage());
            }
        } finally {
            System.out.println("[ServidorTCP] Servidor detenido.");
        }
    }

    /**
     * Lee todos los bytes disponibles desde el socket recibido.
     *
     * @param socket socket conectado
     * @return bytes recibidos
     * @throws IOException si ocurre un error de lectura
     */
    private byte[] recibirBytes(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        byte[] bloque = new byte[1024];
        int bytesLeidos;

        while ((bytesLeidos = in.read(bloque)) != -1) {
            buffer.write(bloque, 0, bytesLeidos);
        }

        return buffer.toByteArray();
    }

    @Override
    public void addObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Notifica a los observadores con el último contexto recibido.
     */
    @Override
    public void notifyObservers() {
        if (contextoActual == null) {
            return;
        }

        for (Observer o : observers) {
            o.update(contextoActual);
        }
    }
}