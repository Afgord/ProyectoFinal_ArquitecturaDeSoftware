/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uno.decodificador;

import com.mycompany.componentered.entrada.IReceptorExterno;
import org.uno.eventos.comun.IEventListener;
import org.uno.eventos.comun.MensajeRed;

public class ManejadorRecepcion implements IReceptorExterno {
    private final Deserializador des;
    private final IEventListener listener;

    public ManejadorRecepcion(IEventListener listener) {
        this.des = new Deserializador();
        this.listener = listener;
    }

    @Override
    public void recibir(byte[] datos) {
        MensajeRed mensaje = des.bytesAObjeto(datos);
        if (mensaje != null) {
            listener.onEventoRecibido(mensaje);
        }
    }
}