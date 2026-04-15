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
    private final String simbolo;
    private String colorInterno;
    private final String rutaImagen;
    private boolean lado;

    public Carta(String simbolo, String colorInterno, String rutaImagen) {
        this.simbolo = simbolo;
        this.colorInterno = colorInterno;
        this.rutaImagen = rutaImagen;
        this.lado = true;
    }
    
    public String getSimbolo() { return simbolo; }
    public String getColorInterno() { return colorInterno; }
    public void setColorNombre(String nuevoNombre) { this.colorInterno = nuevoNombre; }
    public String getRutaImagen() { return rutaImagen; }
    public boolean isLado() { return lado; }
    public void setLado(boolean lado) { this.lado = lado; }
}