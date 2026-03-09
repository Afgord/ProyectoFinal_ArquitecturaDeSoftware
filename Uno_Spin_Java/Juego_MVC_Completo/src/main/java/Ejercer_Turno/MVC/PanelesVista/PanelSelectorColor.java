/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.Dominio.Carta;
import Ejercer_Turno.Dominio.Mazo;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.ModeloJuego;
import java.awt.*;
import javax.swing.*;

public class PanelSelectorColor extends JPanel {

    public PanelSelectorColor(ControlJuego control, ModeloJuego modelo, Carta carta, JDialog dialogo) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        setBackground(new Color(50, 50, 50));
        JLabel titulo = new JLabel("Elije el color:", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        add(titulo, BorderLayout.NORTH);
        JPanel botonesPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        botonesPanel.setOpaque(false);
        Mazo mazo = modelo.getTablero().getMazo();
        botonesPanel.add(crearBoton("Azul", mazo.getcAzul(), control, carta, dialogo));
        botonesPanel.add(crearBoton("Rojo", mazo.getcRojo(), control, carta, dialogo));
        botonesPanel.add(crearBoton("Amarillo", mazo.getcAmarillo(), control, carta, dialogo));
        botonesPanel.add(crearBoton("Verde", mazo.getcVerde(), control, carta, dialogo));
        add(botonesPanel, BorderLayout.CENTER);
    }

    private JButton crearBoton(String nombre, Color color, ControlJuego control, Carta carta, JDialog dialogo) {
        JButton btn = new JButton(nombre);
        btn.setBackground(color);
        if (color.equals(Color.WHITE) || color.equals(Color.PINK) || color.equals(Color.YELLOW)) {
            btn.setForeground(Color.BLACK);
        } else {
            btn.setForeground(Color.WHITE);
        }
        
        btn.setFocusPainted(false);
        btn.addActionListener(e -> {
            control.solicitarTirarCartaNegra(carta, color, nombre.toLowerCase());
            dialogo.dispose();
        });
        return btn;
    }

    public static void mostrar(Component padre, ControlJuego control, ModeloJuego modelo, Carta carta) {
        Window parentWindow = SwingUtilities.getWindowAncestor(padre);
        JDialog dialog = new JDialog(parentWindow, "Selecciona Color", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.add(new PanelSelectorColor(control, modelo, carta, dialog));
        dialog.pack();
        dialog.setLocationRelativeTo(parentWindow);
        dialog.setVisible(true);
    }
}