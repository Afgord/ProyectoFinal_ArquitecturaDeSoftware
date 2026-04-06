/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.dto;

import java.io.Serializable;

/**
 * @author lagar
 */
public class ConfiguracionDTO implements Serializable {
    private final int rangoInicio;
    private final int rangoFinal;
    private final boolean tieneMasDos;
    private final boolean tieneProhibido;
    private final boolean tieneReversa;
    private final boolean tieneMasCuatro;
    private final boolean tieneCambioColor;

    public ConfiguracionDTO(int rI, int rF, boolean m2, boolean pro, boolean rev, boolean m4, boolean cc) {
        this.rangoInicio = rI;
        this.rangoFinal = rF;
        this.tieneMasDos = m2;
        this.tieneProhibido = pro;
        this.tieneReversa = rev;
        this.tieneMasCuatro = m4;
        this.tieneCambioColor = cc;
    }

    public int getRangoInicio() { return rangoInicio; }
    public int getRangoFinal() { return rangoFinal; }
    public boolean isTieneMasDos() { return tieneMasDos; }
    public boolean isTieneProhibido() { return tieneProhibido; }
    public boolean isTieneReversa() { return tieneReversa; }
    public boolean isTieneMasCuatro() { return tieneMasCuatro; }
    public boolean isTieneCambioColor() { return tieneCambioColor; }
}