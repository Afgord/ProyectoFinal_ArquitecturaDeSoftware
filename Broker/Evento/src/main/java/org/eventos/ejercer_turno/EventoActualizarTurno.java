/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.ejercer_turno;

import java.util.List;
import dtos.CartaDTO;
import dtos.JugadorDTO;

/**
 *
 * @author lagar
 */
public class EventoActualizarTurno extends Evento{
    private List<JugadorDTO> jugadores;
    private CartaDTO cartaEnCima;
    private String idJugadorTurnoActual;

    public EventoActualizarTurno(List<JugadorDTO> jugadores, CartaDTO cartaEnCima, String idJugadorTurnoActual, String idEvento) {
        super(idEvento);
        this.jugadores = jugadores;
        this.cartaEnCima = cartaEnCima;
        this.idJugadorTurnoActual = idJugadorTurnoActual;
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
