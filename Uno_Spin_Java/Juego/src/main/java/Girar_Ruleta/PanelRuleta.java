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
    private int velocidad = 100; // tiempo inicial en ms entre cambios de imagen
    private int aceleracion = -2; // cuánto se incrementa/reduce la velocidad por tick
    private boolean desacelerando = false;

    public PanelRuleta() {
        cargarImagenes();
        iniciarAnimacion();
    }

    // Cargar todas las imágenes de la ruleta
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

    // Pintar la ruleta actual
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ruletas[indiceActual] != null) {
            g.drawImage(ruletas[indiceActual], 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Iniciar animación
    private void iniciarAnimacion() {
        timer = new Timer(velocidad, e -> {
            indiceActual = (indiceActual + 1) % ruletas.length;
            repaint();

            // Si estamos desacelerando, aumentar tiempo entre cambios
            if (desacelerando) {
                velocidad += 5; // desacelerar
                if (velocidad >= 300) { // velocidad máxima, detener ruleta
                    timer.stop();
                    System.out.println("Ruleta detenida en: " + (indiceActual + 1));
                } else {
                    timer.setDelay(velocidad);
                }
            }
        });
        timer.start();
    }

    // Método para iniciar desaceleración
    public void detenerRuleta() {
        desacelerando = true;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200); // tamaño del panel
    }

    // Prueba rápida en un JFrame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ruleta Animada");
        PanelRuleta ruleta = new PanelRuleta();
        frame.add(ruleta);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Detener la ruleta después de 3 segundos para probar
        new Timer(3000, e -> ruleta.detenerRuleta()).start();
    }
}