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
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelManoSecundaria extends JPanel {

    private List<PanelCarta> cartas;
    private PanelDescarte panelDescarte;
    private PanelJugador panelJugador;

    private URL urlRotacion;
    private BufferedImage atrasRotado;

    private String ubicacion;

    public PanelManoSecundaria(List<PanelCarta> cartas,
                               PanelDescarte panelDescarte,
                               PanelJugador panelJugador,
                               String ubicacion) {

        this.cartas = cartas;
        this.panelDescarte = panelDescarte;
        this.panelJugador = panelJugador;
        this.ubicacion = ubicacion;

        setBackground(Color.RED);
        setLayout(null);

        switch (ubicacion) {
            case "izquierda" -> {
                setPreferredSize(new Dimension(120, 500));
                urlRotacion = getClass().getResource("/cartas/atrasIzquierda.png");
            }
            case "arriba" -> {
                setPreferredSize(new Dimension(500, 120));
                urlRotacion = getClass().getResource("/cartas/atrasArriba.png");
            }
            case "derecha" -> {
                setPreferredSize(new Dimension(120, 500));
                urlRotacion = getClass().getResource("/cartas/atrasDerecha.png");
            }
        }

        try {
            atrasRotado = ImageIO.read(urlRotacion);
        } catch (IOException e) {
            System.out.println("No se cargaron las cartas rotadas");
        }
        actualizarContador();
    }
    
    public void agregarCarta(PanelCarta carta) {
        cartas.add(carta);
        actualizarContador();
        repaint();
    }
    
    public void eliminarCarta(PanelCarta carta) {
        cartas.remove(carta);
        actualizarContador();
        repaint();
    }
    
    private void actualizarContador() {
        if (panelJugador != null) {
            panelJugador.actualizarCantidad(cartas.size());
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (atrasRotado == null) return;

        int total = cartas.size();
        if (total == 0) return;

        int separacion = 30;

        switch (ubicacion) {

            case "arriba" -> {
                int anchoCarta = 100;
                int altoCarta = 120;

                int anchoTotal = total * separacion;
                int xInicial = (getWidth() - anchoTotal) / 2;
                int y = (getHeight() - altoCarta) / 2;

                for (int i = 0; i < total; i++) {
                    g.drawImage(
                        atrasRotado,
                        xInicial + (i * separacion),
                        y,
                        anchoCarta,
                        altoCarta,
                        this
                    );
                }
            }

            case "izquierda", "derecha" -> {
                int anchoCarta = 120;
                int altoCarta = 100;

                int altoTotal = total * separacion;
                int yInicial = (getHeight() - altoTotal) / 2;
                int x = (getWidth() - anchoCarta) / 2;

                for (int i = 0; i < total; i++) {
                    g.drawImage(
                        atrasRotado,
                        x,
                        yInicial + (i * separacion),
                        anchoCarta,
                        altoCarta,
                        this
                    );
                }
            }
        }
    }
}