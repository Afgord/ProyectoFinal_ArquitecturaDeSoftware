/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.Interfaces.IModeloDatos;
import DTOs.MazoDTO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * 
 * @author Luis Rafael
 */
public class PanelMazo extends JPanel {

    private final ControlJuego control;
    private final IModeloDatos modeloJuego;
    private BufferedImage imgAtras;

    public PanelMazo(ControlJuego control, IModeloDatos modeloJuego) {
        this.control = control;
        this.modeloJuego = modeloJuego;
        
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
        
        MazoDTO mazo = modeloJuego.getMazoDTO();
        int cantidad = mazo.getCantidadCartas();
        boolean estaVacio = (cantidad == 0);

        if (imgAtras != null && !estaVacio) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (cantidad > 1) {
                g2d.drawImage(imgAtras, 4, 4, getWidth() - 8, getHeight() - 8, this);
            }
            g2d.drawImage(imgAtras, 0, 0, getWidth() - 8, getHeight() - 8, this);
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString(String.valueOf(cantidad), 5, 15);
        }
    }
}