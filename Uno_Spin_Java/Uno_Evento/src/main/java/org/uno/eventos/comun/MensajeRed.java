/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uno.eventos.comun;

import java.io.Serializable;

/**
 * 
 * @author lagar
 */
public class MensajeRed implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String idMensaje;
    private final String emisor;  
    private final Object contenido;

    public MensajeRed(String idMensaje, String emisor, Object contenido) {
        this.idMensaje = idMensaje;
        this.emisor = emisor;
        this.contenido = contenido;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public String getEmisor() {
        return emisor;
    }

    public Object getContenido() {
        return contenido;
    }
}
