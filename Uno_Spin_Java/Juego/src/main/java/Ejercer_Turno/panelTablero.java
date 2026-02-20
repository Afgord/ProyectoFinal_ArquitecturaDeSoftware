/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
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
    private PanelManoSecundaria panelMano2;
    private PanelManoSecundaria panelMano3;
    private PanelManoSecundaria panelMano4;
    private Mazo mazo;
    private PanelDescarte panelDescarte;
    private PanelMazo panelMazo;
    private PanelCartaSeleccionada panelCartaSeleccionada;
    
    public panelTablero() {
        setPreferredSize(new Dimension(1200,750));
        setBackground(Color.RED);
        setLayout(null);
        mazo = new Mazo();
        this.panelJugador1 = new PanelJugador("xrapayel",0, "/avatares/defecto.png");
        this.panelJugador1.setBounds(200, 510, 250, 80);
        add(panelJugador1);
        this.panelJugador2 = new PanelJugador("angel",0, "/avatares/defecto.png");
        this.panelJugador2.setBounds(0, 0, 250, 80);
        add(panelJugador2);
        this.panelJugador3 = new PanelJugador("roberto", 0, "/avatares/defecto.png");
        this.panelJugador3.setBounds(350, 120, 250, 80);
        add(panelJugador3);
        this.panelJugador4 = new PanelJugador("lafayett", 0, "/avatares/defecto.png");
        this.panelJugador4.setBounds(920, 0, 250, 80);
        add(panelJugador4);
        this.panelDescarte = new PanelDescarte(mazo);
        this.panelDescarte.setBounds(630, 280, 100, 120);
        add(panelDescarte);
        this.panelMano1 = new PanelMano(mazo.tomarCartas(7),panelDescarte,panelJugador1,this);
        this.panelMano1.setBounds(200,590,800,120);
        add(panelMano1);
        this.panelMano2 = new PanelManoSecundaria(mazo.tomarCartas(7),panelDescarte,panelJugador2,"izquierda");
        this.panelMano2.setBounds(0,100,120,500);
        add(panelMano2);
        this.panelMano3 = new PanelManoSecundaria(mazo.tomarCartas(7),panelDescarte,panelJugador3,"arriba");
        this.panelMano3.setBounds(350,0,500,120);
        add(panelMano3);
        this.panelMano4 = new PanelManoSecundaria(mazo.tomarCartas(7),panelDescarte,panelJugador4,"derecha");
        this.panelMano4.setBounds(1070,100,120,500);
        add(panelMano4);
        this.panelMazo = new PanelMazo(mazo,panelMano1);
        this.panelMazo.setBounds(500,280,100,120);
        add(panelMazo);          
        panelCartaSeleccionada = new PanelCartaSeleccionada();
        panelCartaSeleccionada.setBounds(40, 550, 100, 120);
        add(panelCartaSeleccionada);
        JButton btnActualizar = new JButton("Reacomodar cartas");
            btnActualizar.setBounds(500, 450, 180, 40);
            add(btnActualizar);
            btnActualizar.addActionListener(e -> {
                panelMano1.refrescarMano();
        });
    }
    
    public PanelCartaSeleccionada getPanelCartaSeleccionada() {
        return panelCartaSeleccionada;
    }
    
}
