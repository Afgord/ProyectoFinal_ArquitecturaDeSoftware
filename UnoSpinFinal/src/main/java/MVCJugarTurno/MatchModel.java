package MVCJugarTurno;

import Contracts.IMatchReadModel;
import Contracts.IObserver;
import Dominio.*;
import java.util.*;

/**
 * IMPLEMENTACIÓN DEL MODELO (CORE).
 * Actúa como el 'Subject' en el patrón Observer y como 'Game Engine'.
 * Esta clase es el "Corazón" del sistema según el MVC de Martin Fowler.
 */
public class MatchModel implements IMatchReadModel {
    // --- ESTADO DEL DOMINIO ---
    private final Match match;
    private final List<IObserver> observers;
    private final Set<String> playersWhoShoutedUno; 
    
    // --- MÁQUINA DE ESTADOS ---
    private boolean waitingForSpin;
    private boolean waitingForColorSelection;
    private boolean waitingForSpecialAction; 
    private boolean showingHands;
    private SpinAction lastSpinResult;
    private String lastSpinMessage = "Ninguno"; // Persistencia para la UI
    private String lastEventLog;

    public MatchModel(String matchId, GameConfiguration config) {
        this.match = new Match(matchId, config);
        this.observers = new ArrayList<>();
        this.playersWhoShoutedUno = new HashSet<>();
        this.lastEventLog = "Esperando que los jugadores se unan al lobby...";
    }

    // ======================================================
    // MÉTODOS DE COMANDO (CONTROLADOR)
    // ======================================================

    public void addPlayerToMatch(String name, String avatar, CardColor color) {
        Player p = new Player(name, avatar, color);
        if (match.addPlayer(p)) {
            log("Jugador " + name + " se ha unido.");
            if (match.getStatus() == MatchStatus.IN_PROGRESS) {
                dealInitialCards(7);
                log("Partida iniciada automáticamente. Turno de: " + match.getCurrentPlayer().getName());
            }
            notifyObservers();
        }
    }

    public void togglePlayerReady(String playerId) {
        Player p = getPlayerById(playerId);
        if (p != null && match.getStatus() == MatchStatus.LOBBY) {
            match.togglePlayerReady(playerId);
            log("Jugador " + p.getName() + " está " + (p.isReady() ? "LISTO" : "NO LISTO"));
            notifyObservers();
        }
    }

    public void startMatch() {
        if (match.requestStart()) {
            dealInitialCards(7);
            log("¡Partida Iniciada! Turno inicial: " + match.getCurrentPlayer().getName());
            notifyObservers();
        }
    }

    public void playCard(String playerId, Card card) {
        if (!isPlayerTurn(playerId) || isSystemBlocked()) return;

        if (isMoveLegal(card)) {
            executeMove(card);
            
            if (checkVictory()) {
                notifyObservers();
                return;
            }

            processCardEffects(card);
            notifyObservers();
        }
    }

    public void drawCard(String playerId) {
        if (!isPlayerTurn(playerId) || isSystemBlocked()) return;

        if (waitingForSpecialAction) {
            waitingForSpecialAction = false;
            log(match.getCurrentPlayer().getName() + " omitió su descarte especial.");
        }

        Card drawn = match.getBoard().getDrawPile().draw();
        if (drawn != null) {
            match.getCurrentPlayer().getHand().add(drawn);
            log(match.getCurrentPlayer().getName() + " robó una carta.");
        }
        
        playersWhoShoutedUno.remove(playerId);
        advanceTurn();
        notifyObservers();
    }

    public void spinWheel(String playerId) {
        if (!isPlayerTurn(playerId) || !waitingForSpin) return;

        this.lastSpinResult = SpinAction.values()[(int) (Math.random() * SpinAction.values().length)];
        this.waitingForSpin = false;
        
        this.lastSpinMessage = lastSpinResult.name(); // Actualizamos el mensaje de ruleta
        log("RULETA: " + lastSpinMessage);
        
        applySpinEffect(lastSpinResult);
        
        if (!waitingForSpecialAction && !waitingForColorSelection) {
            advanceTurn();
        }
        notifyObservers();
    }

