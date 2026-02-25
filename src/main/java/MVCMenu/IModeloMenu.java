package MVCMenu;

import Infraestructura.Observer;
import Infraestructura.Subject;

/**
 * @author Angel Beltran
 * INTERFAZ DEL MODELO (MENU)
 * Define qué datos puede consultar la vista y qué acciones permite el modelo.
 * Extiende Subject para obligar a que sea observable.
 */
public interface IModeloMenu extends Subject {
    // metodos de lectura
    String getNombreJugador();
    String getMensajeError();
    boolean isListoParaJugar();

    // metodos de escritura que usa el controlador
    void setNombreJugador(String nombre);
    void iniciarJuego(); // valida y cambia al estado
}
