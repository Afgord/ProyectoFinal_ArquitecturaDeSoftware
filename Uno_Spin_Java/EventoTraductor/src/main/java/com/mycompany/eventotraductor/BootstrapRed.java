package com.mycompany.eventotraductor;

import Ejercer_Turno.MVC.ModeloJuego;
import Iniciar_Partida.MVC.ModeloLobby;
import comunes.IPublicador;
import entrada.Receptor;
import entrada.ServidorTCP;
import org.codedesc.CodeDescFactory;
import org.codedesc.IDeserializador;
import org.codedesc.ISerializador;
import org.eventos.ejercer_turno.Evento;
import salida.DispatcherFactory;
import salida.IDispatcher;

/**
 * Punto único de cableado de la frontera de red del publicador/consumidor.
 */
public final class BootstrapRed {

    private BootstrapRed() {}

    public static SesionCliente iniciar(String hostBroker, int puertoBroker, int puertoLocal, String idJugadorLocal) {
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        IPublicador publicador = new PublicadorTCP(dispatcher, hostBroker, puertoBroker);

        ISerializador<Evento> serializador = CodeDescFactory.crearSerializador();
        IDeserializador<Evento> deserializador = CodeDescFactory.crearDeserializador();

        ModeloJuego modelo = new ModeloJuego(idJugadorLocal);
        ModeloLobby modeloLobby = new ModeloLobby(idJugadorLocal);
        EventoTraductor traductor = new EventoTraductor(publicador, serializador, idJugadorLocal);

        ReceptorProcesador procesador = new ReceptorProcesador(
            deserializador, modelo, modeloLobby, idJugadorLocal);
        Receptor receptor = new Receptor(procesador);

        ServidorTCP servidor = new ServidorTCP(puertoLocal);
        servidor.addObserver(receptor);
        servidor.iniciar();

        return new SesionCliente(modelo, modeloLobby, traductor, procesador);
    }
}
