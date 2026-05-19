/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configurar_Partida.MVC;

import Configurar_Partida.Interfaces.IModeloConfigurarPartida;
import Configurar_Partida.Interfaces.ObservadorConfigurarPartida;
import java.util.ArrayList;
import java.util.List;
import org.eventos.ejercer_turno.EventoConfiguracionRechazada;
import org.eventos.ejercer_turno.EventoPartidaConfigurada;

/**
 * Modelo del CU1 - Configurar partida.
 *
 * Su estado cambia únicamente cuando recibe eventos de respuesta
 * provenientes de la red.
 */
public class ModeloConfigurarPartida implements IModeloConfigurarPartida {

    private final List<ObservadorConfigurarPartida> observadores = new ArrayList<>();

    private boolean configuracionExitosa;
    private boolean configuracionRechazada;
    private String mensajeResultado;

    @Override
    public void registrarObservador(ObservadorConfigurarPartida observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    private void notificar() {
        for (ObservadorConfigurarPartida observador : observadores) {
            observador.notificarCambio(this);
        }
    }

    public void aplicarPartidaConfigurada(EventoPartidaConfigurada evento) {
        this.configuracionExitosa = true;
        this.configuracionRechazada = false;
        this.mensajeResultado = "Partida configurada correctamente.";
        notificar();
    }

    public void aplicarConfiguracionRechazada(EventoConfiguracionRechazada evento) {
        this.configuracionExitosa = false;
        this.configuracionRechazada = true;
        this.mensajeResultado = evento.getMotivo();
        notificar();
    }

    @Override
    public boolean isConfiguracionExitosa() {
        return configuracionExitosa;
    }

    @Override
    public boolean isConfiguracionRechazada() {
        return configuracionRechazada;
    }

    @Override
    public String getMensajeResultado() {
        return mensajeResultado;
    }
}