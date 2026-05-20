/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.eventotraductor;

import Configurar_Partida.MVC.ControlConfigurarPartida;
import Configurar_Partida.MVC.FrmConfigurarPartida;
import Configurar_Partida.MVC.ModeloConfigurarPartida;

/**
 * Punto de entrada independiente para probar el CU1 - Configurar partida.
 *
 * Inicializa la frontera de red del cliente y conecta:
 * Vista -> Control -> EventoTraductor -> Broker
 * así como:
 * Broker -> ReceptorProcesador -> Modelo -> Vista.
 */
public class EjecutadorConfigurarPartida {

    /** Misma IP configurada actualmente para el Broker en el proyecto. */
    private static final String HOST_BROKER = "127.0.0.1";

    /** Puerto de escucha del Broker. */
    private static final int PUERTO_BROKER = 5001;

    /** Jugador anfitrión de prueba. */
    private static final String ID_JUGADOR_LOCAL = "1";

    /**
     * Coincide con el esquema actual del proyecto:
     * jugador "n" escucha en 5001 + n.
     */
    private static final int PUERTO_LOCAL =
            5001 + Integer.parseInt(ID_JUGADOR_LOCAL);

    public static void main(String[] args) {
        BootstrapRed bootstrap = BootstrapRed.iniciar(
                HOST_BROKER,
                PUERTO_BROKER,
                PUERTO_LOCAL,
                ID_JUGADOR_LOCAL
        );

        EventoTraductor eventos = bootstrap.getTraductor();
        ModeloConfigurarPartida modeloConfiguracion =
                bootstrap.getModeloConfiguracion();

        ControlConfigurarPartida control =
                new ControlConfigurarPartida(eventos);

        java.awt.EventQueue.invokeLater(() -> {
            FrmConfigurarPartida frm =
                    new FrmConfigurarPartida(control, modeloConfiguracion);
            frm.setVisible(true);
        });
    }
}