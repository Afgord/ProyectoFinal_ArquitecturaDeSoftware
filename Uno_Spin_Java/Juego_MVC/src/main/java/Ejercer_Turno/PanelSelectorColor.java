/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.*;
import javax.swing.*;

public class PanelSelectorColor extends JPanel {
    private Color colorSeleccionado;
    private String nombreColorSeleccionado;
    private boolean seleccionRealizada = false;

    public PanelSelectorColor(Color azul, Color rojo, Color amarillo, Color verde) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        setBackground(new Color(50, 50, 50)); 
        JLabel titulo = new JLabel("Elije el color:", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        add(titulo, BorderLayout.NORTH);
        JPanel botonesPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        botonesPanel.setOpaque(false);
        botonesPanel.add(crearBoton("Azul", azul));
        botonesPanel.add(crearBoton("Rojo", rojo));
        botonesPanel.add(crearBoton("Amarillo", amarillo));
        botonesPanel.add(crearBoton("Verde", verde));
        add(botonesPanel, BorderLayout.CENTER);
    }

    private JButton crearBoton(String nombre, Color colorFondo) {
        JButton btn = new JButton(nombre);
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE); 
        btn.setFocusPainted(false);
        btn.addActionListener(e -> {
            this.colorSeleccionado = colorFondo;
            this.nombreColorSeleccionado = nombre.toLowerCase();
            this.seleccionRealizada = true;

        });
        return btn;
    }

    public Color getColorSeleccionado() { return colorSeleccionado; }
    public String getNombreColorSeleccionado() { return nombreColorSeleccionado; }
    public boolean isSeleccionRealizada() { return seleccionRealizada; }
}
