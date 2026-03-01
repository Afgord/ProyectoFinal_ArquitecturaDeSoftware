package Dominio;

/**
 * Entidad que representa el estado físico central de la partida.
 */
public class Board {
    private CardColor currentColor;
    private boolean clockwise;
    private final DiscardPile discardPile;
    private final Deck drawPile;

    public Board(GameConfiguration config) {
        this.clockwise = true;
        this.drawPile = new Deck(config);
        this.discardPile = new DiscardPile();
    }

    public void flipFirstCard() {
        Card first = drawPile.draw();
        // El mazo ya asegura que no sea un +4 mediante su lógica interna
        discardPile.push(first);
        this.currentColor = first.getColor();
    }

    public CardColor getCurrentColor() { return currentColor; }
    public void setCurrentColor(CardColor color) { this.currentColor = color; }
    
    public boolean isClockwise() { return clockwise; }
    public void toggleDirection() { this.clockwise = !this.clockwise; }

    public DiscardPile getDiscardPile() { return discardPile; }
    public Deck getDrawPile() { return drawPile; }
}
