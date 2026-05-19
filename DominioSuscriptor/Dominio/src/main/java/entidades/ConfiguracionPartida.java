/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import dtos.ConfiguracionPartidaDTO;

/**
 * Representa la configuración inicial de una partida y valida sus reglas.
 */
public class ConfiguracionPartida {

    private final int rangoMinimo;
    private final int rangoMaximo;
    private final int numeroComodines;
    private final int numeroCartasAccion;
    private final int tiempoMaximoMostrarCartas;

    public ConfiguracionPartida(ConfiguracionPartidaDTO dto) {
        this.rangoMinimo = dto.getRangoMinimo();
        this.rangoMaximo = dto.getRangoMaximo();
        this.numeroComodines = dto.getNumeroComodines();
        this.numeroCartasAccion = dto.getNumeroCartasAccion();
        this.tiempoMaximoMostrarCartas = dto.getTiempoMaximoMostrarCartas();
    }

    public boolean esValida() {
        return obtenerMotivoInvalidez() == null;
    }

    public String obtenerMotivoInvalidez() {
        if (rangoMinimo < 0 || rangoMinimo > 9) {
            return "El rango mínimo debe estar entre 0 y 9.";
        }

        if (rangoMaximo < 0 || rangoMaximo > 9) {
            return "El rango máximo debe estar entre 0 y 9.";
        }

        if (rangoMinimo > rangoMaximo) {
            return "El rango mínimo no puede ser mayor que el rango máximo.";
        }

        if (numeroComodines < 1 || numeroComodines > 8) {
            return "El número de comodines debe estar entre 1 y 8.";
        }

        if (numeroCartasAccion < 1 || numeroCartasAccion > 8) {
            return "El número de cartas de acción debe estar entre 1 y 8.";
        }

        if (tiempoMaximoMostrarCartas < 1) {
            return "El tiempo máximo para mostrar cartas debe ser mayor que cero.";
        }

        return null;
    }

    public int getRangoMinimo() {
        return rangoMinimo;
    }

    public int getRangoMaximo() {
        return rangoMaximo;
    }

    public int getNumeroComodines() {
        return numeroComodines;
    }

    public int getNumeroCartasAccion() {
        return numeroCartasAccion;
    }

    public int getTiempoMaximoMostrarCartas() {
        return tiempoMaximoMostrarCartas;
    }
}