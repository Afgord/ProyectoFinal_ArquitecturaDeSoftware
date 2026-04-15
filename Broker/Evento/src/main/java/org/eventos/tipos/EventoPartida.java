/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.tipos;
import org.eventos.comun.Evento;
/**
 * 
 * @author lagar
 */
public class EventoPartida extends Evento {
    private final String accion; 
    private final String mensaje;
    private Object configuracion;

    public EventoPartida(String accion, String mensaje) {
        this.accion = accion;
        this.mensaje = mensaje;
    }

    public String getAccion() { return accion; }
    public String getMensaje() { return mensaje; }
    public Object getConfiguracion() { return configuracion; }
    public void setConfiguracion(Object configuracion) { this.configuracion = configuracion; }
}