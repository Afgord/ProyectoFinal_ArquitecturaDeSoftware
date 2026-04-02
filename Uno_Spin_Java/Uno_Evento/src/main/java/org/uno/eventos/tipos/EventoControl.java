/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uno.eventos.tipos;

import org.uno.eventos.comun.Evento;
/**
 * 
 * @author lagar
 */
public class EventoControl extends Evento {
    private final String accion; 
    private final String mensaje;

    public EventoControl(String accion, String mensaje) {
        this.accion = accion;
        this.mensaje = mensaje;
    }

    public String getAccion() { return accion; }
    public String getMensaje() { return mensaje; }
}