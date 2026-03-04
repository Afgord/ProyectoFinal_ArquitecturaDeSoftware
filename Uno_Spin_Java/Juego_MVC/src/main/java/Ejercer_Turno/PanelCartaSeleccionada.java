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

/**
 * VISTA: PanelCartaSeleccionada
 * Actúa como un visor de previsualización para la carta que el usuario 
 * tiene marcada actualmente en su mano.
 */
public class PanelCartaSeleccionada extends JPanel {

    private ICartaReadOnly cartaActual;
    private BufferedImage imagenRender;

    public PanelCartaSeleccionada() {
        // Configuración visual del contenedor de vista previa
        setLayout(null);
        setBackground(new Color(50, 50, 50)); // Fondo oscuro neutro
        setBorder(new LineBorder(Color.WHITE, 2, true));
        setOpaque(true);
    }

    /**
     * Actualiza la carta que se está mostrando.
     * @param carta El modelo de la carta seleccionada (puede ser null para limpiar)
     */
    public void mostrarCarta(ICartaReadOnly carta) {
        this.cartaActual = carta;
        
        if (carta != null) {
            // Intentamos obtener la imagen del objeto de presentación de la carta
            // o cargarla directamente si prefieres. 
            // Para simplicidad, refrescamos el fondo con el color de la carta.
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
            
            // Si la carta tiene una ruta de imagen válida, aquí podrías dibujar
            // una versión más grande de la carta.
            
            // Dibujamos el símbolo en el centro como respaldo visual
            g2d.setColor(Color.WHITE);
            g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
            
            String texto = cartaActual.getSimbolo();
            int anchoTexto = g2d.getFontMetrics().stringWidth(texto);
            g2d.drawString(texto, (getWidth() / 2) - (anchoTexto / 2), (getHeight() / 2) + 15);
        }
    }
}
