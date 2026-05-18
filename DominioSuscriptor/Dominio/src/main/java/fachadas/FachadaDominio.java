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
    public ResultadoJugadaDTO robarCarta();
    public ResultadoJugadaDTO validarYPlay(CartaDTO carta);
    public ResultadoJugadaDTO pasarTurno();
    public ResultadoGritoDTO gritarUno(JugadorDTO datosGrito);
    public ResultadoUnirseDTO unirseAPartida(JugadorDTO jugadorDTO);
}
