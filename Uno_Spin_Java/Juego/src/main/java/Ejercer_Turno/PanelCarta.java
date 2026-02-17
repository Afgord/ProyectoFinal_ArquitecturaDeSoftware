/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
    private String simbolo;
    private Color colorExterno;
    private String colorInterno;
    private BufferedImage ladoAdelante;
    private BufferedImage ladoAtras;
    private boolean lado;
    private final URL urlAtras = getClass().getResource("/cartas/atras.png");

    public PanelCarta(String simbolo, Color colorExterno, String colorInterno, String ladoAdelante, boolean lado) {
        cargarCarta(ladoAdelante);
        setBackground(colorExterno);
        setPreferredSize(new Dimension(100,120));
        this.simbolo = simbolo;
        this.colorExterno = colorExterno;
        this.colorInterno = colorInterno;
        this.lado = lado;
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                if (getParent() instanceof PanelMano) {
                    PanelMano mano = (PanelMano) getParent();
                    mano.eliminarCarta(PanelCarta.this);
                }

            }
        });  
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(colorExterno);
        if(lado == false){
            g2d.drawImage(ladoAtras, 0, 0, this.getWidth(), this.getHeight(), this);
        } else {
            g2d.drawImage(ladoAdelante, 0, 0, this.getWidth(), this.getHeight(), this);
        }
        
           
    }
    
    private void cargarCarta(String adelanteImagen){
        try {
            ladoAtras = ImageIO.read(urlAtras);
            URL urlAdelante = getClass().getResource(adelanteImagen);
            ladoAdelante = ImageIO.read(urlAdelante);
        } catch(IOException e){
            System.out.println(e);
        }
    }
}
