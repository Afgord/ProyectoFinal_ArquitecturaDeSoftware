/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

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
public class PanelMazo extends JPanel {

    private BufferedImage atras;
    private URL urlAtras;

    private Mazo mazo;
    private PanelMano panelMano;

    public PanelMazo(Mazo mazo, PanelMano panelMano) {
        this.mazo = mazo;
        this.panelMano = panelMano;
        setPreferredSize(new Dimension(100,120));
        setBackground(Color.BLACK);
        try{
            urlAtras = getClass().getResource("/cartas/atras.png");
            atras = ImageIO.read(urlAtras);
        } catch (IOException e){
            System.out.println("error al cargar la imagen del Mazo" + e);
        }
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                robarCarta();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (atras != null && mazo.getBaraja().isEmpty() == false){
            g.drawImage(atras, 0, 0, this.getWidth(),this.getHeight() , this);
        } else if(atras != null){
            System.out.println("ya no hay mas cartas en el mazo");
            setBackground(Color.RED);
        } else {
            
            System.out.println("no se cargo la imagen desde el paintcomponents");
        }
        
    }
    
    private void robarCarta() {
        try {
            PanelCarta nuevaCarta = mazo.tomarUnaCarta();
            panelMano.agregarCarta(nuevaCarta);
        } catch (Exception e) {
            System.out.println("No hay más cartas en el mazo");
        }
    }
}
