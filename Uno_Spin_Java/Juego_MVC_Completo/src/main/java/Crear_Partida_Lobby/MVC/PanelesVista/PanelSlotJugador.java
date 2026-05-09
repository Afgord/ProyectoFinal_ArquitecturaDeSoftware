package Crear_Partida_Lobby.MVC.PanelesVista;

import dtos.JugadorDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Vista de un slot del lobby: avatar circular con color por slot
 * (rosa, rojo, azul, verde) y nombre del jugador o "..." si esta vacio.
 */
public class PanelSlotJugador extends JPanel {

    private static final Color[] COLORES_SLOT = {
        new Color(255, 138, 200),  // rosa
        new Color(231, 90, 90),    // rojo
        new Color(120, 158, 230),  // azul
        new Color(120, 200, 120)   // verde
    };

    private final int indiceSlot;
    private JugadorDTO jugador;
    private final JLabel nombre;

    public PanelSlotJugador(int indiceSlot) {
        this.indiceSlot = indiceSlot;
        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(220, 70));

        nombre = new JLabel("...");
        nombre.setForeground(Color.DARK_GRAY);
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nombre.setBounds(78, 20, 140, 30);
        add(nombre);
    }

    public void setJugador(JugadorDTO jugador) {
        this.jugador = jugador;
        nombre.setText(jugador != null ? jugador.getNombre() : "...");
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int diametro = 60;
        int x = 8;
        int y = 5;

        Color color = (jugador != null) ? colorSlot() : new Color(220, 220, 220);
        g2.setColor(color);
        g2.fillOval(x, y, diametro, diametro);

        // Silueta de persona simplificada
        g2.setColor(new Color(60, 60, 60, 160));
        int cabezaR = diametro / 5;
        int cabezaX = x + diametro / 2 - cabezaR;
        int cabezaY = y + diametro / 4 - cabezaR;
        g2.fillOval(cabezaX, cabezaY, cabezaR * 2, cabezaR * 2);
        g2.fillOval(x + diametro / 4, y + diametro / 2,
                diametro / 2, diametro / 2);

        g2.dispose();
    }

    private Color colorSlot() {
        return COLORES_SLOT[indiceSlot % COLORES_SLOT.length];
    }

    public int getIndiceSlot() { return indiceSlot; }
}
