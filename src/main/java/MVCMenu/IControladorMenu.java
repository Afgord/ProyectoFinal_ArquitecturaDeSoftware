package MVCMenu;

/**
 * Interfaz que define las intenciones del usuario que la Vista puede delegar.
 */
public interface IControladorMenu {
    void actualizarNombre(String nombre);
    void solicitarInicio();
}
