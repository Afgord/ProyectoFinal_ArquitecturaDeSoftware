/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;

/**
 * DTO con los datos necesarios para configurar una partida.
 */
public class ConfiguracionPartidaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int rangoMinimo;
    private final int rangoMaximo;
    private final int numeroComodines;
    private final int numeroCartasAccion;
    private final int tiempoMaximoMostrarCartas;

    public ConfiguracionPartidaDTO(
            int rangoMinimo,
            int rangoMaximo,
            int numeroComodines,
            int numeroCartasAccion,
            int tiempoMaximoMostrarCartas
    ) {
        this.rangoMinimo = rangoMinimo;
        this.rangoMaximo = rangoMaximo;
        this.numeroComodines = numeroComodines;
        this.numeroCartasAccion = numeroCartasAccion;
        this.tiempoMaximoMostrarCartas = tiempoMaximoMostrarCartas;
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
