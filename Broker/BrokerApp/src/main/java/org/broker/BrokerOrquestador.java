/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.broker;

import comunes.ContextoConexion;
import comunes.Observer;
import entrada.IReceptorExterno;
import org.codedesc.CodeDescFactory;
import org.codedesc.IDeserializador;
import org.codedesc.ISerializador;
import org.directorios.DirectorioFactory;
import org.directorios.IDirectorio;
import org.eventos.ejercer_turno.Evento;
import org.eventos.ejercer_turno.EventoAccion;
import org.eventos.ejercer_turno.EventoFallo;
import org.eventos.ejercer_turno.EventoGritar;
import org.eventos.ejercer_turno.EventoPasarTurno;
import org.eventos.ejercer_turno.EventoRobarCarta;
import org.eventos.ejercer_turno.EventoTirarCarta;
import salida.IDispatcher;

/**
 *
 * @author lagar
 */
public class BrokerOrquestador implements Observer{
    private final IDispatcher dispatcher;
    private final IReceptorExterno receptor;
    private final IDeserializador<EventoAccion> deserializador;
    private final ISerializador<Evento> serializador;
    private final IDirectorio directorio;

    public BrokerOrquestador(IDispatcher dispatcher, IReceptorExterno receptor) {
        this.dispatcher = dispatcher;
        this.receptor = receptor;
        this.directorio = DirectorioFactory.crearNuevoDirectorio();
        this.deserializador = CodeDescFactory.crearDeserializador();
        this.serializador = CodeDescFactory.crearSerializador();
    }
    
    @Override
    public void update(ContextoConexion contexto) {
        Evento evento = deserializador.bytesAObjeto(contexto.getBytes());
        if(evento != null){
            if(evento instanceof EventoTirarCarta ||
                evento instanceof EventoRobarCarta ||
                    evento instanceof EventoGritar ||
                    evento instanceof EventoPasarTurno){
                byte[] bytesAEnviar = serializador.objetoABytes(evento);
                dispatcher.dispatch("192.168.100.12", 5000, bytesAEnviar);
            } else if (evento instanceof EventoFallo){
                byte[] bytesAEnviar = serializador.objetoABytes(evento);
                dispatcher.dispatch("192.168.100.12", 5002, bytesAEnviar);
            } else {
                byte[] bytesAEnviar = serializador.objetoABytes(evento);
                dispatcher.dispatch("192.168.100.12", 5002, bytesAEnviar);
                dispatcher.dispatch("192.168.100.12", 5003, bytesAEnviar);
                dispatcher.dispatch("192.168.100.12", 5004, bytesAEnviar);
                dispatcher.dispatch("192.168.100.12", 5005, bytesAEnviar);
            }  
        }
    }
    
}
