package MVCMenu;

/**
 * @author 
 * Implementación del Controlador del Menú.
 * Recibe intenciones de la Vista y las traduce en órdenes al Modelo.
 */
public class ControladorMenu implements IControladorMenu {

    private final IModeloMenu modelo;

    public ControladorMenu(IModeloMenu modelo) {
        this.modelo = modelo;
    }

    @Override
    public void actualizarNombre(String nombre) {
        // En cada tecla pulsada, actualizamos el modelo puede ser util despues que queremaos hacerlo en tiempo real
        modelo.setNombreJugador(nombre);
    }

    @Override
    public void solicitarInicio() {
        System.out.println("CONTROLADOR: Usuario solicitó inicio. Verificando con modelo...");
        modelo.iniciarJuego();
    }
}
