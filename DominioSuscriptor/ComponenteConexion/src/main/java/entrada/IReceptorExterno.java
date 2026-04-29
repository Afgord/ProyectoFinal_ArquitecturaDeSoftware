/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package entrada;

/**
 * Contrato externo para cualquier subsistema que desee recibir bytes desde el
 * mecanismo de entrada.
 */
public interface IReceptorExterno {

    /**
     * Recibe bytes entregados por el componente de conexión.
     *
     * @param bytes bytes recibidos
     */
    void recibir(byte[] bytes);
}
