package org.broker;

import dtos.AceptacionDTO;
import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoCrearPartida;
import org.eventos.ejercer_turno.EventoLobbyActualizado;
import org.eventos.ejercer_turno.EventoPartidaIniciada;
import org.eventos.ejercer_turno.EventoRegistroConexion;
import org.eventos.ejercer_turno.EventoResponderSolicitudInicio;
import org.eventos.ejercer_turno.EventoSolicitarInicio;
import org.eventos.ejercer_turno.EventoSolicitudInicioRecibida;
import org.eventos.ejercer_turno.EventoUnirsePartida;

/**
 * Smoke test del caso de uso Iniciar Partida sin UI.
 *
 * Pasos:
 *   1. Registra dos clientes (1 host + 1 jugador) en el broker.
 *   2. Host crea partida -> espera EventoLobbyActualizado.
 *   3. Jugador 2 se une -> espera EventoLobbyActualizado.
 *   4. Host solicita inicio -> espera EventoSolicitudInicioRecibida.
 *   5. Jugador 2 acepta -> espera EventoEstadoAceptacionActualizado +
 *      EventoPartidaIniciada con manos repartidas.
 *
 * Requiere broker y DominioSuscriptor corriendo en 192.168.100.97:5001
 * y :5000 respectivamente. Cada cliente fake escucha en localhost en
 * un puerto temporal y declara esa IP en el registro.
 */
public class SmokeIniciarPartida {

    private static final String BROKER_HOST = "127.0.0.1";
    private static final int BROKER_PUERTO = 5001;

    public static void main(String[] args) throws Exception {
        ClienteFake host = new ClienteFake("HOST", "Laura", "/avatares/avatar1.png", 6101);
        ClienteFake p2 = new ClienteFake("P2", "Miguel", "/avatares/avatar2.png", 6102);

        host.start();
        p2.start();

        host.enviar(new EventoRegistroConexion(host.id, host.nombre, "127.0.0.1", host.puerto, host.avatar, uuid()));
        p2.enviar(new EventoRegistroConexion(p2.id, p2.nombre, "127.0.0.1", p2.puerto, p2.avatar, uuid()));
        Thread.sleep(500);

        host.enviar(new EventoCrearPartida(new JugadorDTO(host.id, host.nombre, host.avatar), host.id, uuid()));
        Evento evt = host.esperar(EventoLobbyActualizado.class, 4000);
        EventoLobbyActualizado lobby1 = (EventoLobbyActualizado) requireNonNull(evt, "EventoLobbyActualizado tras crear");
        String idPartida = lobby1.getIdPartida();
        System.out.println("[SMOKE] Lobby creado idPartida=" + idPartida + " jugadores=" + lobby1.getJugadores().size());

        p2.enviar(new EventoUnirsePartida(idPartida, new JugadorDTO(p2.id, p2.nombre, p2.avatar), p2.id, uuid()));
        EventoLobbyActualizado lobby2 = (EventoLobbyActualizado) requireNonNull(host.esperar(EventoLobbyActualizado.class, 4000), "EventoLobbyActualizado tras unirse");
        p2.esperar(EventoLobbyActualizado.class, 4000);
        System.out.println("[SMOKE] Lobby actualizado tras unirse, jugadores=" + lobby2.getJugadores().size());
        if (lobby2.getJugadores().size() != 2) throw new IllegalStateException("Esperaba 2 jugadores en el lobby");

        host.enviar(new EventoSolicitarInicio(host.id, uuid()));
        EventoSolicitudInicioRecibida sol = (EventoSolicitudInicioRecibida) requireNonNull(p2.esperar(EventoSolicitudInicioRecibida.class, 4000), "EventoSolicitudInicioRecibida en p2");
        host.esperar(EventoSolicitudInicioRecibida.class, 4000);
        System.out.println("[SMOKE] Solicitud recibida, solicitante=" + sol.getNombreSolicitante()
                + " aceptaciones=" + estadoSolicitud(sol.getAceptaciones()));

        p2.enviar(new EventoResponderSolicitudInicio(true, p2.id, uuid()));
        EventoPartidaIniciada partida = (EventoPartidaIniciada) requireNonNull(host.esperar(EventoPartidaIniciada.class, 4000), "EventoPartidaIniciada en host");
        p2.esperar(EventoPartidaIniciada.class, 4000);
        System.out.println("[SMOKE] Partida iniciada, idPartida=" + partida.getIdPartida()
                + " turno=" + partida.getIdJugadorTurnoActual()
                + " descarte=" + partida.getDescarteInicial().getValor() + "/" + partida.getDescarteInicial().getColor()
                + " manos=" + manos(partida.getJugadores()));

        if (partida.getJugadores().size() != 2) throw new IllegalStateException("Esperaba 2 jugadores en EventoPartidaIniciada");
        for (JugadorDTO j : partida.getJugadores()) {
            if (j.getMano() == null || j.getMano().size() != 7) {
                throw new IllegalStateException("Mano de " + j.getNombre() + " debe tener 7 cartas, tenia "
                        + (j.getMano() == null ? "null" : j.getMano().size()));
            }
        }

        host.stop();
        p2.stop();
        System.out.println("[SMOKE] OK - caso de uso Iniciar Partida verificado end-to-end");
    }

