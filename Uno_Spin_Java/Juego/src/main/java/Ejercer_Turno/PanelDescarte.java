/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author lagar
 */
public class PanelDescarte extends JPanel{
    private Mazo mazo;
    private PanelCarta nuevaCarta;
    
    public PanelDescarte(Mazo mazo) {
        this.mazo = mazo;
        setPreferredSize(new Dimension(100,120));
        this.nuevaCarta = mazo.tomarUnaCarta();
        setBackground(nuevaCarta.getColorExterno());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(nuevaCarta.getLadoAdelante(), 0, 0, 100, 120, this);
    }

    public void setNuevaCarta(PanelCarta nuevaCarta) {
        this.nuevaCarta = nuevaCarta;
        setBackground(nuevaCarta.getColorExterno());
        repaint();
    }
}
