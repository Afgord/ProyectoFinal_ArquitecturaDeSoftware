package Dominio;

import java.util.Collections;
import java.util.Stack;

/**
 * Motor de gestión física de las pilas de robo y descarte.
 */
public class Deck {
    private final Stack<Card> drawPile;
    private final Stack<Card> discardPile;

    public Deck(GameConfiguration config) {
        this.drawPile = new Stack<>();
        this.discardPile = new Stack<>();
        build(config);
    }

    private void build(GameConfiguration config) {
        drawPile.clear();
        discardPile.clear();
        
        CardColor[] colors = {CardColor.RED, CardColor.BLUE, CardColor.GREEN, CardColor.YELLOW};

        for (CardColor color : colors) {
            // 1. Cartas Cero
            if (config.minNumber <= 0) {
                drawPile.push(new Card(color + "_0", color, CardType.NUMBER, 0, false));
            }

            // 2. Números del 1 al 9 (Dos de cada uno)
            for (int i = Math.max(1, config.minNumber); i <= config.maxNumber; i++) {
                boolean firstIsSpin = (i >= 1 && i <= 5);
                drawPile.push(new Card(color + "_" + i + "_A", color, CardType.NUMBER, i, firstIsSpin));
                drawPile.push(new Card(color + "_" + i + "_B", color, CardType.NUMBER, i, false));
            }

            // 3. Acciones
            for (int k = 0; k < config.numActionCardsPerType; k++) {
                drawPile.push(new Card(color + "_SKIP_" + k, color, CardType.SKIP, -1, false));
                drawPile.push(new Card(color + "_REVERSE_" + k, color, CardType.REVERSE, -1, false));
                drawPile.push(new Card(color + "_DRAW2_" + k, color, CardType.DRAW_TWO, -1, false));
            }
        }

        // 4. Comodines
        for (int k = 0; k < config.numWildCardsPerType; k++) {
            drawPile.push(new Card("WILD_" + k, CardColor.BLACK, CardType.WILD, -1, false));
            drawPile.push(new Card("WILD_D4_" + k, CardColor.BLACK, CardType.WILD_DRAW_FOUR, -1, false));
        }

        Collections.shuffle(drawPile);
    }

    public Card draw() {
        if (drawPile.isEmpty()) recycle();
        return drawPile.isEmpty() ? null : drawPile.pop();
    }

    public void discard(Card card) {
        discardPile.push(card);
    }

    public Card peekDiscard() {
        return discardPile.isEmpty() ? null : discardPile.peek();
    }

    private void recycle() {
        if (discardPile.size() <= 1) return;
        Card top = discardPile.pop();
        drawPile.addAll(discardPile);
        discardPile.clear();
        discardPile.push(top);
        Collections.shuffle(drawPile);
    }
}
