package Infraestructura;

/**
 * @author Angel Beltran
 * Interfaz para los Modelos (Sujetos observables).
 */
public interface Subject {
    void registrarObservador(Observer o);
    void removerObservador(Observer o);
    void notificarObservadores(EventoJuego evento);
}
