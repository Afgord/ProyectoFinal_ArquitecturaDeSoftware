package Dominio;

/**
 * Objeto de configuración que encapsula los requisitos del software #1 del PDF.
 */
public class GameConfiguration {
    public int minNumber = 0;
    public int maxNumber = 9;
    public int numActionCardsPerType = 2; // configurable de 1 a 8
    public int numWildCardsPerType = 4;   // configurable de 1 a 8
    public int showHandSeconds = 5;       

    public static GameConfiguration standard() {
        return new GameConfiguration();
    }
}
