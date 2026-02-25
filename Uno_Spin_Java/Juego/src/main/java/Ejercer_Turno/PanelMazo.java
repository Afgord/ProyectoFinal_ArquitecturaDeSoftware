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

public class PanelMazo extends JPanel {
    private BufferedImage atras;
    private URL urlAtras;
    private Mazo mazo;
    private PanelMano panelMano;
    private PanelTablero panelTablero;

    public PanelMazo(Mazo mazo, PanelMano panelMano, PanelTablero panelTablero) {
        this.mazo = mazo;
        this.panelMano = panelMano;
        setPreferredSize(new Dimension(100,120));
        setBackground(Color.BLACK);

        try {
            urlAtras = getClass().getResource("/cartas/atras.png");
            atras = ImageIO.read(urlAtras);
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen del Mazo: " + e);
        }

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                panelTablero.reproducirJalar();
                robarCarta();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!mazo.getBaraja().isEmpty() && atras != null) {
            g.drawImage(atras, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("Mazo vacío", 10, getHeight()/2);
        }
    }

    private void robarCarta() {
        try {
            PanelCarta nuevaCarta = mazo.tomarUnaCarta();
            panelMano.agregarCarta(nuevaCarta);
        } catch (Exception e) {
            System.out.println("No hay más cartas en el mazo");
        }
        repaint();
    }
}
