package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.Interfaces.IModeloDatos;
import Ejercer_Turno.MVC.UtilCarta;
import dtos.CartaDTO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelDescarte extends JPanel {

    private final IModeloDatos modeloJuego;

    public PanelDescarte(IModeloDatos modeloJuego) {
        this.modeloJuego = modeloJuego;
        setPreferredSize(new Dimension(100, 120));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        CartaDTO cartaCima = modeloJuego.getCartaDescarteDTO();
        if (cartaCima == null) return;

        Color fondo = UtilCarta.toAwtColor(cartaCima.getColor());
        if (fondo != null) {
            g2d.setColor(fondo);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        }

        try {
            URL url = getClass().getResource(UtilCarta.rutaImagen(cartaCima));
            if (url != null) {
                BufferedImage img = ImageIO.read(url);
                g2d.drawImage(img, 5, 5, getWidth() - 10, getHeight() - 10, this);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar imagen en descarte");
        }

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 15, 15);
    }
}
