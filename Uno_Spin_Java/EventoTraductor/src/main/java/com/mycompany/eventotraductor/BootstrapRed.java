package com.mycompany.eventotraductor;

import Crear_Partida_Lobby.MVC.ModeloLobby;
import Ejercer_Turno.MVC.ModeloJuego;
import comunes.IPublicador;
import entrada.Receptor;
import entrada.ServidorTCP;
import java.util.ArrayList;
import java.util.List;
import org.codedesc.CodeDescFactory;
import org.codedesc.IDeserializador;
import org.codedesc.ISerializador;
import org.eventos.ejercer_turno.Evento;
import salida.DispatcherFactory;
import salida.IDispatcher;

/**
 * Punto unico de cableado de la frontera de red del publicador/consumidor.
 *
 * Ensambla el flujo outbound (Dispatcher + PublicadorTCP + EventoTraductor)
 * y el flujo inbound (ServidorTCP + Receptor + AplicadorEventos*), y
 * devuelve los modelos (Lobby + Juego) y el EventoTraductor listos para
 * conectarse al MVC desde el Ejecutador. Los modelos del MVC NUNCA
 * importan org.eventos.*: los aplicadores hacen la traduccion.
 */
public final class BootstrapRed {

    private final ModeloLobby modeloLobby;
    private final ModeloJuego modeloJuego;
    private final EventoTraductor traductor;

    private BootstrapRed(ModeloLobby modeloLobby, ModeloJuego modeloJuego, EventoTraductor traductor) {
        this.modeloLobby = modeloLobby;
        this.modeloJuego = modeloJuego;
        this.traductor = traductor;
    }

    public ModeloLobby getModeloLobby() { return modeloLobby; }
    public ModeloJuego getModeloJuego() { return modeloJuego; }
    public EventoTraductor getTraductor() { return traductor; }

    /**
     * Cablea la red del cliente y emite el EventoRegistroConexion para
     * que el broker meta esta conexion en su Directorio. Tras esta
     * llamada el cliente ya recibe broadcasts y puede crear/unirse a un
     * lobby.
     */
    public static BootstrapRed iniciar(String hostBroker, int puertoBroker,
                                       String ipLocal, int puertoLocal,
                                       String idJugadorLocal, String nombre, String urlAvatar) {
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        IPublicador publicador = new PublicadorTCP(dispatcher, hostBroker, puertoBroker);

        ISerializador<Evento> serializador = CodeDescFactory.crearSerializador();
        IDeserializador<Evento> deserializador = CodeDescFactory.crearDeserializador();

        ModeloLobby modeloLobby = new ModeloLobby(idJugadorLocal);
        ModeloJuego modeloJuego = new ModeloJuego(idJugadorLocal);
        EventoTraductor traductor = new EventoTraductor(publicador, serializador, idJugadorLocal);

        // Adaptadores inbound. Orden: lobby primero (eventos del lobby), luego
        // juego (eventos de juego en curso).
        List<IAplicadorEventos> aplicadores = new ArrayList<>();
        aplicadores.add(new AplicadorEventosLobby(modeloLobby));
        aplicadores.add(new AplicadorEventosJuego(modeloJuego));
        ReceptorProcesador procesador = new ReceptorProcesador(deserializador, aplicadores, idJugadorLocal);
        Receptor receptor = new Receptor(procesador);

        ServidorTCP servidor = new ServidorTCP(puertoLocal);
        servidor.addObserver(receptor);
        servidor.iniciar();

        // Registro inmediato en el broker. A partir de aqui los broadcasts del
        // dominio llegan a este puertoLocal.
        traductor.emitirRegistroConexion(nombre, urlAvatar, ipLocal, puertoLocal);

        return new BootstrapRed(modeloLobby, modeloJuego, traductor);
    }
}
