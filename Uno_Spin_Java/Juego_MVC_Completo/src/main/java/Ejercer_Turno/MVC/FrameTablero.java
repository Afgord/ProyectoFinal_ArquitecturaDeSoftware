/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Interfaces.Observador;
import Ejercer_Turno.Interfaces.IModeloDatos;
import Ejercer_Turno.MVC.PanelesVista.PanelTablero;
import Ejercer_Turno.Dominio.Carta;
import audio.AudioManager;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class FrameTablero extends JFrame implements Observador {

    private final ControlJuego control;
    private PanelTablero panelTablero;
    private final AudioManager audio;
    
    private int cantidadMazoPrevio;
    private Carta cartaCimaPrevia;

    public FrameTablero(ControlJuego control, IModeloDatos modeloInicial, AudioManager audio) {
        this.control = control;
        this.audio = audio;
        
        modeloInicial.registrarObservador(this);
        
        this.cantidadMazoPrevio = modeloInicial.getMazo().getCantidadCartas();
        this.cartaCimaPrevia = modeloInicial.getDescarte().getCartaCima();

        setTitle("UNO SPIN - MVC Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1280, 800);

        this.panelTablero = new PanelTablero(control, modeloInicial, audio);
        add(panelTablero);

        try {
            setIconImage(new ImageIcon(getClass().getResource("/otros/icono.png")).getImage());
        } catch (Exception e) {
            System.err.println("Error icono: " + e.getMessage());
        }

        setLocationRelativeTo(null);
        setVisible(true);
        
        panelTablero.actualizarManos();
    }

    @Override
    public void notificarCambio(IModeloDatos contexto) {
        SwingUtilities.invokeLater(() -> {
            procesarEfectosSonoros(contexto);
            panelTablero.actualizarEstadoVisual(contexto);
            panelTablero.actualizarMazo();
            panelTablero.actualizarDescarte();
            panelTablero.refrescarTurno();

            if (contexto.getTablero().getJugadorActual().getNumCartas() == 0) {
                if (audio != null) audio.stopMusic();
                String ganador = contexto.getTablero().getJugadorActual().getNombre();
                JOptionPane.showMessageDialog(this, "¡Felicidades " + ganador + "! Has ganado.");
                System.exit(0);
            }
        });
    }

    private void procesarEfectosSonoros(IModeloDatos contexto) {
        if (audio == null) return;
        int mazoActual = contexto.getMazo().getCantidadCartas();
        Carta cartaCimaActual = contexto.getDescarte().getCartaCima();
        
        if (mazoActual < cantidadMazoPrevio) {
            audio.playEffect("jalar");
        } else if (cartaCimaActual != null && !cartaCimaActual.equals(cartaCimaPrevia)) {
            audio.playEffect("tirar");
        } else if (mazoActual == cantidadMazoPrevio && (cartaCimaActual == null ? cartaCimaPrevia == null : cartaCimaActual.equals(cartaCimaPrevia))) {
            audio.playEffect("alerta");
        }

        this.cantidadMazoPrevio = mazoActual;
        this.cartaCimaPrevia = cartaCimaActual;
    }

    public PanelTablero getPanelTablero() { return panelTablero; }
}