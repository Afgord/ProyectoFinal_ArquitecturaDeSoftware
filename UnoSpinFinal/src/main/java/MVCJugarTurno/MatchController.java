package MVCJugarTurno;

import Dominio.Card;
import Dominio.CardColor;

/**
 * CONTROLADOR (MANDO).
 * Su única responsabilidad es traducir las interacciones del usuario 
 * en llamadas a métodos del Modelo. No contiene lógica de reglas de juego.
 */
public class MatchController {
    private final MatchModel model;

    public MatchController(MatchModel model) {
        this.model = model;
    }

    public void onAddPlayer(String name, String avatar, CardColor color) {
        model.addPlayerToMatch(name, avatar, color);
    }

    public void onStartGame() {
        model.startMatch();
    }

    public void onPlayCard(String playerId, Card card) {
        model.playCard(playerId, card);
    }

    public void onDrawCard(String playerId) {
        model.drawCard(playerId);
    }

    public void onSpinWheel(String playerId) {
        model.spinWheel(playerId);
    }

    public void onSelectColor(String playerId, CardColor color) {
        model.selectColor(playerId, color);
    }

    public void onShoutUno(String playerId) {
        model.shoutUno(playerId);
    }
}
