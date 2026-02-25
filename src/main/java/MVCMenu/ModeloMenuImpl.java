package MVCMenu;

import Infraestructura.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * Lógica de dominio pura.
 * Cero dependencias de UI (Swing, AWT, etc.). Podría correr en terminal.
 */
public class ModeloMenuImpl implements IModeloMenu {
    
    private List<Observer<IModeloMenu>> observadores;
    
    // Estado
    private String nombreJugador;
    private String mensajeError;
    private boolean listoParaJugar;

    public ModeloMenuImpl() {
        this.observadores = new ArrayList<>();
        this.nombreJugador = "";
        this.mensajeError = "";
        this.listoParaJugar = false;
    }

    // --- INFRAESTRUCTURA OBSERVER ---

    @Override
    public void registrarObservador(Observer<IModeloMenu> o) {
        if (!observadores.contains(o)) {
            observadores.add(o);
        }
    }

    @Override
    public void removerObservador(Observer<IModeloMenu> o) {
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores() {
        // Se envía a sí mismo (como IModeloMenu) como contexto
        for (Observer<IModeloMenu> o : observadores) {
            o.update(this);
        }
    }

    // --- LÓGICA DE NEGOCIO ---

    @Override
    public void setNombreJugador(String nombre) {
        this.nombreJugador = nombre != null ? nombre.trim() : "";
        this.mensajeError = ""; // Limpiamos errores previos al escribir
        notificarObservadores();
    }

    @Override
    public void iniciarJuego() {
        if (this.nombreJugador.isEmpty()) {
            this.mensajeError = "El nombre no puede estar vacío.";
            this.listoParaJugar = false;
        } else if (this.nombreJugador.length() < 3) {
            this.mensajeError = "El nombre debe tener al menos 3 letras.";
            this.listoParaJugar = false;
        } else {
            this.mensajeError = "";
            this.listoParaJugar = true; // Estado de éxito
        }
        notificarObservadores();
    }

    // --- MÉTODOS DE LECTURA ---

    @Override
    public String getNombreJugador() { return this.nombreJugador; }

    @Override
    public String getMensajeError() { return this.mensajeError; }

    @Override
    public boolean isListoParaJugar() { return this.listoParaJugar; }
}
