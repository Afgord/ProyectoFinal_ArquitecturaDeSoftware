package Contracts;

import Dominio.*;

/**
 * PRINCIPIO DE SEGREGACIÓN DE INTERFACES.
 * Expone solo los métodos de lectura para la Vista.
 */
public interface IMatchReadModel {
    void subscribe(IObserver observer);
    
    String getMatchId();
    MatchStatus getStatus();
    int getCurrentPlayerIndex();
    Player getPlayer(int index); 
    int getPlayerCount();
    
    Card getTopDiscard();
    CardColor getCurrentColor();
    boolean isClockwise();
    
    boolean isWaitingForSpin();
    boolean isWaitingForColorSelection();
    boolean isWaitingForSpecialAction(); 
    SpinAction getLastSpinResult();
    
    /**
     * Retorna el nombre de la última acción de ruleta para 
     * mostrarla de forma persistente en la interfaz.
     */
    String getLastSpinMessage(); 
    
    String getLastEventLog();
    boolean isFinished();
}
