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
public class EventoSpin extends Evento {
    private static final long serialVersionUID = 1L;
    private final String resultado; 

    public EventoSpin(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() { return resultado; }
}