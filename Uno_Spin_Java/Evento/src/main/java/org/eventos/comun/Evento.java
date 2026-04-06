/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.comun;

import java.io.Serializable;
import java.util.UUID;
/**
 * 
 * @author lagar
 */
public abstract class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String idEvento;

    public Evento() {
        this.idEvento = UUID.randomUUID().toString();
    }

    public String getIdEvento() { 
        return idEvento; 
    }
}