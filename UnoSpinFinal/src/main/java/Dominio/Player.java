package Dominio;

import java.util.UUID;

/**
 * Entidad Jugador. 
 * Incluye ID único y estado de preparación para el Lobby.
 */
public class Player {
    private final String id;
    private final String name;
    private final String avatarPath;
    private final CardColor representativeColor;
    private final Hand hand;
    private boolean ready;

    public Player(String name, String avatarPath, CardColor representativeColor) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.avatarPath = avatarPath;
        this.representativeColor = representativeColor;
        this.hand = new Hand();
        this.ready = false;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAvatarPath() { return avatarPath; }
    public CardColor getRepresentativeColor() { return representativeColor; }
    public Hand getHand() { return hand; }
    
    public boolean isReady() { return ready; }
    public void setReady(boolean ready) { this.ready = ready; }
}
