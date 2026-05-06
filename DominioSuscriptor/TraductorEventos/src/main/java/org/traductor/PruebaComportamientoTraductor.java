/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.traductor;

import dtos.CartaDTO;
import entidades.Colores;
import entidades.Valor;
import org.codedesc.CodeDescFactory;
import org.codedesc.ISerializador;
import org.codedesc.IDeserializador;
import org.eventos.ejercer_turno.*;
import entrada.ServidorTCP;
import salida.IDispatcher;
import salida.DispatcherFactory;

public class PruebaComportamientoTraductor {

    public static void main(String[] args) {
        final String IP_SERVIDOR = "192.168.100.97";
        final int PUERTO_SERVIDOR = 5000;
        final int PUERTO_CLIENTE = 5001;

        ISerializador<EventoAccion> serializador = CodeDescFactory.crearSerializador();
        IDeserializador<Evento> deserializador = CodeDescFactory.crearDeserializador();
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();

        ServidorTCP receptor = new ServidorTCP(PUERTO_CLIENTE);
        receptor.addObserver((contexto) -> {
            Evento respuesta = deserializador.bytesAObjeto(contexto.getBytes()); 
            
            if (respuesta instanceof EventoActualizarTurno) {
                EventoActualizarTurno act = (EventoActualizarTurno) respuesta;
                System.out.println("\n[CLIENTE] Notificación: " + act.getIdEvento());
                System.out.println("[CLIENTE] Turno actual: " + act.getIdJugadorTurnoActual());
            } else if (respuesta instanceof EventoResultadoRuleta) {
                EventoResultadoRuleta res = (EventoResultadoRuleta) respuesta;
                System.out.println("\n[CLIENTE] RULETA: " + res.getResultadoRuleta());
            }
        });

        new Thread(() -> receptor.iniciar()).start();

        try {
            Thread.sleep(2000);

            CartaDTO carta = new CartaDTO(Valor.REVERSA, Colores.VERDE);
            EventoTirarCarta evento = new EventoTirarCarta(carta, "4", "tirar");
            //EventoRobarCarta evento = new EventoRobarCarta(true, "1", "Robar");

            byte[] payload = serializador.objetoABytes(evento);
            
            System.out.println("[CLIENTE] Enviando jugada al servidor...");
            dispatcher.dispatch(IP_SERVIDOR, PUERTO_SERVIDOR, payload);

            System.out.println("[CLIENTE] Escuchando en puerto " + PUERTO_CLIENTE + "...");
            Thread.sleep(30000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            receptor.detener();
            System.exit(0);
        }
    }
}