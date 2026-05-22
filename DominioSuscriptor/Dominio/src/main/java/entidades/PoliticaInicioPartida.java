package entidades;

/**
 * Reglas de negocio para el inicio de partida.
 * - 2-3 jugadores: inicio manual por consenso.
 * - 4 jugadores: auto-inicio al completar la sala.
 */
public final class PoliticaInicioPartida {

    public static final int JUGADORES_MINIMOS = 2;

    private PoliticaInicioPartida() {
    }

    public static boolean debeIniciarAutomaticamente(int jugadoresEnSala, EstadoPartida estado) {
        return estado == EstadoPartida.EN_ESPERA
            && jugadoresEnSala == Tablero.CAPACIDAD_MAXIMA;
    }

    public static boolean permiteInicioManual(int jugadoresEnSala, EstadoPartida estado) {
        return estado == EstadoPartida.EN_ESPERA
            && jugadoresEnSala >= JUGADORES_MINIMOS
            && jugadoresEnSala < Tablero.CAPACIDAD_MAXIMA;
    }

    public static boolean puedeIniciar(EstadoPartida estado, int jugadoresEnSala) {
        return debeIniciarAutomaticamente(jugadoresEnSala, estado)
            || permiteInicioManual(jugadoresEnSala, estado);
    }
}
