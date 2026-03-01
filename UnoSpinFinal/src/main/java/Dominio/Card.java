package Dominio;

import java.util.Objects;

/**
 * Objeto de Valor inmutable que representa una carta física.
 * Un 5 rojo normal es lógicamente igual a otro 5 rojo normal.
 */
public class Card {
    private final String id;
    private final CardColor color;
    private final CardType type;
    private final int value;    // 0-9 para números, -1 para el resto.
    private final boolean isSpin;

    public Card(String id, CardColor color, CardType type, int value, boolean isSpin) {
        this.id = id;
        this.color = color;
        this.type = type;
        this.value = value;
        this.isSpin = isSpin;
    }

    public String getId() { return id; }
    public CardColor getColor() { return color; }
    public CardType getType() { return type; }
    public int getValue() { return value; }
    public boolean isSpin() { return isSpin; }

    @Override
    public String toString() {
        return String.format("%s %s %s%s", 
            color, type, (value >= 0 ? value : ""), (isSpin ? " [SPIN]" : ""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return id.equals(card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
