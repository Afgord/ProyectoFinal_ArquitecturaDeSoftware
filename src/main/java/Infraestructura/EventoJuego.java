package Infraestructura;

/**
 * @author Angel Beltran
 * Contexto del evento para el patrón Observer.
 * Evita pasar Object genéricos y da seguridad de tipos.
 */
public class EventoJuego {
    private final TipoEvento tipo;
    private final Object datos;

    public EventoJuego(TipoEvento tipo, Object datos) {
        this.tipo = tipo;
        this.datos = datos;
    }

    public TipoEvento getTipo() {
        return tipo; 
    }
    public Object getDatos() {
        return datos; 
    }
}
