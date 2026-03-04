package Ejercer_Turno;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelCarta extends JPanel {
    private final ICartaReadOnly modelo;
    private BufferedImage imgAdelante;
    private BufferedImage imgAtras;
    private boolean seleccionada = false;

    public PanelCarta(ICartaReadOnly carta) {
        this.modelo = carta;
        
        setPreferredSize(new Dimension(100, 120));
        setOpaque(false); // Para que se vea el redondeado
        cargarImagenes();
    }

    private void cargarImagenes() {
        try {
            URL urlAtras = getClass().getResource("/cartas/atras.png");
            URL urlAdelante = getClass().getResource(modelo.getRutaImagen());
            
            if (urlAtras != null) imgAtras = ImageIO.read(urlAtras);
            if (urlAdelante != null) imgAdelante = ImageIO.read(urlAdelante);
        } catch (IOException e) {
            System.err.println("Error cargando imagen de carta: " + modelo.getRutaImagen());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Pintar el fondo con el color inyectado
        g2d.setColor(modelo.getColorExterno());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // 2. Pintar la imagen (Adelante o Atrás)
        if (modelo.isLado() && imgAdelante != null) {
            g2d.drawImage(imgAdelante, 5, 5, getWidth()-10, getHeight()-10, this);
        } else if (imgAtras != null) {
            g2d.drawImage(imgAtras, 0, 0, getWidth(), getHeight(), this);
        }

        // 3. Efecto visual de selección
        if (seleccionada) {
            g2d.setColor(new Color(255, 255, 255, 150));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRoundRect(2, 2, getWidth()-5, getHeight()-5, 15, 15);
        }
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
        repaint();
    }

    public ICartaReadOnly getModelo() { return modelo; }
    public BufferedImage getImagenAdelante() { return imgAdelante; }
}
