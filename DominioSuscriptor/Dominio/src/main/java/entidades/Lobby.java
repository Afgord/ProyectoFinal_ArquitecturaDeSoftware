package entidades;

import dtos.EstadoAceptacion;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Estado de un lobby de partida en el dominio: jugadores conectados,
 * estado del flujo de inicio y aceptaciones recolectadas tras la
 * solicitud del host.
 */
public class Lobby {

    public enum Estado { ESPERANDO, SOLICITANDO_INICIO, INICIADA }

    public static final int CAPACIDAD_MAXIMA = 4;

    private final String idPartida;
    private final LinkedHashMap<String, Jugador> jugadores;
    private final LinkedHashMap<String, EstadoAceptacion> aceptaciones;
    private String idHost;
    private Estado estado;
    private String idJugadorSolicitante;

    public Lobby(String idPartida, Jugador host) {
        this.idPartida = idPartida;
        this.jugadores = new LinkedHashMap<>();
        this.aceptaciones = new LinkedHashMap<>();
        this.estado = Estado.ESPERANDO;
        this.idHost = host.getIdJugador();
        this.jugadores.put(host.getIdJugador(), host);
    }

    public boolean agregar(Jugador jugador) {
        if (jugador == null) return false;
        if (estado == Estado.INICIADA) return false;
        if (jugadores.containsKey(jugador.getIdJugador())) return false;
        if (jugadores.size() >= CAPACIDAD_MAXIMA) return false;
        jugadores.put(jugador.getIdJugador(), jugador);
        // Si hay solicitud activa, el jugador entrante queda PENDIENTE.
        if (estado == Estado.SOLICITANDO_INICIO) {
            aceptaciones.put(jugador.getIdJugador(), EstadoAceptacion.PENDIENTE);
        }
        return true;
    }

    public boolean remover(String idJugador) {
        if (idJugador == null) return false;
        Jugador removido = jugadores.remove(idJugador);
        if (removido == null) return false;
        aceptaciones.remove(idJugador);
        // Si el solicitante o el host abandona, cancelamos la solicitud.
        if (idJugador.equals(idJugadorSolicitante)) {
            cancelarSolicitud();
        }
        if (idJugador.equals(idHost) && !jugadores.isEmpty()) {
            this.idHost = jugadores.keySet().iterator().next();
        }
        return true;
    }

    public void iniciarSolicitud(String idSolicitante) {
        if (!jugadores.containsKey(idSolicitante)) return;
        this.estado = Estado.SOLICITANDO_INICIO;
        this.idJugadorSolicitante = idSolicitante;
        this.aceptaciones.clear();
        for (String id : jugadores.keySet()) {
            this.aceptaciones.put(id, id.equals(idSolicitante)
                    ? EstadoAceptacion.ACEPTADO
                    : EstadoAceptacion.PENDIENTE);
        }
    }

    public boolean responderSolicitud(String idJugador, boolean acepta) {
        if (estado != Estado.SOLICITANDO_INICIO) return false;
        if (!jugadores.containsKey(idJugador)) return false;
        aceptaciones.put(idJugador, acepta ? EstadoAceptacion.ACEPTADO : EstadoAceptacion.ESPERANDO);
        return true;
    }

    public boolean todosAceptaron() {
        if (jugadores.isEmpty()) return false;
        for (String id : jugadores.keySet()) {
            EstadoAceptacion ea = aceptaciones.get(id);
            if (ea != EstadoAceptacion.ACEPTADO) return false;
        }
        return true;
    }

    public boolean estaLleno() {
        return jugadores.size() >= CAPACIDAD_MAXIMA;
    }

    public void cancelarSolicitud() {
        this.estado = Estado.ESPERANDO;
        this.idJugadorSolicitante = null;
        this.aceptaciones.clear();
    }

    public void marcarIniciada() {
        this.estado = Estado.INICIADA;
    }

    public List<Jugador> getJugadoresOrdenados() {
        return new ArrayList<>(jugadores.values());
    }

    public Jugador getJugador(String idJugador) {
        return jugadores.get(idJugador);
    }

    public boolean contiene(String idJugador) {
        return jugadores.containsKey(idJugador);
    }

    public int totalJugadores() {
        return jugadores.size();
    }

    public Map<String, EstadoAceptacion> getAceptaciones() {
        return aceptaciones;
    }

    public String getIdPartida() { return idPartida; }
    public String getIdHost() { return idHost; }
    public Estado getEstado() { return estado; }
    public String getIdJugadorSolicitante() { return idJugadorSolicitante; }
}
