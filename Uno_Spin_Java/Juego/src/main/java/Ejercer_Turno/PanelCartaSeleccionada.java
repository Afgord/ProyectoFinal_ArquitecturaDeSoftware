/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class PanelCartaSeleccionada extends JPanel {

    private BufferedImage imagen;
    private Color colorExterno;

    public PanelCartaSeleccionada() {
        setPreferredSize(new Dimension(100, 120));
        setBackground(Color.WHITE);
    }

    public void setCartaSeleccionada(String simbolo, BufferedImage imagen, Color colorExterno) {
        this.imagen = imagen;
        this.colorExterno = colorExterno;
        repaint();
    }

    public void limpiarSeleccion() {
        this.imagen = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (imagen == null) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            String texto = "Ninguna";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 5;
            g2d.drawString(texto, x, y);
            return;
        }

        // Fondo externo
        g2d.setColor(colorExterno);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Imagen
        g2d.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

        // Borde
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 20, 20);
    }
}
