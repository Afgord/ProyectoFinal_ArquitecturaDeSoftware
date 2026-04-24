/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;

/**
 *
 * @author lagar
 */
public class InfoRuletaDTO {
    private final String efecto; // "GUERRA", "PUNTUACION_BAJA", "NORMAL"
    private final boolean requiereEleccion;
    private final String mensaje;
    private final List<String> idJugadoresInvolucrados;

    public InfoRuletaDTO(String efecto, boolean requiereEleccion, String mensaje, List<String> involucrados) {
        this.efecto = efecto;
        this.requiereEleccion = requiereEleccion;
        this.mensaje = mensaje;
        this.idJugadoresInvolucrados = involucrados;
    }

    public String getEfecto() {
        return efecto;
    }

    public boolean isRequiereEleccion() {
        return requiereEleccion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<String> getIdJugadoresInvolucrados() {
        return idJugadoresInvolucrados;
    }
    
    
}
