/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.*;
import javax.swing.JPanel;

public class PanelDescarte extends JPanel implements IDescarteObserver {
    private final Descarte modelo;

    public PanelDescarte(Descarte modelo) {
        this.modelo = modelo;
        this.modelo.addObserver(this);
        setPreferredSize(new Dimension(100, 120));
        setOpaque(false);
    }

    @Override
    public void descarteActualizado() {
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Carta cartaCima = modelo.getCartaCima();
        if (cartaCima != null) {
            g2d.setColor(cartaCima.getColorExterno()); 
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            PanelCarta render = new PanelCarta(cartaCima);
            Image img = render.getImagenAdelante(); 
            if (img != null) {
                g2d.drawImage(img, 5, 5, getWidth() - 10, getHeight() - 10, this);
            }
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 15, 15);
        }
    }
    
    public boolean validarJugada(Carta c) { return modelo.validarJugada(c); }
    public void recibirCarta(Carta c) { modelo.recibirCarta(c); }
}