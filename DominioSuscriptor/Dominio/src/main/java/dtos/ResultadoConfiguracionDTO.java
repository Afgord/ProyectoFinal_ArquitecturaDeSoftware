/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;

/**
 * Resultado de la solicitud de configuración de una partida.
 */
public class ResultadoConfiguracionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final boolean exito;
    private final String motivo;

    public ResultadoConfiguracionDTO(boolean exito, String motivo) {
        this.exito = exito;
        this.motivo = motivo;
    }

    public boolean isExito() {
        return exito;
    }

    public String getMotivo() {
        return motivo;
    }
}