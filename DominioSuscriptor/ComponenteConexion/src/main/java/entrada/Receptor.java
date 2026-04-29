/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entrada;

import comunes.ContextoConexion;
import comunes.Observer;

/**
 * Observer del flujo de entrada.
 *
 * Recibe el contexto notificado por ServidorTCP y entrega los bytes al
 * subsistema externo.
 */
public class Receptor implements Observer {

    /**
     * Contrato externo que consumirá los bytes recibidos.
     */
    private final IReceptorExterno receptorExterno;

    public Receptor(IReceptorExterno receptorExterno) {
        this.receptorExterno = receptorExterno;
    }

    /**
     * Recibe el contexto de comunicación y delega sus bytes al receptor externo.
     *
     * @param contexto contexto recibido desde el Subject
     */
    @Override
    public void update(ContextoConexion contexto) {
        if (contexto == null || contexto.getBytes() == null || contexto.getBytes().length == 0) {
            System.out.println("[Receptor] Contexto inválido.");
            return;
        }

        System.out.println("[Receptor] Bytes recibidos desde el mecanismo de entrada.");
        receptorExterno.recibir(contexto.getBytes());
    }
}