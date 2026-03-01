package Dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * AGGREGATE ROOT del Dominio. 
 * Orquesta la relación entre Jugadores, el Tablero y las reglas de inicio del PDF.
 */
public class Match {
    private final String matchId;
    private final List<Player> players;
    private final Board board;
    private final GameConfiguration config;
    private MatchStatus status;
    private int currentPlayerIndex;

    public Match(String matchId, GameConfiguration config) {
        this.matchId = matchId;
        this.config = config;
        this.players = new ArrayList<>();
        this.board = new Board(config);
        this.status = MatchStatus.LOBBY;
        this.currentPlayerIndex = 0;
    }

    /**
     * Agrega un jugador validando que no se repitan colores ni avatares (Req. #4).
     * Si se llega a 4 jugadores, la partida inicia automáticamente (Req. #5).
     */
    public boolean addPlayer(Player newPlayer) {
        if (status != MatchStatus.LOBBY || players.size() >= 4) {
            return false;
        }

        // Validación clínica de duplicados
        for (Player p : players) {
            if (p.getRepresentativeColor() == newPlayer.getRepresentativeColor() ||
                p.getAvatarPath().equals(newPlayer.getAvatarPath())) {
                return false; 
            }
        }

        players.add(newPlayer);

        // Requerimiento #5: Si es el 4to jugador, inicia automático
        if (players.size() == 4) {
            start();
        }
        return true;
    }

    /**
     * Cambia el estado de preparación de un jugador.
     */
    public void togglePlayerReady(String playerId) {
        if (status != MatchStatus.LOBBY) return;
        
        for (Player p : players) {
            if (p.getId().equals(playerId)) {
                p.setReady(!p.isReady());
                break;
            }
        }
    }

    /**
     * Intenta iniciar la partida. Solo permitido si hay al menos 2 jugadores
     * y todos están listos (Req. #5).
     */
    public boolean requestStart() {
        if (status != MatchStatus.LOBBY || players.size() < 2) {
            return false;
        }

        for (Player p : players) {
            if (!p.isReady()) return false;
        }

        start();
        return true;
    }

    private void start() {
        board.flipFirstCard();
        this.status = MatchStatus.IN_PROGRESS;
        // Podríamos barajar aquí si el Board no lo hace en su constructor
    }

    // --- Getters ---
    public String getMatchId() { return matchId; }
    public List<Player> getPlayers() { return players; }
    public Board getBoard() { return board; }
    public MatchStatus getStatus() { return status; }
    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    
    public Player getCurrentPlayer() {
        if (players.isEmpty()) return null;
        return players.get(currentPlayerIndex);
    }
}
