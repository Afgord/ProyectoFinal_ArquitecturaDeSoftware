/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import Girar_Ruleta.PanelRuleta;
import java.awt.*;
import javax.swing.*;
import audio.AudioController;

public class PanelTablero extends JPanel implements ITableroObserver {

    private final Tablero tableroEntidad;
    private PanelJugador panelJugador1, panelJugador2, panelJugador3, panelJugador4;
    private PanelMano panelMano1;
    private PanelManoSecundaria panelMano2, panelMano3, panelMano4;
    private PanelDescarte panelDescarte;
    private PanelMazo panelMazo;
    private PanelCartaSeleccionada panelCartaSeleccionada;
    private PanelUno panelUno;
    private PanelRuleta panelRuleta;

    public PanelTablero(Tablero tablero, Jugador j1, Jugador j2, Jugador j3, Jugador j4, int numCartasInicial) {
        this.tableroEntidad = tablero;
        this.tableroEntidad.addObserver(this);

        setPreferredSize(new Dimension(1200, 750));
        setBackground(Color.RED); 
        setLayout(null);

        inicializarComponentes(j1, j2, j3, j4, numCartasInicial);
    }

    private void inicializarComponentes(Jugador j1, Jugador j2, Jugador j3, Jugador j4, int numCartas) {
        // 1. Botón UNO y Alerta
        panelUno = new PanelUno(this);
        panelUno.setBounds(1040, 600, 150, 100);
        add(panelUno);

        // 2. Descarte (Usa la primera carta del mazo configurado con colores)
        panelDescarte = new PanelDescarte(tableroEntidad.getDescarte());
        panelDescarte.setBounds(720, 280, 100, 120);
        add(panelDescarte);

        // 3. Info de Jugadores
        panelJugador1 = new PanelJugador(j1, panelUno);
        panelJugador1.setBounds(200, 510, 250, 80);
        add(panelJugador1);

        // 4. Mazo y Mano Principal
        panelMano1 = new PanelMano(j1, panelDescarte, panelJugador1, this);
        panelMano1.setBounds(200, 590, 800, 120);
        add(panelMano1);

        panelMazo = new PanelMazo(tableroEntidad.getMazo(), j1, this);
        panelMazo.setBounds(610, 280, 100, 120);
        add(panelMazo);
        // --- AGREGAR ESTO EN inicializarComponentes ---

        // Paneles de Info para los otros jugadores
        panelJugador2 = new PanelJugador(j2, null); 
        panelJugador2.setBounds(0, 0, 250, 80);
        add(panelJugador2);

        panelJugador3 = new PanelJugador(j3, null);
        panelJugador3.setBounds(350, 120, 250, 80);
        add(panelJugador3);

        panelJugador4 = new PanelJugador(j4, null);
        panelJugador4.setBounds(920, 0, 250, 80);
        add(panelJugador4);

        // Manos Secundarias (Las que muestran las cartas por atrás)
        // Necesitas pasarle la mano del jugador y la ubicación para que sepa qué imagen de "atrás" usar
        this.panelMano2 = new PanelManoSecundaria(j2, "izquierda"); // Solo 2 parámetros
        this.panelMano2.setBounds(0, 100, 120, 500);
        add(panelMano2);

        this.panelMano3 = new PanelManoSecundaria(j3, "arriba");    // Solo 2 parámetros
        this.panelMano3.setBounds(350, 0, 500, 120);
        add(panelMano3);

        this.panelMano4 = new PanelManoSecundaria(j4, "derecha");   // Solo 2 parámetros
        this.panelMano4.setBounds(1070, 100, 120, 500);
        add(panelMano4);
        // 5. Carta Seleccionada (Preview)
        panelCartaSeleccionada = new PanelCartaSeleccionada();
        panelCartaSeleccionada.setBounds(800, 450, 100, 120);
        add(panelCartaSeleccionada);
        
        JLabel lbTexto = new JLabel("Carta Seleccionada");
        lbTexto.setBounds(760, 420, 200, 30);
        lbTexto.setFont(new Font("Arial", Font.BOLD, 18));
        lbTexto.setForeground(Color.WHITE);
        add(lbTexto);
        
        this.panelRuleta = new PanelRuleta();
        this.panelRuleta.setBounds(300, 200, 300, 300); // Ajusta según tu diseño
        add(panelRuleta);
        // Reparto inicial (Lógica de Entidades)
        repartir(j1, j2, j3, j4, numCartas);
    }

    private void repartir(Jugador j1, Jugador j2, Jugador j3, Jugador j4, int cant) {
        for (int i = 0; i < cant; i++) {
            j1.agregarCarta(tableroEntidad.getMazo().tomarUnaCarta());
            j2.agregarCarta(tableroEntidad.getMazo().tomarUnaCarta());
            j3.agregarCarta(tableroEntidad.getMazo().tomarUnaCarta());
            j4.agregarCarta(tableroEntidad.getMazo().tomarUnaCarta());
        }
    }

    @Override
    public void actualizar() { repaint(); }

    public PanelCartaSeleccionada getPanelCartaSeleccionada() { return panelCartaSeleccionada; }
    public void reproducirTirar() { AudioController.playEffect("tirar"); }
    public void reproducirJalar() { AudioController.playEffect("jalar"); }
    public void reproducirUno() { AudioController.playEffect("uno"); }
    public void reproducirAlerta() { AudioController.playEffect("alerta"); }

    @Override
    public void cambiarTurno() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void finalizarJuego() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Mazo getMazo() {
        return tableroEntidad.getMazo();
    }
}