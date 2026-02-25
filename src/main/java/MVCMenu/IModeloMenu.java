package MVCMenu;

import Infraestructura.Subject;

/**
 * Interfaz del Modelo.
 * Extiende Subject tipado para que la Vista reciba esta misma interfaz como contexto.
 */
public interface IModeloMenu extends Subject<IModeloMenu> {
    // Métodos para que la Vista consulte el estado (Pull Model)
    String getNombreJugador();
    String getMensajeError();
    boolean isListoParaJugar();

    // Métodos para que el Controlador modifique el estado
    void setNombreJugador(String nombre);
    void iniciarJuego();
}
