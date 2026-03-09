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

public class PanelMano extends JPanel {
    private final ControlJuego control;
    private final ModeloJuego modeloJuego;
    private PanelCarta cartaSeleccionadaVista;

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
        if (n == 0) { revalidate(); repaint(); return; }
        int anchoPanel = getWidth() > 0 ? getWidth() : 800;
        int altoPanel = getHeight() > 0 ? getHeight() : 150;
        int anchoCarta = 100; int altoCarta = 140;
        int y = (altoPanel - altoCarta) / 2;
        int espacio = (n == 1) ? 0 : Math.min(70, (anchoPanel - anchoCarta) / (n - 1));
        int x = (n == 1) ? (anchoPanel - anchoCarta) / 2 : (anchoPanel - ((n-1)*espacio + anchoCarta)) / 2;

        for (Carta c : cartas) {
            PanelCarta pCarta = new PanelCarta(c, control);
            pCarta.setBounds(x, y, anchoCarta, altoCarta);
            configurarEventoCarta(pCarta);
            add(pCarta, 0);
            x += espacio;
        }
        revalidate(); repaint();
    }

    private void configurarEventoCarta(PanelCarta pCarta) {
        pCarta.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (cartaSeleccionadaVista != null && cartaSeleccionadaVista.getModelo() == pCarta.getModelo()) {
                    intentarLanzar(pCarta.getModelo());
                } else {
                    if (cartaSeleccionadaVista != null) cartaSeleccionadaVista.setSeleccionada(false);
                    cartaSeleccionadaVista = pCarta;
                    cartaSeleccionadaVista.setSeleccionada(true);
                    actualizarZoom(pCarta.getModelo());
                }
            }
        });
    }

    private void actualizarZoom(Carta modelo) {
        FrameTablero frame = (FrameTablero) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.getPanelTablero().getPanelZoom().mostrarCarta(modelo);
        }
    }

    private void intentarLanzar(Carta modelo) {
        if (modelo.esComodin()) {
            PanelSelectorColor.mostrar(this, control, modeloJuego, modelo);
        } else {
            control.solicitarTirarCarta(modelo);
        }
        this.cartaSeleccionadaVista = null; 
        repaint();
    }
}