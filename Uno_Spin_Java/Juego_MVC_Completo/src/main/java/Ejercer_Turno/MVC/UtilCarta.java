package Ejercer_Turno.MVC;

import dtos.CartaDTO;
import entidades.Colores;
import entidades.Valor;
import java.awt.Color;

/**
 * Helper estático con mapeos visuales del cliente (sin lógica de DominioSuscriptor).
 *
 * Convierte (Valor, Colores) en propiedades de presentación: Color AWT,
 * ruta de imagen, símbolo a pintar y si se trata de un comodín.
 *
 * No mantiene estado y no se instancia.
 */
public final class UtilCarta {

    public static final Color C_AZUL = Color.CYAN;
    public static final Color C_ROJO = Color.PINK;
    public static final Color C_AMARILLO = Color.ORANGE;
    public static final Color C_VERDE = Color.MAGENTA;
    public static final Color C_NEGRO = Color.BLACK;

    private static final String RAIZ_CARTAS = "/cartas/";
    private static final String AVATAR_DEFAULT = "/avatares/default.jpg";

    private UtilCarta() {}

    public static Color toAwtColor(Colores c) {
        if (c == null) return C_NEGRO;
        return switch (c) {
            case ROJO -> C_ROJO;
            case AZUL -> C_AZUL;
            case AMARILLO -> C_AMARILLO;
            case VERDE -> C_VERDE;
            case NEGRO -> C_NEGRO;
        };
    }

    public static String nombreColor(Colores c) {
        if (c == null) return "";
        return switch (c) {
            case ROJO -> "rojo";
            case AZUL -> "azul";
            case AMARILLO -> "amarillo";
            case VERDE -> "verde";
            case NEGRO -> "negro";
        };
    }

    public static boolean esComodin(Valor v) {
        return v == Valor.CAMBIOCOLOR || v == Valor.MASCUATRO;
    }

    public static String simbolo(Valor v) {
        if (v == null) return "";
        return switch (v) {
            case CERO -> "0";
            case UNO -> "1";
            case DOS -> "2";
            case TRES -> "3";
            case CUATRO -> "4";
            case CINCO -> "5";
            case SEIS -> "6";
            case SIETE -> "7";
            case OCHO -> "8";
            case NUEVE -> "9";
            case PROHIBIDO -> "S";
            case REVERSA -> "R";
            case MASDOS -> "+2";
            case MASCUATRO -> "+4";
            case CAMBIOCOLOR -> "W";
        };
    }

    private static String nombreValor(Valor v) {
        if (v == null) return "atras";
        return switch (v) {
            case CERO -> "0";
            case UNO -> "1";
            case DOS -> "2";
            case TRES -> "3";
            case CUATRO -> "4";
            case CINCO -> "5";
            case SEIS -> "6";
            case SIETE -> "7";
            case OCHO -> "8";
            case NUEVE -> "9";
            case PROHIBIDO -> "prohibido";
            case REVERSA -> "reversa";
            case MASDOS -> "mas2";
            case MASCUATRO -> "mas4";
            case CAMBIOCOLOR -> "cambiocolor";
        };
    }

    /**
     * Ruta del recurso de la imagen frontal de la carta.
     *
     * Para comodines la imagen vive siempre bajo "negro/" porque el arte
     * no cambia con el color elegido; el color seleccionado se aplica como
     * fondo en el panel.
     */
    public static String rutaImagen(CartaDTO c) {
        if (c == null) return RAIZ_CARTAS + "atras.png";
        Valor v = c.getValor();
        if (esComodin(v)) {
            return RAIZ_CARTAS + "negro/" + nombreValor(v) + ".png";
        }
        Colores col = c.getColor();
        return RAIZ_CARTAS + nombreColor(col) + "/" + nombreValor(v) + ".png";
    }

    /**
     * Colores AWT en el orden esperado por el ControlColor existente:
     * AZUL, ROJO, AMARILLO, VERDE.
     */
    public static Color[] coloresConfigurados() {
        return new Color[]{C_AZUL, C_ROJO, C_AMARILLO, C_VERDE};
    }

    /**
     * Mapea posición del jugador en la lista a un avatar determinístico.
     */
    public static String avatarPorIndice(int idx) {
        return switch (idx) {
            case 0 -> "/avatares/0.jpg";
            case 1 -> "/avatares/1.jpg";
            case 2 -> "/avatares/2.jpg";
            case 3 -> "/avatares/3.jpg";
            default -> AVATAR_DEFAULT;
        };
    }
}
