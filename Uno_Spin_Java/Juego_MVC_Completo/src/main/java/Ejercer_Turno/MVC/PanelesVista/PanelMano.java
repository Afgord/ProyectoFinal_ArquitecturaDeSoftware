/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC.PanelesVista;

import org.uno.dto.CartaDTO;
import org.uno.dto.JugadorDTO;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.FrameTablero;
import Ejercer_Turno.Interfaces.IModeloDatos;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 * 
 * @author lagar
 */
public class PanelMano extends JPanel {
    private final ControlJuego control;
    private final IModeloDatos modeloJuego;
    private PanelCarta cartaSeleccionadaVista;

    public PanelMano(ControlJuego control, IModeloDatos modeloJuego) {
        this.control = control;
        this.modeloJuego = modeloJuego;
        setLayout(null);
        setOpaque(false);
    }

    public void refrescarMano() {
        removeAll();
        
        JugadorDTO usuario = modeloJuego.getJugadorLocalDTO();

        if (usuario == null) {
            revalidate();
            repaint();
            return;
        }

        List<CartaDTO> cartas = usuario.getCartas();
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
        int espacio = (n == 1) ? 0 : Math.min(70, (anchoPanel - anchoCarta) / (n - 1));
        int x = (n == 1) ? (anchoPanel - anchoCarta) / 2 : (anchoPanel - ((n - 1) * espacio + anchoCarta)) / 2;

        for (CartaDTO c : cartas) {
            PanelCarta pCarta = new PanelCarta(c, control);
            pCarta.setBounds(x, y, anchoCarta, altoCarta);
            configurarEventoCarta(pCarta);
            add(pCarta, 0);
            x += espacio;
        }

        revalidate();
        repaint();
    }

    private void configurarEventoCarta(PanelCarta pCarta) {
        pCarta.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (cartaSeleccionadaVista != null && cartaSeleccionadaVista.getModelo().equals(pCarta.getModelo())) {
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

    private void actualizarZoom(CartaDTO modelo) {
        java.awt.Window ventana = SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof FrameTablero frame) {
            frame.getPanelTablero().getPanelZoom().mostrarCarta(modelo);
        }
    }

    private void intentarLanzar(CartaDTO modeloCarta) {
        if (modeloCarta.isEsComodin()) {
            Frame padre = (Frame) SwingUtilities.getWindowAncestor(this);
            control.solicitarSeleccionColor(modeloCarta, padre);
        } else {
            control.solicitarTirarCarta(modeloCarta);
        }
        this.cartaSeleccionadaVista = null;
        repaint();
    }
}