    public void selectColor(String playerId, CardColor color) {
        if (!isPlayerTurn(playerId) || !waitingForColorSelection) return;

        match.getBoard().setCurrentColor(color);
        this.waitingForColorSelection = false;
        log("Color cambiado a " + color);

        Card top = match.getBoard().getDiscardPile().peekTop();
        if (top != null && top.getType() == CardType.WILD_DRAW_FOUR) {
            advanceTurn();
            penaltyDraw(match.getCurrentPlayerIndex(), 4);
            log(match.getCurrentPlayer().getName() + " recibe castigo de +4.");
        }

        advanceTurn();
        notifyObservers();
    }

    public void shoutUno(String playerId) {
        Player p = getPlayerById(playerId);
        if (p != null && p.getHand().size() <= 2) {
            playersWhoShoutedUno.add(playerId);
            log("¡" + p.getName() + " GRITÓ UNO!");
            notifyObservers();
        }
    }

    // ======================================================
    // LÓGICA DE NEGOCIO INTERNA
    // ======================================================

    private boolean isMoveLegal(Card card) {
        Card top = match.getBoard().getDiscardPile().peekTop();
        CardColor currentColor = match.getBoard().getCurrentColor();

        if (waitingForSpecialAction) {
            if (lastSpinResult == SpinAction.DISCARD_NUMBER) {
                return card.getType() == CardType.NUMBER;
            }
            if (lastSpinResult == SpinAction.SCORE_REBATE) return true;
        }

        if (card.getColor() == CardColor.BLACK) return true;
        if (card.getColor() == currentColor) return true;
        if (top != null) {
            if (card.getType() == top.getType() && card.getType() != CardType.NUMBER) return true;
            if (card.getType() == CardType.NUMBER && card.getValue() == top.getValue()) return true;
        }

        return false;
    }

    private void executeMove(Card card) {
        Player current = match.getCurrentPlayer();
        
        if (waitingForSpecialAction && lastSpinResult == SpinAction.DISCARD_NUMBER) {
            int valorElegido = card.getValue();
            List<Card> aEliminar = new ArrayList<>();
            for (Card c : current.getHand().getCards()) {
                if (c.getType() == CardType.NUMBER && c.getValue() == valorElegido) {
                    aEliminar.add(c);
                }
            }
            for (Card c : aEliminar) current.getHand().remove(c);
            match.getBoard().getDiscardPile().push(card);
            log(current.getName() + " descartó TODAS las cartas con número " + valorElegido);
        } else {
            current.getHand().remove(card);
            match.getBoard().getDiscardPile().push(card);
            log(current.getName() + " jugó " + card.toString());
        }
    }

    private void processCardEffects(Card card) {
        if (waitingForSpecialAction) {
            waitingForSpecialAction = false;
            advanceTurn();
            return;
        }

        if (card.getColor() == CardColor.BLACK) {
            waitingForColorSelection = true;
            return; 
        }

        match.getBoard().setCurrentColor(card.getColor());

        if (card.isSpin()) {
            waitingForSpin = true;
            advanceTurn(); 
        } else {
            applyActionCard(card);
            advanceTurn();
        }
    }

    private void applyActionCard(Card card) {
        switch (card.getType()) {
            case REVERSE -> {
                match.getBoard().toggleDirection();
                log("¡Sentido de juego cambiado!");
                if (match.getPlayers().size() == 2) advanceTurn();
            }
            case SKIP -> {
                log(match.getPlayers().get(getNextIndex()).getName() + " pierde su turno.");
                advanceTurn();
            }
            case DRAW_TWO -> {
                advanceTurn();
                penaltyDraw(match.getCurrentPlayerIndex(), 2);
                log(match.getCurrentPlayer().getName() + " roba 2 y pierde turno.");
            }
            default -> {}
        }
    }

