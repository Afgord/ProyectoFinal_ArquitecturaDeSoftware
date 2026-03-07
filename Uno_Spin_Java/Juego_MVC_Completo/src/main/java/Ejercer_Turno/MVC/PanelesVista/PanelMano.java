/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.Dominio.Carta;
import Ejercer_Turno.Dominio.Jugador;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.FrameTablero;
import Ejercer_Turno.MVC.ModeloJuego;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PanelMano extends JPanel {

    private final ControlJuego control;
    private final ModeloJuego modeloJuego;
    private PanelCarta cartaSeleccionadaVista;
    private Timer timerDobleClic;

    public PanelMano(ControlJuego control, ModeloJuego modeloJuego) {
        this.control = control;
        this.modeloJuego = modeloJuego;
        setLayout(null);
        setOpaque(false);
    }

    public void refrescarMano() {
        removeAll();
        Jugador jugadorActual = modeloJuego.getTablero().getJugadorActual();
        List<Carta> cartas = jugadorActual.getCartasModelo(); 
        
        int n = cartas.size();
        if (n == 0) {
            revalidate();
            repaint();
            return;
        }

        int anchoPanel = getWidth() > 0 ? getWidth() : 800;
        int altoPanel = getHeight() > 0 ? getHeight() : 150;
        int anchoCarta = 100;
        int altoCarta = 140;
        int y = (altoPanel - altoCarta) / 2;
        
        int espacio;
        int xInicial;

        if (n == 1) {
            espacio = 0;
            xInicial = (anchoPanel - anchoCarta) / 2;
        } else {
            espacio = (anchoPanel - anchoCarta) / (n - 1);
            if (espacio > anchoCarta + 10) {
                espacio = 70;
                int anchoTotal = (n - 1) * espacio + anchoCarta;
                xInicial = (anchoPanel - anchoTotal) / 2;
            } else {
                xInicial = 0;
            }
        }

        int x = xInicial;
        for (Carta c : cartas) {
            PanelCarta pCarta = new PanelCarta(c, control);
            pCarta.setBounds(x, y, anchoCarta, altoCarta);
            configurarEventosMano(pCarta);
            add(pCarta, 0);
            x += espacio;
        }

        revalidate();
        repaint();
    }

    private void configurarEventosMano(PanelCarta pCarta) {
        pCarta.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    if (timerDobleClic != null) timerDobleClic.stop();
                    procesarLanzamiento(pCarta);
                } else if (evt.getClickCount() == 1) {
                    timerDobleClic = new javax.swing.Timer(250, e -> {
                        seleccionarCarta(pCarta);
                    });
                    timerDobleClic.setRepeats(false);
                    timerDobleClic.start();
                }
            }
        });
    }

    private void seleccionarCarta(PanelCarta pCarta) {
        if (cartaSeleccionadaVista != null) {
            cartaSeleccionadaVista.setSeleccionada(false);
        }
        cartaSeleccionadaVista = pCarta;
        cartaSeleccionadaVista.setSeleccionada(true);
        
        FrameTablero frame = (FrameTablero) SwingUtilities.getWindowAncestor(PanelMano.this);
        if (frame != null) {
            frame.getPanelTablero().getPanelZoom().mostrarCarta(pCarta.getModelo());
        }
        repaint();
    }

    private void procesarLanzamiento(PanelCarta pCarta) {
        System.out.println("Intentando lanzar: " + pCarta.getModelo().getSimbolo() + " Color: " + pCarta.getModelo().getColorInterno());
        Carta modelo = pCarta.getModelo();      
        if (modelo.esComodin()) {
            SwingUtilities.invokeLater(() -> {
                PanelSelectorColor.mostrar(PanelMano.this, control, modeloJuego, modelo);
            });
        } else {
            control.solicitarTirarCarta(modelo);
        }
        cartaSeleccionadaVista = null;
    }
    
    
}