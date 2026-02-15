/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author lagar
 */
public class Carta {
    private String simbolo;
    private Color colorExterno;
    private String colorInterno;
    private BufferedImage adelante;
    private BufferedImage atras;

    public Carta() {
    }
    public Carta(String simbolo, Color colorExterno, String colorInterno, String rutaAdelante, String rutaAtras) {
        this.simbolo = simbolo;
        this.colorExterno = colorExterno;
        this.colorInterno = colorInterno;
        if (this.colorExterno == null) {
            this.colorExterno = Color.GRAY;
        } else {
            this.colorExterno = colorExterno;
        }
        try {
            adelante = ImageIO.read(getClass().getResource(rutaAdelante));
            atras = ImageIO.read(getClass().getResource(rutaAtras));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public BufferedImage getAdelante() {
        return adelante;
    }

    public BufferedImage getAtras() {
        return atras;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public Color getColorExterno() {
        return colorExterno;
    }

    public String getColorInterno() {
        return colorInterno;
    }
    
    private void cargarRecursos(String rutaAdelante, String rutaAtras){
        try {
            adelante = ImageIO.read(getClass().getResource(rutaAdelante));
            atras = ImageIO.read(getClass().getResource(rutaAtras));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
