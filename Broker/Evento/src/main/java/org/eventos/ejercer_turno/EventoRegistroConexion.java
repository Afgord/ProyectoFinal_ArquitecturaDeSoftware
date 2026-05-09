package org.eventos.ejercer_turno;

/**
 * Evento de sistema del broker: registra una conexión (jugador) en el
 * Directorio. NO se reenvía a nadie; lo consume directamente
 * BrokerOrquestador antes del ruteo normal.
 */
public class EventoRegistroConexion extends Evento {
    private static final long serialVersionUID = 1L;
    private final String idJugador;
    private final String nombre;
    private final String ip;
    private final int puerto;
    private final String urlAvatar;

    public EventoRegistroConexion(String idJugador, String nombre, String ip, int puerto, String urlAvatar, String idEvento) {
        super(idEvento);
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.ip = ip;
        this.puerto = puerto;
        this.urlAvatar = urlAvatar;
    }

    public String getIdJugador() { return idJugador; }
    public String getNombre() { return nombre; }
    public String getIp() { return ip; }
    public int getPuerto() { return puerto; }
    public String getUrlAvatar() { return urlAvatar; }
}
