/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.Dominio.Carta;
import Ejercer_Turno.MVC.ControlJuego;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * 
 * @author lagar
 */
public class PanelCarta extends JPanel {

    private final Carta modelo;
    private BufferedImage imgAdelante;
    private BufferedImage imgAtras;
    private boolean seleccionada = false;

    public PanelCarta(Carta modelo, ControlJuego control) {
        this.modelo = modelo;
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(100, 140));
        cargarImagenes();
    }

    private void cargarImagenes() {
        try {
            URL urlAtras = getClass().getResource("/cartas/atras.png");
            if (urlAtras != null) {
                imgAtras = ImageIO.read(urlAtras);
            }
            String ruta = modelo.getRutaImagen();
            if (ruta != null && !ruta.isEmpty()) {
                URL urlAdelante = getClass().getResource(ruta);
                if (urlAdelante != null) {
                    imgAdelante = ImageIO.read(urlAdelante);
                } else {
                    System.err.println("No se encontró el recurso en: " + ruta);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer las imágenes de la carta: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (modelo.getColorExterno() != null) {
            g2d.setColor(modelo.getColorExterno());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        }
        if (modelo.isLado()) {
            if (imgAdelante != null) {
                g2d.drawImage(imgAdelante, 5, 5, getWidth() - 10, getHeight() - 10, this);
            }
        } else {
            if (imgAtras != null) {
                g2d.drawImage(imgAtras, 0, 0, getWidth(), getHeight(), this);
            }
        }
        if (seleccionada) {
            g2d.setColor(new Color(255, 255, 255, 180));
            g2d.setStroke(new BasicStroke(4));
            g2d.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 15, 15);
        }
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
        repaint();
    }

    public Carta getModelo() {
        return modelo;
    }
}