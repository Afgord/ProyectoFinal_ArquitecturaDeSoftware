/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelMazo extends JPanel implements IMazoObserver {
    private final Mazo mazoModelo;
    private final Jugador jugadorLocal; // El que roba
    private final PanelTablero tablero;
    private BufferedImage imgAtras;

    public PanelMazo(Mazo mazo, Jugador jugador, PanelTablero tablero) {
        this.mazoModelo = mazo;
        this.jugadorLocal = jugador;
        this.tablero = tablero;
        
        this.mazoModelo.addObserver(this); // Escuchar cuando se roban cartas
        
        setPreferredSize(new Dimension(100, 120));
        setOpaque(false);
        cargarImagen();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                robar();
            }
        });
    }

    private void cargarImagen() {
        try {
            imgAtras = ImageIO.read(getClass().getResource("/cartas/atras.png"));
        } catch (Exception e) {
            System.err.println("No se pudo cargar imagen del mazo");
        }
    }

    private void robar() {
        Carta c = mazoModelo.tomarUnaCarta();
        if (c != null) {
            tablero.reproducirJalar();
            jugadorLocal.agregarCarta(c);
        } else {
            JOptionPane.showMessageDialog(this, "¡No hay más cartas!");
        }
    }

    @Override
    public void mazoActualizado() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imgAtras != null && !mazoModelo.estaVacio()) {
            if (mazoModelo.getCantidadCartas() > 1) {
                g.drawImage(imgAtras, 2, 2, getWidth()-4, getHeight()-4, this);
            }
            g.drawImage(imgAtras, 0, 0, getWidth()-4, getHeight()-4, this);
        }
    }
}
