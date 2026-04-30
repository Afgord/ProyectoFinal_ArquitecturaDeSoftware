/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eventos.mvc_dominio;

import org.evento.dto.CartaDTO;

/**
 *
 * @author lagar
 */
public class EventoTirarCarta extends EventoAccion{
    private CartaDTO carta;

    public EventoTirarCarta(CartaDTO carta, String idJugador, String idEvento) {
        super(idJugador, idEvento);
        this.carta = carta;
    }

    public CartaDTO getCarta() {
        return carta;
    }
    
    
}
