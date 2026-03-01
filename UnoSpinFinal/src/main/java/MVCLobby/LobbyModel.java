package MVCLobby;

import Dominio.CardColor;
import Dominio.GameConfiguration;
import Dominio.Player;
import ContractsLobby.ILobbyReadModel;
import ContractsLobby.IObserverLobby;
import java.util.*;

/**
 * MODELO DEL LOBBY.
 * Responsable de los casos de uso: Registrar Jugador, Validar Unicidad 
 * y Gestionar el Quórum de inicio.
 */
public class LobbyModel implements ILobbyReadModel {
    private final List<Player> players;
    private final List<IObserverLobby> observers;
    private GameConfiguration config;
    private boolean matchStarting = false;

    // Recursos de personalización (Req #4)
    private final List<CardColor> allColors = Arrays.asList(CardColor.RED, CardColor.BLUE, CardColor.GREEN, CardColor.YELLOW);
    private final List<String> allAvatars = Arrays.asList("avatar1.png", "avatar2.png", "avatar3.png", "avatar4.png");

    public LobbyModel() {
        this.players = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.config = GameConfiguration.standard();
    }

    // ======================================================
    // MÉTODOS DE COMANDO (CONTROLADOR)
    // ======================================================

    /**
     * Req #3 y #4: Registro expreso con validación de unicidad.
     */
    public boolean registerPlayer(String name, String avatar, CardColor color) {
        if (players.size() >= 4 || matchStarting) return false;

        // Validación clínica: No repetir nombre, avatar ni color (Req #4)
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(name) || 
                p.getAvatarPath().equals(avatar) || 
                p.getRepresentativeColor() == color) {
                return false; 
            }
        }

        Player newPlayer = new Player(name, avatar, color);
        players.add(newPlayer);
        
        // Req #5: Si llega el 4to, el juego inicia automáticamente.
        if (players.size() == 4) {
            this.matchStarting = true;
        }

        notifyObservers();
        return true;
    }

    /**
     * Req #5: Gestión de consenso.
     */
    public void setPlayerReady(String playerId, boolean ready) {
        for (Player p : players) {
            if (p.getId().equals(playerId)) {
                p.setReady(ready);
                break;
            }
        }
        notifyObservers();
    }

    /**
     * Req #5: Solicitar inicio consensuado (2-3 jugadores).
     */
    public void requestStartMatch() {
        if (canStartMatch()) {
            this.matchStarting = true;
            notifyObservers();
        }
    }

    /**
     * Req #1: Configuración de parámetros de mazo.
     */
    public void updateConfiguration(int min, int max, int actionCards, int wildCards) {
        // Validación de rangos según PDF (Acción/Comodines: 1 a 8)
        config.minNumber = Math.max(0, min);
        config.maxNumber = Math.min(9, max);
        config.numActionCardsPerType = Math.max(1, Math.min(8, actionCards));
        config.numWildCardsPerType = Math.max(1, Math.min(8, wildCards));
        notifyObservers();
    }

    // ======================================================
    // IMPLEMENTACIÓN DE LECTURA (VISTA)
    // ======================================================

    @Override public void subscribe(IObserverLobby o) { observers.add(o); }
    private void notifyObservers() { for (IObserverLobby o : observers) o.update(this); }

    @Override public List<Player> getJoinedPlayers() { return Collections.unmodifiableList(players); }
    @Override public int getPlayerCount() { return players.size(); }

    @Override public List<CardColor> getAvailableColors() {
        List<CardColor> available = new ArrayList<>(allColors);
        for (Player p : players) available.remove(p.getRepresentativeColor());
        return available;
    }

    @Override public List<String> getAvailableAvatars() {
        List<String> available = new ArrayList<>(allAvatars);
        for (Player p : players) available.remove(p.getAvatarPath());
        return available;
    }

    @Override public boolean isPlayerReady(String id) {
        for(Player p : players) if(p.getId().equals(id)) return p.isReady();
        return false;
    }

    @Override public boolean canStartMatch() {
        if (players.size() < 2) return false;
        // Caso automático 4 jugadores
        if (players.size() == 4) return true;
        // Caso consensuado 2-3 jugadores (todos deben estar listos)
        for (Player p : players) if (!p.isReady()) return false;
        return true;
    }

    @Override public boolean isMatchStarting() { return matchStarting; }
    @Override public int getMinNumber() { return config.minNumber; }
    @Override public int getMaxNumber() { return config.maxNumber; }
    
    public GameConfiguration getFinalConfig() { return config; }
}
