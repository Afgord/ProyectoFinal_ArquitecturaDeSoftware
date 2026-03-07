/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelAvatar extends JPanel {
    private BufferedImage imagenPerfil;

    public PanelAvatar(String rutaImagen) {
        setOpaque(false);
        cargarImagen(rutaImagen);
    }

    private void cargarImagen(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            if (url != null) {
                imagenPerfil = ImageIO.read(url);
            } else {
                System.err.println("No se encontró el avatar en: " + ruta);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar el avatar: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenPerfil != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, getWidth(), getHeight()));
            g2d.drawImage(imagenPerfil, 0, 0, getWidth(), getHeight(), this);
            g2d.setClip(null);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
        }
    }
}
