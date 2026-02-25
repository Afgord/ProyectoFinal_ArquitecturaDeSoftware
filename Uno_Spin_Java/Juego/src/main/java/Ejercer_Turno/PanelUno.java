/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelUno extends JPanel {

    private BufferedImage uno;
    private BufferedImage alerta;
    private PanelTablero panelTablero;

    private boolean unoActivo = false;
    private boolean alertaActivo = false;

    public PanelUno(PanelTablero panelTablero) {
        this.panelTablero = panelTablero;
        setBackground(Color.RED);
        cargarImagen();
        agregarEventoClick();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (unoActivo) {
            g.drawImage(uno, 0, 0, this.getWidth(), this.getHeight(), this);
        }

        if (alertaActivo) {
            g.drawImage(alerta, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    private void cargarImagen() {
        try {
            URL url1 = getClass().getResource("/otros/uno.png");
            URL url2 = getClass().getResource("/otros/alerta.png");

            uno = ImageIO.read(url1);
            alerta = ImageIO.read(url2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void agregarEventoClick() { 
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (unoActivo) {
                    panelTablero.reproducirUno();
                    unoActivo = false;
                    repaint();
                } else if (alertaActivo) {
                    panelTablero.reproducirAlerta();
                    alertaActivo = false;
                    repaint();
                }
            }
        });
    }

    public void activarUno() {
        unoActivo = true;
        alertaActivo = false;
        repaint();
    }

    public void activarAlerta() {
        alertaActivo = true;
        unoActivo = false;
        repaint();
    }

    public void desactivarUno() {
        unoActivo = false;
        repaint();
    }

    public void desactivarAlerta() {
        alertaActivo = false;
        repaint();
    }
}
