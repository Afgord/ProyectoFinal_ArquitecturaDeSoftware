package Dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entidad que encapsula la colección de cartas de un jugador.
 * Contiene lógica específica para el conteo de puntos y búsqueda de cartas.
 */
public class Hand {
    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void add(Card card) {
        cards.add(card);
    }

    public boolean remove(Card card) {
        return cards.remove(card);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public int size() {
        return cards.size();
    }

    public void clear() {
        cards.clear();
    }

    /**
     * Calcula los puntos de la mano según el manual.
     */
    public int calculatePoints() {
        int points = 0;
        for (Card c : cards) {
            if (c.getType() == CardType.NUMBER) points += c.getValue();
            else if (c.getColor() == CardColor.BLACK) points += 50;
            else points += 20;
        }
        return points;
    }
}