    private void applySpinEffect(SpinAction action) {
        Player current = match.getCurrentPlayer();
        switch (action) {
            case ALMOST_UNO -> {
                while(current.getHand().size() > 2) {
                    current.getHand().remove(current.getHand().getCards().get(0));
                }
                log(current.getName() + " se queda con solo 2 cartas.");
            }
            case DRAW_UNTIL_BLUE -> drawUntilColor(CardColor.BLUE);
            case DRAW_UNTIL_RED -> drawUntilColor(CardColor.RED);
            case HAND_SWAP -> executeHandSwap();
            case WAR -> executeWar();
            case SHOW_HAND -> this.showingHands = true;
            case DISCARD_NUMBER -> this.waitingForSpecialAction = true;
            case SCORE_REBATE -> executeScoreRebate();
            case DISCARD_COLOR -> executeDiscardColor();
        }
    }

    private void executeWar() {
        int max = -1;
        Player winner = null;
        for (Player p : match.getPlayers()) {
            for (Card c : p.getHand().getCards()) {
                if (c.getType() == CardType.NUMBER && c.getValue() > max) {
                    max = c.getValue();
                    winner = p;
                }
            }
        }
        if (winner != null) {
            log("Ganador de la Guerra: " + winner.getName() + " (Valor " + max + ")");
            List<Card> toRemove = new ArrayList<>();
            for(Card c : winner.getHand().getCards()) {
                if(c.getType() == CardType.NUMBER && c.getValue() == max) toRemove.add(c);
            }
            for(Card c : toRemove) winner.getHand().remove(c);
            checkVictory(); 
        }
    }

    private void executeScoreRebate() {
        int min = Integer.MAX_VALUE;
        int winnerIdx = -1;
        for (int i = 0; i < match.getPlayers().size(); i++) {
            int p = match.getPlayers().get(i).getHand().calculatePoints();
            if (p < min) { min = p; winnerIdx = i; }
        }
        
        if (winnerIdx != -1) {
            forceSetTurn(winnerIdx);
            this.waitingForSpecialAction = true;
            log(match.getCurrentPlayer().getName() + " tiene la puntuación más baja y puede descartar.");
        }
    }

    private void executeDiscardColor() {
        Player current = match.getCurrentPlayer();
        CardColor colorToMatch = match.getBoard().getCurrentColor();
        List<Card> toRemove = new ArrayList<>();
        for(Card c : current.getHand().getCards()) {
            if(c.getColor() == colorToMatch) toRemove.add(c);
        }
        for(Card c : toRemove) current.getHand().remove(c);
        log(current.getName() + " descartó todas sus cartas color " + colorToMatch);
        checkVictory();
    }

    private void executeHandSwap() {
        int n = match.getPlayers().size();
        List<List<Card>> newHands = new ArrayList<>();
        for(Player p : match.getPlayers()) newHands.add(new ArrayList<>(p.getHand().getCards()));

        if (match.getBoard().isClockwise()) {
            newHands.add(0, newHands.remove(newHands.size() - 1));
        } else {
            newHands.add(newHands.remove(0));
        }

        for (int i = 0; i < n; i++) {
            match.getPlayers().get(i).getHand().clear();
            for(Card c : newHands.get(i)) match.getPlayers().get(i).getHand().add(c);
        }
        log("¡Manos intercambiadas según el sentido del juego!");
    }

    private void drawUntilColor(CardColor color) {
        Player p = match.getCurrentPlayer();
        while (true) {
            Card c = match.getBoard().getDrawPile().draw();
            if (c == null) break;
            p.getHand().add(c);
            if (c.getColor() == color) break;
        }
        log(p.getName() + " robó cartas hasta encontrar el color " + color);
    }

