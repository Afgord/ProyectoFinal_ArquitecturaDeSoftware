/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package comunes;

/**
 * Contrato del patrón Observer.
 *
 * Cada observador recibe el contexto producido por el Subject cuando este
 * notifica un cambio.
 */
public interface Observer {

    /**
     * Recibe el contexto generado por el Subject.
     *
     * @param contexto contexto de comunicación notificado
     */
    void update(ContextoConexion contexto);
}