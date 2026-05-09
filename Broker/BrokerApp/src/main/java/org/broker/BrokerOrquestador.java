/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.broker;

import salida.IDispatcher;
import org.codedesc.*;
import org.directorios.*;
import org.eventos.ejercer_turno.*;
/**
 * 
 * @author lagar
 */
public class BrokerOrquestador {
    private final IDispatcher dispatcher;
    private final IDeserializador<Evento> deserializador;
    private final ISerializador<Evento> serializador;
    private final IDirectorio directorio;
    private final String ID_DOMINIO = "DOMINIO_SISTEMA";

    public BrokerOrquestador(IDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.directorio = DirectorioFactory.crearNuevoDirectorio();
        this.deserializador = CodeDescFactory.crearDeserializador();
        this.serializador = CodeDescFactory.crearSerializador();
        directorio.registrarConexion(new Conexion(ID_DOMINIO, "192.168.100.97", 5000));
    }

    public void rutarEvento(byte[] bytes) {
        Evento evento = deserializador.bytesAObjeto(bytes);
        if (evento == null) return;

        // Eventos del sistema del broker: actualizan el Directorio y NO se reenvian.
        if (evento instanceof EventoRegistroConexion reg) {
            directorio.registrarConexion(new Conexion(reg.getIdJugador(), reg.getIp(), reg.getPuerto()));
            return;
        }
        if (evento instanceof EventoBajaConexion baja) {
            directorio.removerConexion(baja.getIdJugador());
            return;
        }

        byte[] bytesAEnviar = serializador.objetoABytes(evento);

        if (evento instanceof EventoAccion) {
            enviarAEntidad(ID_DOMINIO, bytesAEnviar);
        } else {
            hacerBroadcast(bytesAEnviar);
        }
    }

    private void enviarAEntidad(String id, byte[] datos) {
        Conexion con = directorio.obtenerConexion(id);
        if (con != null) dispatcher.dispatch(con.getIp(), con.getPuerto(), datos);
    }

    private void hacerBroadcast(byte[] datos) {
        for (Conexion con : directorio.obtenerTodos()) {
            if (!con.getIdJugador().equals(ID_DOMINIO)) {
                dispatcher.dispatch(con.getIp(), con.getPuerto(), datos);
            }
        }
    }

    public IDirectorio getDirectorio() { return directorio; }
}