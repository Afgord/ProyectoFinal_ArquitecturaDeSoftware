/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package comunes;

/**
 * Contrato del patrón Subject.
 *
 * Permite registrar, remover y notificar observadores.
 */
public interface Subject {

    /**
     * Registra un observador.
     *
     * @param o observador a registrar
     */
    void addObserver(Observer o);

    /**
     * Remueve un observador registrado.
     *
     * @param o observador a remover
     */
    void removeObserver(Observer o);

    /**
     * Notifica a los observadores registrados usando el estado interno actual
     * del Subject.
     */
    void notifyObservers();
}