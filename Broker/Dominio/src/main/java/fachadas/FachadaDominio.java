/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;

/**
 * 
 * @author lagar
 */
public interface FachadaDominio {
    public void robarCarta();
    public boolean validarYPlay(CartaDTO carta);
    public void pasarTurno();
}
