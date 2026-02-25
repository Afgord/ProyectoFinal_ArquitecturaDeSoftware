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

public class PanelJugador extends JPanel {
    private JLabel lbUsuario;
    private JLabel lbNumCartas;
    private PanelAvatar panelAvatar;
    private PanelUno panelUno;
    private Jugador jugador;

    public PanelJugador(Jugador jugador, PanelUno panelUno) {
        this.panelUno = panelUno;
        this.jugador = jugador;
        cargarJugador(jugador);
        verificarUno();
    }

    public PanelJugador(Jugador jugador) {
        this.jugador = jugador;
        cargarJugador(jugador);
    }

    public void actualizarCantidad(int cantidad) {
        lbNumCartas.setText("Cartas: " + cantidad);
        jugador.setNumCartas(cantidad);

        verificarUno();
    }

    public void actualizarJugador(Jugador jugador) {
        this.jugador = jugador;

        lbUsuario.setText(jugador.getNombre());
        lbNumCartas.setText("Cartas: " + jugador.getNumCartas());

        verificarUno();
    }

    private void verificarUno() {
        if (panelUno == null) return;

        if (jugador.getNumCartas() == 2) {
            panelUno.activarUno();
        } else {
            panelUno.desactivarUno();
        }
    }

    private void cargarJugador(Jugador jugador) {

        setLayout(null);
        setPreferredSize(new Dimension(250, 80));
        setBackground(new Color(255, 204, 0));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        lbUsuario = new JLabel(jugador.getNombre() != null ? jugador.getNombre() : "Jugador");
        lbUsuario.setBounds(90, 10, 150, 25);
        lbUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        lbUsuario.setForeground(Color.BLACK);
        add(lbUsuario);

        lbNumCartas = new JLabel("Cartas: " + jugador.getNumCartas());
        lbNumCartas.setBounds(90, 40, 150, 25);
        lbNumCartas.setFont(new Font("Arial", Font.PLAIN, 14));
        lbNumCartas.setForeground(Color.DARK_GRAY);
        add(lbNumCartas);

        panelAvatar = new PanelAvatar(jugador.getUrlAvatar());
        panelAvatar.setBounds(10, 5, 70, 70);
        add(panelAvatar);
    }
}
