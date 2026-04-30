/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;
import dtos.JugadorDTO;

/**
 * 
 * @author lagar
 */
public interface FachadaDominio {
    public Object robarCarta();
    public Object validarYPlay(CartaDTO carta);
    public void pasarTurno();
    public Object gritarUno(JugadorDTO datosGrito);
}
