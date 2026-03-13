/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC.PanelesVista;

import Girar_Ruleta.PanelRuleta;
import Ejercer_Turno.Dominio.Jugador;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.Interfaces.IModeloDatos; 
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PanelTablero extends JPanel {

    private final ControlJuego control;
    private final IModeloDatos modelo; 

    private PanelMazo panelMazo;
    private PanelDescarte panelDescarte;
    private PanelMano panelManoJugador;
    private PanelCartaSeleccionada panelZoom;
    private PanelUno panelUno;
    private PanelRuleta panelRuleta;

    public PanelTablero(ControlJuego control, IModeloDatos modelo) {
        this.control = control;
        this.modelo = modelo;

        setPreferredSize(new Dimension(1200, 750));
        setBackground(Color.RED);
        setLayout(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        panelUno = new PanelUno(control);
        panelUno.setBounds(1040, 600, 150, 100);
        add(panelUno);

        panelMazo = new PanelMazo(control, modelo);
        panelMazo.setBounds(610, 280, 100, 120);
        add(panelMazo);

        panelDescarte = new PanelDescarte(modelo);
        panelDescarte.setBounds(720, 280, 100, 120);
        add(panelDescarte);

        panelManoJugador = new PanelMano(control, modelo);
        panelManoJugador.setBounds(200, 590, 800, 150);
        add(panelManoJugador);

        panelZoom = new PanelCartaSeleccionada();
        panelZoom.setBounds(800, 450, 100, 120);
        add(panelZoom);

        JLabel lbTexto = new JLabel("Carta Seleccionada");
        lbTexto.setBounds(760, 420, 200, 30);
        lbTexto.setFont(new Font("Arial", Font.BOLD, 18));
        lbTexto.setForeground(Color.WHITE);
        add(lbTexto);

        panelRuleta = new PanelRuleta();
        panelRuleta.setBounds(300, 200, 300, 300);
        add(panelRuleta);

        actualizarRivales();
        panelManoJugador.refrescarMano();
    }

    public void actualizarRivales() {
        Component[] componentes = getComponents();
        for (Component c : componentes) {
            if (c instanceof PanelJugador || c instanceof PanelManoSecundaria) {
                remove(c);
            }
        }

        List<Jugador> jugadores = modelo.getJugadores();
        Jugador actual = modelo.getTablero().getJugadorActual();
        int rivalIdx = 0;

        for (Jugador j : jugadores) {
            if (j.equals(actual)) continue;

            PanelJugador pj = new PanelJugador(j);
            PanelManoSecundaria pms;

            switch (rivalIdx) {
                case 0 -> {
                    pj.setBounds(0, 0, 250, 80);
                    pms = new PanelManoSecundaria(j, "izquierda");
                    pms.setBounds(0, 100, 120, 400);
                    add(pj); add(pms);
                }
                case 1 -> {
                    pj.setBounds(350, 120, 250, 80);
                    pms = new PanelManoSecundaria(j, "arriba");
                    pms.setBounds(350, 0, 500, 120);
                    add(pj); add(pms);
                }
                case 2 -> {
                    pj.setBounds(920, 0, 250, 80);
                    pms = new PanelManoSecundaria(j, "derecha");
                    pms.setBounds(1070, 100, 120, 400);
                    add(pj); add(pms);
                }
            }
            rivalIdx++;
        }
        revalidate();
        repaint();
    }

    public void actualizarMazo() { panelMazo.repaint(); }
    public void actualizarDescarte() { panelDescarte.repaint(); }
    public void actualizarManos() { 
        panelManoJugador.refrescarMano(); 
        actualizarRivales();
    }
    public void refrescarTurno() { actualizarManos(); }
    
    public PanelCartaSeleccionada getPanelZoom() { return panelZoom; }
    public PanelUno getPanelUno() { return panelUno; }
}