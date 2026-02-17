/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author lagar
 */
public class panelTablero extends JPanel{
    private JPanel panelJugador1;
    private JPanel panelJugador2;
    private JPanel panelJugador3;
    private JPanel panelJugador4;
    private JPanel panelMano1;
    private Mazo mazo;
    
    public panelTablero() {
        setPreferredSize(new Dimension(1200,750));
        setBackground(Color.RED);
        setLayout(null);
        mazo = new Mazo();
        this.panelJugador1 = new PanelJugador("xrapayel",7, "/avatares/defecto.png");
        this.panelJugador1.setBounds(0, 0, 250, 80);
        add(panelJugador1);
        this.panelJugador2 = new PanelJugador("angel",6, "/avatares/defecto.png");
        this.panelJugador2.setBounds(0, 80, 250, 80);
        add(panelJugador2);
        this.panelJugador3 = new PanelJugador("roberto", 8, "/avatares/defecto.png");
        this.panelJugador3.setBounds(0, 160, 250, 80);
        add(panelJugador3);
        this.panelJugador4 = new PanelJugador("lafayett", 8, "/avatares/defecto.png");
        this.panelJugador4.setBounds(0, 240, 250, 80);
        add(panelJugador4);
        this.panelMano1 = new PanelMano(mazo.tomarCartas(7));
        this.panelMano1.setBounds(200,590,800,120);
        add(panelMano1);
        
    }
    
}
