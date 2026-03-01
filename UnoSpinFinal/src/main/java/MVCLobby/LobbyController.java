package MVCLobby;

import Dominio.CardColor;

/**
 * CONTROLADOR DEL LOBBY.
 * Actúa como el puente de mando para los casos de uso de Pre-juego:
 * Registro, Configuración e Inicio de partida.
 */
public class LobbyController {
    private final LobbyModel model;

    public LobbyController(LobbyModel model) {
        this.model = model;
    }

    /**
     * Caso de Uso: Registrar Jugador (Req #3 y #4).
     * @return true si el registro fue exitoso y cumple con la unicidad.
     */
    public boolean onRegisterPlayer(String name, String avatar, CardColor color) {
        // Delegamos la validación de negocio al modelo
        return model.registerPlayer(name, avatar, color);
    }

    /**
     * Caso de Uso: Gestión de Consenso (Req #5).
     */
    public void onToggleReady(String playerId, boolean isReady) {
        model.setPlayerReady(playerId, isReady);
    }

    /**
     * Caso de Uso: Iniciar Partida (Req #5).
     * Solo surte efecto si el modelo valida que el quórum está completo.
     */
    public void onRequestStart() {
        model.requestStartMatch();
    }

    /**
     * Caso de Uso: Configurar Partida (Req #1).
     */
    public void onUpdateConfig(int min, int max, int actions, int wilds) {
        model.updateConfiguration(min, max, actions, wilds);
    }
}
