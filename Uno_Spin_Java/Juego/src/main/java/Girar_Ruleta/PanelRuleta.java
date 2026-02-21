/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Girar_Ruleta;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class PanelRuleta extends JPanel {
    private BufferedImage[] ruletas;
    private int indiceActual = 0;
    private Timer timer;
    private int velocidad = 100;
    private int aceleracion = -2;
    private boolean desacelerando = false;

    public PanelRuleta() {
        cargarImagenes();
        iniciarAnimacion();
    }

    private void cargarImagenes() {
        String[] nombres = {
            "ruleta_1.png", "ruleta_2.png", "ruleta_3.png", "ruleta_4.png",
            "ruleta_5.png", "ruleta_6.png", "ruleta_7.png", "ruleta_8.png",
            "ruleta_normal.png"
        };
        ruletas = new BufferedImage[nombres.length];

        for (int i = 0; i < nombres.length; i++) {
            try {
                URL url = getClass().getResource("/ruleta/" + nombres[i]);
                if (url != null) {
                    ruletas[i] = ImageIO.read(url);
                } else {
                    System.err.println("No se encontró la imagen: " + nombres[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ruletas[indiceActual] != null) {
            g.drawImage(ruletas[indiceActual], 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void iniciarAnimacion() {
        timer = new Timer(velocidad, e -> {
            indiceActual = (indiceActual + 1) % ruletas.length;
            repaint();
            if (desacelerando) {
                velocidad += 5; 
                if (velocidad >= 300) {
                    timer.stop();
                    System.out.println("Ruleta detenida en: " + (indiceActual + 1));
                } else {
                    timer.setDelay(velocidad);
                }
            }
        });
        timer.start();
    }

    public void detenerRuleta() {
        desacelerando = true;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200); 
    }
}