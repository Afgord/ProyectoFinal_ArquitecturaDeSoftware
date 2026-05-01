/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

import java.io.Serializable;

/**
 *
 * @author lagar
 */
public class Evento implements Serializable {
    private String idEvento;
    private static final long serialVersionUID = 1L;

    public Evento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdEvento() {
        return idEvento;
    }
    
}
