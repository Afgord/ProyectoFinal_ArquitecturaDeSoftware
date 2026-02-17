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
    private PanelJugador panelJugador1;
    private PanelJugador panelJugador2;
    private PanelJugador panelJugador3;
    private PanelJugador panelJugador4;
    private PanelMano panelMano1;
    private Mazo mazo;
    private PanelDescarte panelDescarte;
    private PanelMazo panelMazo;
    
    public panelTablero() {
        setPreferredSize(new Dimension(1200,750));
        setBackground(Color.RED);
        setLayout(null);
        mazo = new Mazo();
        this.panelJugador1 = new PanelJugador("xrapayel",0, "/avatares/defecto.png");
        this.panelJugador1.setBounds(200, 510, 250, 80);
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
        this.panelDescarte = new PanelDescarte(mazo);
        this.panelDescarte.setBounds(600, 280, 100, 120);
        add(panelDescarte);
        this.panelMano1 = new PanelMano(mazo.tomarCartas(7),panelDescarte,panelJugador1);
        this.panelMano1.setBounds(200,590,800,120);
        add(panelMano1);
        this.panelMazo = new PanelMazo(mazo,panelMano1);
        this.panelMazo.setBounds(480,280,100,120);
        add(panelMazo);
        
                
        
    }
    
}
