package com.mycompany.eventotraductor;

import comunes.IPublicador;
import salida.IDispatcher;

/**
 * Adaptador entre la abstracción de transporte que conoce el publicador
 * (IPublicador.enviar(byte[])) y el contrato real de salida del
 * ComponenteConexion (IDispatcher.dispatch(host, puerto, byte[])).
 *
 * Mantiene host y puerto del broker fijos en construcción para que el
 * EventoTraductor no necesite conocerlos.
 */
public class PublicadorTCP implements IPublicador {

    private final IDispatcher dispatcher;
    private final String host;
    private final int puerto;

    public PublicadorTCP(IDispatcher dispatcher, String host, int puerto) {
        this.dispatcher = dispatcher;
        this.host = host;
        this.puerto = puerto;
    }

    @Override
    public void enviar(byte[] datos) {
        dispatcher.dispatch(host, puerto, datos);
    }
}
