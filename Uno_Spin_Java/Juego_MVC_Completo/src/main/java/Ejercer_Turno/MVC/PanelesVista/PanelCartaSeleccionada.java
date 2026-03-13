/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.Dominio.Carta;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
/**
 * 
 * @author lagar
 */
public class PanelCartaSeleccionada extends JPanel {

    private Carta cartaActual;

    public PanelCartaSeleccionada() {
        setLayout(null);
        setBackground(new Color(50, 50, 50));
        setBorder(new LineBorder(Color.WHITE, 2, true));
        setOpaque(true);
    }

    public void mostrarCarta(Carta carta) {
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
            g2d.setFont(new Font("Arial", Font.BOLD, 35));
            String texto = cartaActual.getSimbolo();
            FontMetrics metrics = g2d.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(texto)) / 2;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
            
            g2d.drawString(texto, x, y);
        }
    }
}