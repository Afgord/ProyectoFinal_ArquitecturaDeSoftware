package Infraestructura;

/**
 * Interfaz genérica para los Sujetos (Modelos).
 */
public interface Subject<T> {
    void registrarObservador(Observer<T> o);
    void removerObservador(Observer<T> o);
    void notificarObservadores();
}
