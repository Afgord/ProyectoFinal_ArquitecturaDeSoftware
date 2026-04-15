/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 * 
 * @author lagar
 */
public abstract class Carta {
    private final Valor valor;
    private String colorInterno;
    private final String rutaImagen;
    private boolean lado;

    public Carta(Valor valor, String colorInterno, String rutaImagen) {
        this.valor = valor;
        this.colorInterno = colorInterno;
        this.rutaImagen = rutaImagen;
        this.lado = true;
    }
    
    public Valor getValor() { return valor; }
    public String getColorInterno() { return colorInterno; }
    public void setColorNombre(String nuevoNombre) { this.colorInterno = nuevoNombre; }
    public String getRutaImagen() { return rutaImagen; }
    public boolean isLado() { return lado; }
    public void setLado(boolean lado) { this.lado = lado; }
}