package org.broker;

import org.codedesc.CodeDescFactory;
import org.codedesc.ISerializador;
import org.eventos.ejercer_turno.EventoAccion;
import org.eventos.ejercer_turno.EventoIniciarPartida;
import salida.DispatcherFactory;
import salida.IDispatcher;

/** Envía EventoIniciarPartida al broker (simula click en lobby). */
public class SoloIniciarPartida {

    public static void main(String[] args) throws Exception {
        String host = System.getProperty("uno.host", "127.0.0.1");
        String idJugador = args.length > 0 ? args[0] : "1";
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        ISerializador<EventoAccion> serializador = CodeDescFactory.crearSerializador();
        EventoIniciarPartida evt = new EventoIniciarPartida(idJugador, "INICIAR_MANUAL");
        dispatcher.dispatch(host, 5001, serializador.objetoABytes(evt));
        System.out.println("[SOLO-INICIAR] EventoIniciarPartida enviado por jugador " + idJugador);
        Thread.sleep(2000);
    }
}
