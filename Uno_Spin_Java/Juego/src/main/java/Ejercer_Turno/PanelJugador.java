/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.awt.Dimension;
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
        setPreferredSize(new Dimension(250,80));
        setBackground(Color.YELLOW);
        setLayout(null);
        if (nombre != null){
            this.lbUsuario = new JLabel(nombre);
            
        } else {
            this.lbUsuario = new JLabel("Jugador");
        }
        this.lbUsuario.setBounds(90, 14, 50, 30);
        add(lbUsuario);
        String numCartasString = String.valueOf(numCartas);
        this.lbNumCartas = new JLabel(numCartasString);
        this.lbNumCartas.setBounds(90, 28, 10, 50);
        add(lbNumCartas);
        this.panelAvatar = new panelAvatar(urlAvatar);
        this.panelAvatar.setBounds(10, 5, 70, 70);
        add(panelAvatar);
    }
}
