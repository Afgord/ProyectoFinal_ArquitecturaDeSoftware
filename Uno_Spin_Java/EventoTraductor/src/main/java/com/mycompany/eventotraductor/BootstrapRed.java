package com.mycompany.eventotraductor;

import Configurar_Partida.MVC.ModeloConfigurarPartida;
import Ejercer_Turno.MVC.ModeloJuego;
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
 *
 * Ensambla el flujo outbound (Dispatcher + EventoTraductor) y
 * el flujo inbound (ServidorTCP + Receptor + ReceptorProcesador), y devuelve el
 * ModeloJuego y el EventoTraductor listos para conectarse al MVC desde el
 * Ejecutador.
 *
 * El idJugadorLocal, host y puertos los provee el Directorio (lobby) en una
 * iteración futura; mientras tanto el Ejecutador los pasa hardcoded para debug.
 */
public final class BootstrapRed {

    private final ModeloJuego modelo;
    private final EventoTraductor traductor;
    private final ModeloConfigurarPartida modeloConfiguracion;

    private BootstrapRed(
            ModeloJuego modelo,
            ModeloConfigurarPartida modeloConfiguracion,
            EventoTraductor traductor
    ) {
        this.modelo = modelo;
        this.modeloConfiguracion = modeloConfiguracion;
        this.traductor = traductor;
    }

    public ModeloConfigurarPartida getModeloConfiguracion() {
        return modeloConfiguracion;
    }

    public ModeloJuego getModelo() {
        return modelo;
    }

    public EventoTraductor getTraductor() {
        return traductor;
    }

    public static BootstrapRed iniciar(String hostBroker, int puertoBroker, int puertoLocal, String idJugadorLocal) {
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();

        ISerializador<Evento> serializador = CodeDescFactory.crearSerializador();
        IDeserializador<Evento> deserializador = CodeDescFactory.crearDeserializador();

        ModeloJuego modelo = new ModeloJuego(idJugadorLocal);
        ModeloConfigurarPartida modeloConfiguracion = new ModeloConfigurarPartida();
        EventoTraductor traductor
                = new EventoTraductor(
                        dispatcher,
                        serializador,
                        idJugadorLocal,
                        hostBroker,
                        puertoBroker
                );

        ReceptorProcesador procesador
                = new ReceptorProcesador(deserializador, modelo, modeloConfiguracion, idJugadorLocal);
        Receptor receptor = new Receptor(procesador);

        ServidorTCP servidor = new ServidorTCP(puertoLocal);
        servidor.addObserver(receptor);
        servidor.iniciar();

        return new BootstrapRed(modelo, modeloConfiguracion, traductor);
    }
}
