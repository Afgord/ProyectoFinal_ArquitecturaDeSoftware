package pruebas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author lagar
 */
public class PanelCarta extends JPanel{
    private Carta carta;
    private boolean mostrarFrente;
    
    public PanelCarta(Carta carta) {
        this.carta = carta;
        setBackground(carta.getColorExterno());
        voltear();
        
    }
    
    public void voltear(){
        mostrarFrente = !mostrarFrente;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (mostrarFrente) {
            g.drawImage(carta.getAdelante(), 100, 100, this);
        } else {
            g.drawImage(carta.getAtras(), 100, 100, this);
        }
    }
}
