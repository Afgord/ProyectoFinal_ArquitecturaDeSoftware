/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cambiar_Color.MVC;

import java.awt.*;
import javax.swing.*;
/**
 * 
 * @author lagar
 */
public class PanelSelectorColor extends JDialog {

    private final ControlColor controlColor;

    public PanelSelectorColor(Frame padre, ControlColor controlColor) {
        super(padre, true);
        this.controlColor = controlColor;
        
        setUndecorated(true);
        initComponents();
        pack();
        setLocationRelativeTo(padre);
    }

    private void initComponents() {
        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(new Color(35, 35, 35));
        principal.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel lb = new JLabel("SELECCIONA UN COLOR", SwingConstants.CENTER);
        lb.setForeground(Color.WHITE);
        lb.setFont(new Font("Arial", Font.BOLD, 16));
        lb.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        principal.add(lb, BorderLayout.NORTH);

        JPanel pnlBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlBotones.setOpaque(false);
        pnlBotones.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlBotones.add(crearBtn("Azul", controlColor.getAzul()));
        pnlBotones.add(crearBtn("Rojo", controlColor.getRojo()));
        pnlBotones.add(crearBtn("Amarillo", controlColor.getAmarillo()));
        pnlBotones.add(crearBtn("Verde", controlColor.getVerde()));

        principal.add(pnlBotones, BorderLayout.CENTER);
        add(principal);
    }

    private JButton crearBtn(String texto, Color c) {
        JButton btn = new JButton(texto.toUpperCase());
        btn.setBackground(c);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(120, 60));
        btn.setFocusPainted(false);
        
        double brillo = (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue()) / 255;
        btn.setForeground(brillo > 0.6 ? Color.BLACK : Color.WHITE);

        btn.addActionListener(e -> {
            controlColor.seleccionarColor(c, texto.toLowerCase());
            this.dispose(); 
        });
        return btn;
    }
}