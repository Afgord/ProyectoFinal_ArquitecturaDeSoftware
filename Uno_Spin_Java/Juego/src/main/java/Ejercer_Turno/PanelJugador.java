/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author lagar
 */
public class PanelJugador extends JPanel{
    private JLabel lbUsuario;
    private JLabel lbNumCartas;
    private JPanel panelAvatar;

    public PanelJugador(String nombre, int numCartas, String urlAvatar) {
        setLayout(null);
        setPreferredSize(new Dimension(250,80));
        setBackground(new Color(255,204,0));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        lbUsuario = new JLabel(nombre != null ? nombre : "Jugador");
        lbUsuario.setBounds(90, 10, 150, 25);
        lbUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        lbUsuario.setForeground(Color.BLACK);
        add(lbUsuario);
        lbNumCartas = new JLabel("Cartas: " + numCartas);
        lbNumCartas.setBounds(90, 40, 150, 25);
        lbNumCartas.setFont(new Font("Arial", Font.PLAIN, 14));
        lbNumCartas.setForeground(Color.DARK_GRAY);
        add(lbNumCartas);
        panelAvatar = new panelAvatar(urlAvatar);
        panelAvatar.setBounds(10, 5, 70, 70);
        add(panelAvatar);
    }
}
