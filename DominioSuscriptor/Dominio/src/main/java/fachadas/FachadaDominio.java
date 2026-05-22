/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoGritoDTO;
import dtos.ResultadoJugadaDTO;
import dtos.ResultadoUnirseDTO;

/**
 * 
 * @author lagar
 */
public interface FachadaDominio {
    ResultadoJugadaDTO robarCarta(String idJugador);
    ResultadoJugadaDTO validarYPlay(String idJugador, CartaDTO carta);
    ResultadoJugadaDTO pasarTurno(String idJugador);
    ResultadoGritoDTO gritarUno(JugadorDTO datosGrito);
    ResultadoUnirseDTO unirseAPartida(JugadorDTO jugadorDTO);
}
