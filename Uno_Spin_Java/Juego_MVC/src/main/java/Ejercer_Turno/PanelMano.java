/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * VISTA: PanelMano
 * Gestiona la visualización de las cartas del jugador y la lógica de interacción.
 */
public class PanelMano extends JPanel implements IJugadorObserver {

    private final Jugador jugador;
    private final PanelDescarte panelDescarte;
    private final PanelJugador panelInfo;
    private final PanelTablero tableroPrincipal;
    private PanelCarta cartaSeleccionada;

    public PanelMano(Jugador jugador, PanelDescarte descarte, PanelJugador info, PanelTablero tablero) {
        this.jugador = jugador;
        this.panelDescarte = descarte;
        this.panelInfo = info;
        this.tableroPrincipal = tablero;
        
        this.jugador.addObserver(this); 
        
        setLayout(null);
        setOpaque(false);
    }

    @Override
    public void actualizar() {
        removeAll();

        List<Carta> cartasModelo = jugador.getCartasModelo();
        int n = cartasModelo.size();
        
        if (n == 0) {
            revalidate();
            repaint();
            return;
        }

        // --- LÓGICA DE POSICIONAMIENTO ---
        int anchoPanel = getWidth() > 0 ? getWidth() : 800;
        int altoPanel = getHeight() > 0 ? getHeight() : 120;
        int anchoCarta = 100;
        int altoCarta = 120;

        int espacio;
        int xInicial = 0;
        int y = (altoPanel - altoCarta) / 2;

        if (n == 1) {
            espacio = 0;
            xInicial = (anchoPanel - anchoCarta) / 2;
        } else {
            espacio = (anchoPanel - anchoCarta) / (n - 1);
            if (espacio > anchoCarta + 10) {
                espacio = 60;
                int anchoTotal = (n - 1) * espacio + anchoCarta;
                xInicial = (anchoPanel - anchoTotal) / 2;
            }
        }

        int x = xInicial;
        for (Carta modelo : cartasModelo) {
            PanelCarta pCarta = new PanelCarta(modelo);
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
                seleccionarOCartar(pCarta);
            }
        });
    }

    private void seleccionarOCartar(PanelCarta pCartaClickeada) {
        // Doble pulsación o click en carta ya seleccionada -> Intentar Tirar
        if (cartaSeleccionada != null && 
            cartaSeleccionada.getModelo() == pCartaClickeada.getModelo()) {
            
            intentarTirarCarta(pCartaClickeada);
        } else {
            // Primera pulsación -> Seleccionar
            if (cartaSeleccionada != null) {
                cartaSeleccionada.setSeleccionada(false);
            }
            cartaSeleccionada = pCartaClickeada;
            cartaSeleccionada.setSeleccionada(true);
            
            tableroPrincipal.getPanelCartaSeleccionada().mostrarCarta(pCartaClickeada.getModelo());
            repaint();
        }
    }

    private void intentarTirarCarta(PanelCarta pCarta) {
        Carta modelo = (Carta) pCarta.getModelo();
        
        // Validar reglas del juego
        if (panelDescarte.validarJugada(modelo)) {
            
            // Si es Comodín o +4, abrir selector de color
            if (modelo.getColorInterno().equalsIgnoreCase("negro")) {
                mostrarSelectorColor(modelo);
            } else {
                ejecutarTirada(modelo);
            }
            
        } else {
            tableroPrincipal.reproducirAlerta();
        }
    }

    private void mostrarSelectorColor(Carta modelo) {
        // Acceso al mazo mediante el puente de getters que creamos
        Mazo mazo = tableroPrincipal.getMazo();

        PanelSelectorColor selector = new PanelSelectorColor(
            mazo.getcAzul(), 
            mazo.getcRojo(), 
            mazo.getcAmarillo(), 
            mazo.getcVerde()
        );

        // Mostrar el panel de botones de forma modal
        int result = JOptionPane.showConfirmDialog(
            tableroPrincipal, 
            selector, 
            "Selecciona un nuevo color", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );

        if (selector.isSeleccionRealizada()) {
            // Aplicar el cambio de color a la carta antes de mandarla al descarte
            modelo.setColorExterno(selector.getColorSeleccionado());
            modelo.setColorNombre(selector.getNombreColorSeleccionado());
            ejecutarTirada(modelo);
        }
    }

    private void ejecutarTirada(Carta modelo) {
        panelDescarte.recibirCarta(modelo);
        jugador.tirarCarta(modelo); // El modelo notifica y limpia la mano automáticamente
        
        cartaSeleccionada = null;
        tableroPrincipal.getPanelCartaSeleccionada().limpiar();
        tableroPrincipal.reproducirTirar();
    }
}