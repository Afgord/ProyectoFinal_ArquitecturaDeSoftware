/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.Dominio.Jugador;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * 
 * @author lagar
 */
public class PanelManoSecundaria extends JPanel {

    private final Jugador oponente; 
    private final String ubicacion;
    private BufferedImage atrasRotado;
    private final int ANCHO_CARTA_VERT = 80;
    private final int ALTO_CARTA_VERT = 110;
    private final int ANCHO_CARTA_HORIZ = 110;
    private final int ALTO_CARTA_HORIZ = 80;

    public PanelManoSecundaria(Jugador oponente, String ubicacion) {
        this.oponente = oponente;
        this.ubicacion = ubicacion;
        cargarImagenRotada();
        setOpaque(false);
        configurarDimensiones();
    }

    private void configurarDimensiones() {
        switch (ubicacion) {
            case "arriba" -> setPreferredSize(new Dimension(400, 120));
            case "izquierda", "derecha" -> setPreferredSize(new Dimension(120, 400));
        }
    }

    private void cargarImagenRotada() {
        String ruta = switch (ubicacion) {
            case "izquierda" -> "/cartas/atrasIzquierda.png";
            case "arriba"    -> "/cartas/atrasArriba.png";
            case "derecha"   -> "/cartas/atrasDerecha.png";
            default          -> "/cartas/atras.png";
        };
        try {
            URL url = getClass().getResource(ruta);
            if (url != null) {
                atrasRotado = ImageIO.read(url);
            }
        } catch (IOException e) {
            System.err.println("Error cargando carta rotada para: " + ubicacion);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (atrasRotado == null) return;

        int totalCartas = oponente.getNumCartas();
        if (totalCartas == 0) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int separacion = 15; 

        if (ubicacion.equals("arriba")) {
            int anchoTotal = (totalCartas - 1) * separacion + ANCHO_CARTA_VERT;
            int x = (getWidth() - anchoTotal) / 2;
            int y = (getHeight() - ALTO_CARTA_VERT) / 2;

            for (int i = 0; i < totalCartas; i++) {
                g2d.drawImage(atrasRotado, x + (i * separacion), y, ANCHO_CARTA_VERT, ALTO_CARTA_VERT, this);
            }
        } else {
            int altoTotal = (totalCartas - 1) * separacion + ALTO_CARTA_HORIZ;
            int y = (getHeight() - altoTotal) / 2;
            int x = (getWidth() - ANCHO_CARTA_HORIZ) / 2;

            for (int i = 0; i < totalCartas; i++) {
                g2d.drawImage(atrasRotado, x, y + (i * separacion), ANCHO_CARTA_HORIZ, ALTO_CARTA_HORIZ, this);
            }
        }
    }
}
