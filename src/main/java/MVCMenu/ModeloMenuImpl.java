package MVCMenu;

import Infraestructura.Subject;
import Infraestructura.Observer;
import Infraestructura.EventoJuego;
import Infraestructura.TipoEvento;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Angel Beltran
 * Implementación del Modelo del Menú.
 * Contiene el estado de la configuración inicial de la partida.
 */
public class ModeloMenuImpl implements IModeloMenu {
    // Infraestructura Observer
    private List<Observer> observadores;
    
    // Estado del Modelo
    private String nombreJugador;
    private String mensajeError;
    private boolean listoParaJugar;

    public ModeloMenuImpl() {
        this.observadores = new ArrayList<>();
        this.nombreJugador = "";
        this.mensajeError = "";
        this.listoParaJugar = false;
    }

    //implementacion del patron observer y sus subjects

    @Override
    public void registrarObservador(Observer o) {
        if (!observadores.contains(o)) {
            observadores.add(o);
        }
    }

    @Override
    public void removerObservador(Observer o) {
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores(EventoJuego evento) {
        for (Observer o : observadores) {
            o.update(evento); // error
        }
    }

    //logica de negocio

    @Override
    public void setNombreJugador(String nombre) {
        // Regla de Negocio: Limpiar espacios y validar longitud básica
        this.nombreJugador = nombre != null ? nombre.trim() : "";
        
        // Notificamos que el dato cambió (para que la vista quizá habilite el botón o muestre el texto)
        notificarObservadores(new EventoJuego(TipoEvento.NOMBRE_JUGADOR_ACTUALIZADO, this.nombreJugador));
    }

    @Override
    public void iniciarJuego() {
        // Validación final antes de iniciar
        if (nombreJugador.isEmpty()) {
            this.mensajeError = "¡El nombre no puede estar vacío!";
            notificarObservadores(new EventoJuego(TipoEvento.ERROR_VALIDACION, this.mensajeError));
        } else if (nombreJugador.length() < 3) {
            this.mensajeError = "El nombre es muy corto (mínimo 3 letras).";
            notificarObservadores(new EventoJuego(TipoEvento.ERROR_VALIDACION, this.mensajeError));
        } else {
            // Todo correcto
            this.mensajeError = "";
            this.listoParaJugar = true;
            // Notificamos éxito. Aquí el Router u observador principal sabría que debe cambiar de pantalla
            notificarObservadores(new EventoJuego(TipoEvento.PARTIDA_INICIADA, null));
        }
    }

    //getters para que la vista jale los datos

    @Override
    public String getNombreJugador() {
        return nombreJugador;
    }

    @Override
    public String getMensajeError() {
        return mensajeError;
    }

    @Override
    public boolean isListoParaJugar() {
        return listoParaJugar;
    }
}
