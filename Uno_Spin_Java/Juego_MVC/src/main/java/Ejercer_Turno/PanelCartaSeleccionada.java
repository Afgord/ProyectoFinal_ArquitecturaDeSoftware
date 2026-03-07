/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PanelCartaSeleccionada extends JPanel {

    private ICartaReadOnly cartaActual;
    private BufferedImage imagenRender;

    public PanelCartaSeleccionada() {
        setLayout(null);
        setBackground(new Color(50, 50, 50));
        setBorder(new LineBorder(Color.WHITE, 2, true));
        setOpaque(true);
    }
    public void mostrarCarta(ICartaReadOnly carta) {
        this.cartaActual = carta;      
        if (carta != null) {
            setBackground(carta.getColorExterno());
        } else {
            setBackground(new Color(50, 50, 50));
        }
        repaint();
    }

    public void limpiar() {
        this.cartaActual = null;
        setBackground(new Color(50, 50, 50));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);        
        if (cartaActual != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
            String texto = cartaActual.getSimbolo();
            int anchoTexto = g2d.getFontMetrics().stringWidth(texto);
            g2d.drawString(texto, (getWidth() / 2) - (anchoTexto / 2), (getHeight() / 2) + 15);
        }
    }
}
