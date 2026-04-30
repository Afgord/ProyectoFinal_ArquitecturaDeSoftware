/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

import java.util.List;
import org.evento.dto.CartaDTO;
import org.evento.dto.JugadorDTO;

/**
 *
 * @author lagar
 */
public class EventoResultadoRuleta extends Evento{
    private ResultadoRuleta resultadoRuleta;
    private List<JugadorDTO> jugadores;
    private CartaDTO cartaEnCima;
    private String idJugadorTurnoActual;

    public EventoResultadoRuleta(ResultadoRuleta resultadoRuleta, List<JugadorDTO> jugadores, CartaDTO cartaEnCima, String idJugadorTurnoActual, String idEvento) {
        super(idEvento);
        this.resultadoRuleta = resultadoRuleta;
        this.jugadores = jugadores;
        this.cartaEnCima = cartaEnCima;
        this.idJugadorTurnoActual = idJugadorTurnoActual;
    }

    public ResultadoRuleta getResultadoRuleta() {
        return resultadoRuleta;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public CartaDTO getCartaEnCima() {
        return cartaEnCima;
    }

    public String getIdJugadorTurnoActual() {
        return idJugadorTurnoActual;
    }
    
    
}
