/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;


import java.awt.Color;
import java.awt.Dimension;

import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author lagar
 */
public class PanelMano extends JPanel{
    private List<PanelCarta> cartas;
    private PanelDescarte panelDescarte;
    private PanelJugador panelJugador;
    private PanelCarta cartaSeleccionada = null;
    private panelTablero panelTablero;
    
    public PanelMano(List<PanelCarta> cartas, PanelDescarte panelDescarte, PanelJugador panelJugador, panelTablero panelTablero) {
        this.cartas = cartas;
        this.panelDescarte = panelDescarte;
        this.panelJugador = panelJugador;
        this.panelTablero = panelTablero;
                

        setBackground(Color.RED);
        setPreferredSize(new Dimension(800,120));
        setLayout(null);

        actualizarVista();
        actualizarContador();
    }
    
    private void actualizarVista() {
        removeAll();

        int anchoPanel = getWidth();
        if (anchoPanel == 0) anchoPanel = 800;

        int altoPanel = getHeight();
        if (altoPanel == 0) altoPanel = 120;

        int anchoCarta = 100;
        int altoCarta = 120;

        int n = cartas.size();
        if (n == 0) return;

        int espacio;

        if (n == 1) {
            espacio = 0;
        } else {
            espacio = (anchoPanel - anchoCarta) / (n - 1);
        }

        int x = 0;
        int y = (altoPanel - altoCarta) / 2;

        for (PanelCarta carta : cartas) {
            carta.setBounds(x, y, anchoCarta, altoCarta);
            add(carta);
            x += espacio;
        }

        revalidate();
        repaint();
    }
    
    public void agregarCarta(PanelCarta carta) {
        cartas.add(carta);
        actualizarVista();
        actualizarContador();
    }

    public void eliminarCarta(PanelCarta carta) {
        panelDescarte.setNuevaCarta(carta);
        cartas.remove(carta);
        remove(carta);
        actualizarVista();
        actualizarContador();
        revalidate();
        repaint();
    }
    
    private void actualizarContador() {
        panelJugador.actualizarCantidad(cartas.size());
    }
    
   public void seleccionarOCartar(PanelCarta carta) {

        // Si no hay carta seleccionada
        if (cartaSeleccionada == null) {
            cartaSeleccionada = carta;
            carta.setSeleccionada(true);

            panelTablero.getPanelCartaSeleccionada()
                    .setCartaSeleccionada(
                            carta.getSimbolo(),
                            carta.getLadoAdelante(),
                            carta.getColorExterno()
                    );
            return;
        }

        // Si hace click en la misma carta (descartar)
        if (cartaSeleccionada == carta) {
            carta.setSeleccionada(false);
            panelTablero.getPanelCartaSeleccionada().limpiarSeleccion();
            cartaSeleccionada = null;
            eliminarCarta(carta);
            return;
        }

        // Si selecciona otra carta diferente
        cartaSeleccionada.setSeleccionada(false);
        cartaSeleccionada = carta;
        carta.setSeleccionada(true);

        panelTablero.getPanelCartaSeleccionada()
                .setCartaSeleccionada(
                        carta.getSimbolo(),
                        carta.getLadoAdelante(),
                        carta.getColorExterno()
                );
    }
    public void refrescarMano() {
        actualizarVista();
    }
}
