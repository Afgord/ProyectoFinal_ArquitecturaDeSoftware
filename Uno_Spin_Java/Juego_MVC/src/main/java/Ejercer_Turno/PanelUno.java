/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * VISTA: PanelUno
 * Representa visualmente el botón para cantar "UNO" o la alerta de proximidad.
 */
public class PanelUno extends JPanel {

    private BufferedImage imgUno;
    private BufferedImage imgAlerta;
    private final PanelTablero panelTablero;

    // Estados visuales
    private boolean unoActivo = false;
    private boolean alertaActivo = false;

    public PanelUno(PanelTablero panelTablero) {
        this.panelTablero = panelTablero;
        
        // Configuración visual básica
        setBackground(new Color(0, 0, 0, 0)); // Fondo transparente si es posible
        setOpaque(false);
        
        cargarImagenes();
        configurarEventos();
    }

    private void cargarImagenes() {
        try {
            URL urlUno = getClass().getResource("/otros/uno.png");
            URL urlAlerta = getClass().getResource("/otros/alerta.png");

            if (urlUno != null) imgUno = ImageIO.read(urlUno);
            if (urlAlerta != null) imgAlerta = ImageIO.read(urlAlerta);

        } catch (IOException e) {
            System.err.println("Error cargando imágenes en PanelUno: " + e.getMessage());
        }
    }

    private void configurarEventos() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Si el jugador hace clic cuando el botón está visible
                if (unoActivo) {
                    panelTablero.reproducirUno();
                    desactivarTodo();
                } else if (alertaActivo) {
                    panelTablero.reproducirAlerta();
                    // No necesariamente se desactiva aquí, depende de la lógica del turno
                }
            }
        });
    }

    // --- MÉTODOS DE CONTROL VISUAL ---

    public void activarUno() {
        this.unoActivo = true;
        this.alertaActivo = false;
        repaint();
    }

    public void activarAlerta() {
        this.alertaActivo = true;
        this.unoActivo = false;
        repaint();
    }

    public void desactivarTodo() {
        this.unoActivo = false;
        this.alertaActivo = false;
        repaint();
    }
    
    // Alias para compatibilidad con PanelJugador
    public void desactivarUno() { desactivarTodo(); }
    public void desactivarAlerta() { desactivarTodo(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujamos según el estado actual
        if (unoActivo && imgUno != null) {
            g.drawImage(imgUno, 0, 0, getWidth(), getHeight(), this);
        } else if (alertaActivo && imgAlerta != null) {
            g.drawImage(imgAlerta, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
