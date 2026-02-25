/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import Girar_Ruleta.PanelRuleta;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelTablero extends JPanel {

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
    private PanelRuleta panelRuleta;
    private PanelUno panelUno;

    private JButton btnMute;
    private boolean isMuted = false;

    public PanelTablero(
            Jugador jugador1,
            Jugador jugador2,
            Jugador jugador3,
            Jugador jugador4,
            int rangoInicio,
            int rangoFinal,
            boolean masDos,
            boolean prohibido,
            boolean reversa,
            boolean masCuatro,
            boolean cambioColor,
            Color cambioAzul,
            Color cambioRojo,
            Color cambioAmarillo,
            Color cambioVerde,
            Color cambioNegro,
            int numCartas) {

        setPreferredSize(new Dimension(1200, 750));
        setBackground(Color.RED);
        setLayout(null);

        mazo = new Mazo(rangoInicio, rangoFinal, masDos, prohibido, reversa,
                masCuatro, cambioColor, cambioAzul, cambioRojo,
                cambioAmarillo, cambioVerde, cambioNegro);

        panelUno = new PanelUno(this);
        panelUno.setBounds(1040, 600, 150, 100);
        add(panelUno);

        this.panelJugador1 = new PanelJugador(jugador1, panelUno);
        this.panelJugador1.setBounds(200, 510, 250, 80);
        add(panelJugador1);

        this.panelRuleta = new PanelRuleta();
        this.panelRuleta.setBounds(300, 200, 300, 300);
        add(panelRuleta);

        this.panelJugador2 = new PanelJugador(jugador2);
        this.panelJugador2.setBounds(0, 0, 250, 80);
        add(panelJugador2);

        this.panelJugador3 = new PanelJugador(jugador3);
        this.panelJugador3.setBounds(350, 120, 250, 80);
        add(panelJugador3);

        this.panelJugador4 = new PanelJugador(jugador4);
        this.panelJugador4.setBounds(920, 0, 250, 80);
        add(panelJugador4);

        this.panelDescarte = new PanelDescarte(mazo);
        this.panelDescarte.setBounds(720, 280, 100, 120);
        add(panelDescarte);

        this.panelMano1 = new PanelMano(
                mazo.tomarCartas(numCartas),
                panelDescarte,
                panelJugador1,
                this);
        this.panelMano1.setBounds(200, 590, 800, 120);
        add(panelMano1);

        this.panelMano2 = new PanelManoSecundaria(
                mazo.tomarCartas(numCartas),
                panelDescarte,
                panelJugador2,
                "izquierda");
        this.panelMano2.setBounds(0, 100, 120, 500);
        add(panelMano2);

        this.panelMano3 = new PanelManoSecundaria(
                mazo.tomarCartas(numCartas),
                panelDescarte,
                panelJugador3,
                "arriba");
        this.panelMano3.setBounds(350, 0, 500, 120);
        add(panelMano3);

        this.panelMano4 = new PanelManoSecundaria(
                mazo.tomarCartas(numCartas),
                panelDescarte,
                panelJugador4,
                "derecha");
        this.panelMano4.setBounds(1070, 100, 120, 500);
        add(panelMano4);

        this.panelMazo = new PanelMazo(mazo, panelMano1, this);
        this.panelMazo.setBounds(610, 280, 100, 120);
        add(panelMazo);

        panelCartaSeleccionada = new PanelCartaSeleccionada();
        panelCartaSeleccionada.setBounds(800, 450, 100, 120);
        add(panelCartaSeleccionada);

        JLabel lbTexto = new JLabel("Carta Seleccionada");
        lbTexto.setBounds(760, 420, 200, 30);
        lbTexto.setFont(new Font("Arial", Font.BOLD, 18));
        lbTexto.setForeground(Color.WHITE);
        add(lbTexto);
    }

    public PanelCartaSeleccionada getPanelCartaSeleccionada() {
        return panelCartaSeleccionada;
    }
    
    public void reproducirTirar() {
        AudioController.playEffect("tirar");
    }

    public void reproducirJalar() {
        AudioController.playEffect("jalar");
    }

    public void reproducirUno() {
        AudioController.playEffect("uno");
    }

    public void reproducirAlerta() {
        AudioController.playEffect("alerta");
    }

    public void reproducirMusica() {
        AudioController.playMusic();
    }

    public void detenerMusica() {
        AudioController.stopMusic();
    }
}