/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.*;
import javax.swing.*;

public class PanelJugador extends JPanel implements IJugadorObserver {
    private final IJugadorReadOnly jugador; 
    private final PanelUno panelUno;
    
    private JLabel lbUsuario;
    private JLabel lbNumCartas;
    private PanelAvatar panelAvatar;

    public PanelJugador(Jugador jugador, PanelUno panelUno) {
        this.jugador = jugador;
        this.panelUno = panelUno;
        jugador.addObserver(this);
        configurarPanel();
        inicializarComponentes();
        actualizar();
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

    @Override
    public void actualizar() {
        lbUsuario.setText(jugador.getNombre());
        lbNumCartas.setText("Cartas: " + jugador.getNumCartas());
        if (panelUno != null) {
            if (jugador.getNumCartas() == 1) {
                panelUno.activarUno();
            } else if (jugador.getNumCartas() == 2) {
                panelUno.activarAlerta();
            } else {
                panelUno.desactivarUno();
                panelUno.desactivarAlerta();
            }
        }
        repaint();
    }
}