/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.Dominio.Jugador;
import java.awt.*;
import javax.swing.*;

public class PanelJugador extends JPanel {
    private final Jugador jugador; 
    
    private JLabel lbUsuario;
    private JLabel lbNumCartas;
    private PanelAvatar panelAvatar;

    public PanelJugador(Jugador jugador) {
        this.jugador = jugador;
        configurarPanel();
        inicializarComponentes();
        actualizarDatos(); 
    }

    private void configurarPanel() {
        setLayout(null); 
        setPreferredSize(new Dimension(250, 80));
        setBackground(new Color(255, 204, 0));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    private void inicializarComponentes() {
        lbUsuario = new JLabel();
        lbUsuario.setBounds(90, 10, 150, 25);
        lbUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        add(lbUsuario);
        lbNumCartas = new JLabel();
        lbNumCartas.setBounds(90, 40, 150, 25);
        lbNumCartas.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lbNumCartas);
        panelAvatar = new PanelAvatar(jugador.getUrlAvatar());
        panelAvatar.setBounds(10, 5, 70, 70);
        add(panelAvatar);
    }

    public void actualizarDatos() {
        lbUsuario.setText(jugador.getNombre());
        lbNumCartas.setText("Cartas: " + jugador.getNumCartas());
        repaint();
    }
}