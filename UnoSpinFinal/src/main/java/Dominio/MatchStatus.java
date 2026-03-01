package Dominio;

/**
 * Estados posibles de la partida para controlar los casos de uso.
 */
public enum MatchStatus {
    LOBBY,          // Creando/Uniendo jugadores
    IN_PROGRESS,    // Jugando turnos
    FINISHED        // Alguien se quedó sin cartas
}
