/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Interfaces.Observador;
import Ejercer_Turno.Interfaces.IModeloDatos;
import Ejercer_Turno.MVC.PanelesVista.PanelTablero;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * 
 * @author lagar
 */
public class FrameTablero extends JFrame implements Observador {

    private final ControlJuego control;
    private final IModeloDatos modelo; 
    private PanelTablero panelTablero;

    public FrameTablero(ControlJuego control, IModeloDatos modelo) {
        this.control = control;
        this.modelo = modelo;
        this.modelo.registrarObservador(this);
        
        setTitle("UNO SPIN - MVC Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1280, 800);
        control.reproducirMusica();
        this.panelTablero = new PanelTablero(control, modelo);
        add(panelTablero);
         try {
            setIconImage(new ImageIcon(getClass().getResource("/otros/icono.png")).getImage());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono: " + e);
        }
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void notificarCambio(IModeloDatos contexto) {
        panelTablero.actualizarMazo();
        panelTablero.actualizarDescarte();
        panelTablero.actualizarManos();
        panelTablero.refrescarTurno();

        if (contexto.getTablero().getJugadorActual().getNumCartas() == 0) {
            String ganador = contexto.getTablero().getJugadorActual().getNombre();
            JOptionPane.showMessageDialog(this, "¡Felicidades " + ganador + "! Has ganado.");
            System.exit(0);
        }
    }
    
    public PanelTablero getPanelTablero() {
        return panelTablero;
    }
}