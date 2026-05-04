package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.MVC.ControlJuego;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Pintura del mazo. Sin contador de cartas: ningún evento de la red lo
 * provee, por lo que solo se dibuja el dorso. Reactivar cuando exista un
 * evento que publique la cantidad.
 */
public class PanelMazo extends JPanel {

    private final ControlJuego control;
    private BufferedImage imgAtras;

    public PanelMazo(ControlJuego control) {
        this.control = control;

        setPreferredSize(new Dimension(100, 120));
        setOpaque(false);
        cargarImagen();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                control.solicitarRobarCarta();
            }
        });
    }

    private void cargarImagen() {
        try {
            imgAtras = ImageIO.read(getClass().getResource("/cartas/atras.png"));
        } catch (Exception e) {
            System.err.println("No se pudo cargar imagen del mazo");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imgAtras == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(imgAtras, 4, 4, getWidth() - 8, getHeight() - 8, this);
        g2d.drawImage(imgAtras, 0, 0, getWidth() - 8, getHeight() - 8, this);
    }
}