    private void advanceTurn() {
        showingHands = false;
        forceSetTurn(getNextIndex());
        log("Turno de: " + match.getCurrentPlayer().getName());
    }

    private int getNextIndex() {
        int n = match.getPlayers().size();
        if (match.getBoard().isClockwise()) return (match.getCurrentPlayerIndex() + 1) % n;
        else return (match.getCurrentPlayerIndex() - 1 + n) % n;
    }

    private void forceSetTurn(int index) {
        try {
            java.lang.reflect.Field field = Match.class.getDeclaredField("currentPlayerIndex");
            field.setAccessible(true);
            field.set(match, index);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void setMatchStatus(MatchStatus newStatus) {
        try {
            java.lang.reflect.Field field = Match.class.getDeclaredField("status");
            field.setAccessible(true);
            field.set(match, newStatus);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void penaltyDraw(int playerIndex, int amount) {
        Player p = match.getPlayers().get(playerIndex);
        for (int i = 0; i < amount; i++) {
            Card c = match.getBoard().getDrawPile().draw();
            if (c != null) p.getHand().add(c);
        }
    }

    private boolean checkVictory() {
        Player current = match.getCurrentPlayer();
        if (current.getHand().size() == 0) {
            if (!playersWhoShoutedUno.contains(current.getId())) {
                log(current.getName() + " OLVIDÓ GRITAR UNO. Penalización de +2.");
                penaltyDraw(match.getCurrentPlayerIndex(), 2);
                advanceTurn();
                return false;
            }
            log("¡PARTIDA FINALIZADA! Ganador: " + current.getName());
            setMatchStatus(MatchStatus.FINISHED);
            return true;
        }
        return false;
    }

    // ======================================================
    // INFRAESTRUCTURA (OBSERVER & LECTURA)
    // ======================================================

    @Override public void subscribe(IObserver observer) { observers.add(observer); }
    private void notifyObservers() { for (IObserver o : observers) o.update(this); }
    private void log(String msg) { this.lastEventLog = msg; System.out.println("[MOTOR]: " + msg); }
    private boolean isPlayerTurn(String id) { return match.getCurrentPlayer().getId().equals(id); }
    
    private boolean isSystemBlocked() { 
        return waitingForSpin || waitingForColorSelection || isFinished(); 
    }
    
    private Player getPlayerById(String id) {
        for(Player p : match.getPlayers()) if(p.getId().equals(id)) return p;
        return null;
    }

    @Override public String getMatchId() { return match.getMatchId(); }
    @Override public MatchStatus getStatus() { return match.getStatus(); }
    @Override public int getCurrentPlayerIndex() { return match.getCurrentPlayerIndex(); }
    @Override public Player getPlayer(int index) { return match.getPlayers().get(index); }
    @Override public int getPlayerCount() { return match.getPlayers().size(); }
    @Override public Card getTopDiscard() { return match.getBoard().getDiscardPile().peekTop(); }
    @Override public CardColor getCurrentColor() { return match.getBoard().getCurrentColor(); }
    @Override public boolean isClockwise() { return match.getBoard().isClockwise(); }
    @Override public boolean isWaitingForSpin() { return waitingForSpin; }
    @Override public boolean isWaitingForColorSelection() { return waitingForColorSelection; }
    @Override public boolean isWaitingForSpecialAction() { return waitingForSpecialAction; }
    @Override public SpinAction getLastSpinResult() { return lastSpinResult; }
    @Override public String getLastSpinMessage() { return lastSpinMessage; }
    @Override public String getLastEventLog() { return lastEventLog; }
    @Override public boolean isFinished() { return match.getStatus() == MatchStatus.FINISHED; }
    public boolean hasPlayerShoutedUno(String playerId) { return playersWhoShoutedUno.contains(playerId); }

    private void dealInitialCards(int cant) {
        for (int i = 0; i < match.getPlayers().size(); i++) penaltyDraw(i, cant);
    }
}