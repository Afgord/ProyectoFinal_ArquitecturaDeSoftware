package Dominio;

import java.util.Stack;

/**
 * Entidad especializada en la pila de descarte.
 */
public class DiscardPile {
    private final Stack<Card> cards;

    public DiscardPile() {
        this.cards = new Stack<>();
    }

    public void push(Card card) {
        cards.push(card);
    }

    public Card peekTop() {
        return cards.isEmpty() ? null : cards.peek();
    }

    public Stack<Card> clearExceptTop() {
        Card top = cards.pop();
        Stack<Card> oldCards = new Stack<>();
        while(!cards.isEmpty()) oldCards.push(cards.pop());
        cards.push(top);
        return oldCards;
    }
}
