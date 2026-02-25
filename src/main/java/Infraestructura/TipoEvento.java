package Infraestructura;

/**
 * @author Angel Beltran
 * Define el tipo de evento que ocurrió en el sistema.
 * Esto ayuda a la Vista a saber qué parte de la pantalla actualizar.
 */
public enum TipoEvento {
    // Eventos Generales
    ESTADO_CAMBIADO,
    
    // Eventos del Menú
    NOMBRE_JUGADOR_ACTUALIZADO,
    ERROR_VALIDACION,
    PARTIDA_INICIADA, // Indica que el modelo está listo para arrancar
    
    // Eventos del Juego (Futuros)
    CARTA_JUGADA,
    TURNO_FINALIZADO
}
