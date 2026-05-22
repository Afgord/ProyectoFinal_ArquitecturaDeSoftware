/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.broker;

import entrada.Receptor;
import entrada.ServidorTCP;
import org.codedesc.CodeDescFactory;
import org.codedesc.IDeserializador;
import org.codedesc.ISerializador;
import org.eventos.ejercer_turno.*;
import salida.DispatcherFactory;
import salida.IDispatcher;

/**
 * Clase para simular un Cliente/Jugador con todos los eventos posibles.
 */
public class SimuladorCliente {

    public static void main(String[] args) {
        // === CONFIGURACIÓN MANUAL ===
        final String ID_CLIENTE = args.length > 0 ? args[0] : "1";
        final String IP_BROKER = System.getProperty("uno.host", "127.0.0.1");
        final int PUERTO_BROKER = 5001;
        final int MI_PUERTO_ESCUCHA = 5001 + Integer.parseInt(ID_CLIENTE);

        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();
        ISerializador<EventoAccion> serializador = CodeDescFactory.crearSerializador();
        IDeserializador<Evento> deserializador = CodeDescFactory.crearDeserializador();

        ServidorTCP servidor = new ServidorTCP(MI_PUERTO_ESCUCHA);
        
        servidor.addObserver(new Receptor(bytes -> {
            Evento e = deserializador.bytesAObjeto(bytes);
            if (e != null) {
                System.out.println("\n[RECIBIDO] Tipo: " + e.getClass().getSimpleName());
                if (e instanceof EventoFallo) 
                    System.out.println(" -> Error: " + ((EventoFallo) e).getError());
                if (e instanceof EventoActualizarTurno)
                    System.out.println(" -> Siguiente: " + ((EventoActualizarTurno) e).getIdJugadorTurnoActual());
            }
        }));

        servidor.iniciar();
        System.out.println("=== CLIENTE " + ID_CLIENTE + " ACTIVO EN PUERTO " + MI_PUERTO_ESCUCHA + " ===");

        try {
            Thread.sleep(2000); // Esperar estabilización
            
            EventoAccion eventoParaEnviar = null;

            // --- DESCOMENTA SOLO UNA SECCIÓN PARA PROBAR ---

            // A) PRUEBA: ROBAR CARTA
            eventoParaEnviar = new EventoRobarCarta(true, ID_CLIENTE, "ROBO_NORMAL");

            // B) PRUEBA: TIRAR CARTA (Ejemplo: Dos Rojo)
            // CartaDTO carta = new CartaDTO(Valor.DOS, Colores.ROJO);
            // eventoParaEnviar = new EventoTirarCarta(carta, ID_CLIENTE, "TIRAR_CARTA");

            // C) PRUEBA: PASAR TURNO
            // eventoParaEnviar = new EventoPasarTurno(ID_CLIENTE, "PASAR");

            // D) PRUEBA: GRITAR UNO
            // eventoParaEnviar = new EventoGritar(ID_CLIENTE, "GRITO_UNO");

            if (eventoParaEnviar != null) {
                System.out.println("[ENVIANDO] " + eventoParaEnviar.getClass().getSimpleName());
                byte[] payload = serializador.objetoABytes(eventoParaEnviar);
                dispatcher.dispatch(IP_BROKER, PUERTO_BROKER, payload);
            }

            Thread.currentThread().join();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            servidor.detener();
        }
    }
}