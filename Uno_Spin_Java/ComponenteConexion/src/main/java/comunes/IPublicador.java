package comunes;

/**
 * Abstracción de la capa de transporte.
 * Independiza al traductor de la tecnología subyacente (Sockets, HTTP, etc).
 */
public interface IPublicador {
    /**
     * Envía un flujo de bytes a través de la red.
     * @param datos Array de bytes del evento ya serializado.
     */
    void enviar(byte[] datos);
}
