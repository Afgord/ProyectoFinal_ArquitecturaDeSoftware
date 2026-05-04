package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.MVC.UtilCarta;
import dtos.JugadorDTO;
import java.awt.*;
import javax.swing.*;

/**
 * Tarjeta de un jugador (rival). El avatar se asigna por índice porque
 * dtos.JugadorDTO no transporta esa información.
 */
public class PanelJugador extends JPanel {
    private final JugadorDTO jugador;
    private final boolean esTurnoActual;

    private JLabel lbUsuario;
    private JLabel lbNumCartas;
    private PanelAvatar panelAvatar;

    public PanelJugador(JugadorDTO jugador, int indice, boolean esTurnoActual) {
        this.jugador = jugador;
        this.esTurnoActual = esTurnoActual;
        configurarPanel();
        inicializarComponentes(indice);
        actualizarDatos();
    }

    private void configurarPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(250, 80));
        if (esTurnoActual) {
            setBackground(new Color(255, 255, 150));
            setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        } else {
            setBackground(new Color(255, 204, 0));
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }
    }

    private void inicializarComponentes(int indice) {
        lbUsuario = new JLabel();
        lbUsuario.setBounds(90, 10, 150, 25);
        lbUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        add(lbUsuario);

        lbNumCartas = new JLabel();
        lbNumCartas.setBounds(90, 40, 150, 25);
        lbNumCartas.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lbNumCartas);

        panelAvatar = new PanelAvatar(UtilCarta.avatarPorIndice(indice));
        panelAvatar.setBounds(10, 5, 70, 70);
        add(panelAvatar);
    }

    public void actualizarDatos() {
        lbUsuario.setText(jugador.getNombre());
        lbNumCartas.setText("Cartas: " + jugador.getNumCartas());
        repaint();
    }
}
