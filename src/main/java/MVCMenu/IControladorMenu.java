package MVCMenu;

/**
 * @author Angel Beltran
 * INTERFAZ DEL CONTROLADOR 
 * Define las acciones que la Vista puede delegar.
 * Patrón Strategy para la entrada de datos.
 */
public interface IControladorMenu {
    // Acción cuando el usuario escribe 
    void actualizarNombre(String nombre);
    
    // Acción cuando el usuario da clic en "Iniciar"
    void solicitarInicio();
}