    private static String estadoSolicitud(List<AceptacionDTO> as) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < as.size(); i++) {
            AceptacionDTO a = as.get(i);
            if (i > 0) sb.append(",");
            sb.append(a.getNombre()).append(":").append(a.getEstado());
        }
        return sb.append("]").toString();
    }

    private static String manos(List<JugadorDTO> jugadores) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < jugadores.size(); i++) {
            JugadorDTO j = jugadores.get(i);
            if (i > 0) sb.append(",");
            int n = j.getMano() == null ? 0 : j.getMano().size();
            sb.append(j.getNombre()).append(":").append(n);
        }
        return sb.append("]").toString();
    }

    private static <T> T requireNonNull(T v, String msg) {
        if (v == null) throw new IllegalStateException("Smoke fail: " + msg);
        return v;
    }

    private static String uuid() { return UUID.randomUUID().toString(); }

    static class ClienteFake {
        final String id;
        final String nombre;
        final String avatar;
        final int puerto;
        final ConcurrentLinkedQueue<Evento> recibidos = new ConcurrentLinkedQueue<>();
        ServerSocket servidor;
        Thread accept;
        volatile boolean activo = true;

        ClienteFake(String id, String nombre, String avatar, int puerto) {
            this.id = id;
            this.nombre = nombre;
            this.avatar = avatar;
            this.puerto = puerto;
        }

        void start() throws IOException {
            servidor = new ServerSocket(puerto);
            servidor.setSoTimeout(0);
            accept = new Thread(() -> {
                while (activo) {
                    try {
                        Socket s = servidor.accept();
                        try (s) {
                            byte[] datos = leerTodo(s);
                            if (datos.length == 0) continue;
                            try (ObjectInputStream ois = new ObjectInputStream(new java.io.ByteArrayInputStream(datos))) {
                                Object obj = ois.readObject();
                                if (obj instanceof Evento ev) {
                                    System.out.println("[" + id + "] recibido " + obj.getClass().getSimpleName());
                                    recibidos.offer(ev);
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (activo) System.err.println("[" + id + "] error accept: " + e.getMessage());
                    }
                }
            }, "cliente-" + id);
            accept.setDaemon(true);
            accept.start();
        }

        void enviar(Evento evt) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(evt);
            }
            try (Socket s = new Socket(BROKER_HOST, BROKER_PUERTO);
                 OutputStream out = s.getOutputStream()) {
                out.write(baos.toByteArray());
                out.flush();
            }
            System.out.println("[" + id + "] enviado " + evt.getClass().getSimpleName());
        }

        Evento esperar(Class<? extends Evento> tipo, long timeoutMs) throws InterruptedException {
            long fin = System.currentTimeMillis() + timeoutMs;
            while (System.currentTimeMillis() < fin) {
                for (Evento ev : recibidos) {
                    if (tipo.isInstance(ev)) {
                        recibidos.remove(ev);
                        return ev;
                    }
                }
                Thread.sleep(50);
            }
            return null;
        }

        void stop() {
            activo = false;
            try { if (servidor != null) servidor.close(); } catch (IOException ignored) {}
        }

        private static byte[] leerTodo(Socket s) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int n;
            while ((n = s.getInputStream().read(buf)) > 0) {
                baos.write(buf, 0, n);
            }
            return baos.toByteArray();
        }
    }
}
