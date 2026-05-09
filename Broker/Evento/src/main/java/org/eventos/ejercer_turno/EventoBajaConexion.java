package org.eventos.ejercer_turno;

/**
 * Evento de sistema del broker: da de baja una conexión del Directorio.
 * NO se reenvía; lo consume BrokerOrquestador.
 */
public class EventoBajaConexion extends Evento {
    private static final long serialVersionUID = 1L;
    private final String idJugador;

    public EventoBajaConexion(String idJugador, String idEvento) {
        super(idEvento);
        this.idJugador = idJugador;
    }

    public String getIdJugador() { return idJugador; }
}
