/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import Entidades.Colores;
import Entidades.Valor;
import java.io.Serializable;

/**
 *
 * @author lagar
 */
public class CartaDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private final Valor valor;
    private final Colores color;

    public CartaDTO(Valor valor, Colores color) {
        this.valor = valor;
        this.color = color;
    }
    
    public Valor getValor() { return valor; }
    public Colores getColor() { return color; }
}
