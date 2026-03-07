/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.ModeloJuego;
import Ejercer_Turno.Dominio.Carta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelSelectorColor extends JPanel {

    private final ControlJuego control;
    private final ModeloJuego modelo;
    private final Carta cartaComodin;
    private final JDialog ventanaModal;

    public PanelSelectorColor(ControlJuego control, ModeloJuego modelo, Carta carta, JDialog ventana) {
        this.control = control;
        this.modelo = modelo;
        this.cartaComodin = carta;
        this.ventanaModal = ventana;
        
        setLayout(new GridLayout(2, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(45, 45, 45));
        inicializarBotones();
    }

    private void inicializarBotones() {
        agregarBotonColor("Azul", modelo.getMazo().getcAzul());
        agregarBotonColor("Rojo", modelo.getMazo().getcRojo());
        agregarBotonColor("Amarillo", modelo.getMazo().getcAmarillo());
        agregarBotonColor("Verde", modelo.getMazo().getcVerde());
    }

    private void agregarBotonColor(String nombre, Color color) {
        JPanel boton = new JPanel();
        boton.setBackground(color);
        boton.setPreferredSize(new Dimension(80, 80));
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                control.solicitarTirarCartaNegra(cartaComodin, color, nombre.toLowerCase());
                ventanaModal.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
        });

        add(boton);
    }
    
    public static void mostrar(Component padre, ControlJuego control, ModeloJuego modelo, Carta carta) {
        // Buscamos el Frame principal para que el JDialog sea realmente modal y visible
        Window owner = SwingUtilities.getWindowAncestor(padre);
        JDialog dialog = new JDialog(owner, "Seleccionar Color", Dialog.ModalityType.APPLICATION_MODAL);

        dialog.setUndecorated(true); // Estética de juego
        dialog.add(new PanelSelectorColor(control, modelo, carta, dialog));
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }
}