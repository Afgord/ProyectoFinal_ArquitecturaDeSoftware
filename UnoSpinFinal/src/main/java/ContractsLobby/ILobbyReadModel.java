package ContractsLobby;

import Dominio.CardColor;
import Dominio.Player;
import java.util.List;

/**
 * Interfaz de lectura segregada para el Lobby.
 * La vista puede consultar quién está, pero no puede agregar a nadie directamente.
 */
public interface ILobbyReadModel {
    void subscribe(IObserverLobby observer);
    
    // Estado de Jugadores
    List<Player> getJoinedPlayers();
    int getPlayerCount();
    boolean isPlayerReady(String playerId);
    
    // Gestión de Recursos (Req #4: Colores y Avatares no repetidos)
    List<CardColor> getAvailableColors();
    List<String> getAvailableAvatars();
    
    // Estado de Inicio (Req #5)
    boolean canStartMatch();
    boolean isMatchStarting();
    
    // Configuración actual
    int getMinNumber();
    int getMaxNumber();
}
