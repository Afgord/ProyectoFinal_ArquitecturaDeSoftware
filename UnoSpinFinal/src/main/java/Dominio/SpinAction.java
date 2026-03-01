package Dominio;

/**
 * Las 9 acciones de la ruleta extraídas exactamente del PDF oficial.
 */
public enum SpinAction {
    ALMOST_UNO,         // Casi UNO: Descartar hasta quedarse con 2.
    DISCARD_NUMBER,     // Descartar por número: Elige un número y descarta todos los iguales.
    DISCARD_COLOR,      // Descartar por color: Elige un color y descarta todos los iguales.
    DRAW_UNTIL_BLUE,    // Robar hasta azul.
    DRAW_UNTIL_RED,     // Robar hasta rojo.
    WAR,                // Guerra: Todos muestran la más alta, el ganador descarta.
    SHOW_HAND,          // Mostrar mano: Revelar cartas por N segundos.
    HAND_SWAP,          // Intercambio de manos: Pasar cartas a la izquierda/derecha.
    SCORE_REBATE        // Puntuación más baja: El de menos puntos descarta una a elección.
}
