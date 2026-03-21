/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.Dominio;

import java.awt.Color;
/**
 *
 * @author Luis Rafael
 */
public class CartaDTO {
    private final String id;
    private final Color color;
    private final String simbolo;
    private final boolean esEspecial;

    public CartaDTO(String id, Color color, String simbolo, boolean esEspecial) {
        this.id = id;
        this.color = color;
        this.simbolo = simbolo;
        this.esEspecial = esEspecial;
    }

    public String getId() { return id; }
    public Color getColor() { return color; }
    public String getSimbolo() { return simbolo; }
    public boolean isEsEspecial() { return esEspecial; }
}
