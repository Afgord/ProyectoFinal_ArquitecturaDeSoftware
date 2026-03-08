/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Interfaces.Observador;
import Ejercer_Turno.Interfaces.ContextoEvento;
import Ejercer_Turno.MVC.PanelesVista.PanelTablero;
import audio.AudioController;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FrameTablero extends JFrame implements Observador {

    private final ControlJuego control;
    private final ModeloJuego modelo;
    private PanelTablero panelTablero;

    public FrameTablero(ControlJuego control, ModeloJuego modelo) {
        this.control = control;
        this.modelo = modelo;
        this.modelo.registrarObservador(this);
        
        setTitle("UNO SPIN - MVC Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1280, 800);
        AudioController.playMusic();
        this.panelTablero = new PanelTablero(control, modelo);
        add(panelTablero);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public PanelTablero getPanelTablero() {
        return panelTablero;
    }

    @Override
    public void notificarCambio(ContextoEvento contexto) {
        switch (contexto) {
            case MAZO_ACTUALIZADO:
                panelTablero.actualizarMazo();
                break;
            case DESCARTE_ACTUALIZADO:
                panelTablero.actualizarDescarte();
                break;
            case MANO_JUGADOR_ACTUALIZADO:
                panelTablero.actualizarManos();
                break;
            case TURNO_CAMBIADO:
                panelTablero.refrescarTurno();
                break;
            case ALERTA_SONIDO_TIRAR:
                AudioController.playEffect("tirar");
                break;
            case ALERTA_SONIDO_JALAR:
                AudioController.playEffect("jalar");
                break;
            case ALERTA_SONIDO_UNO:
                AudioController.playEffect("uno");
                break;
            case ALERTA_SONIDO_ERROR:
                AudioController.playEffect("alerta");
                break;
            case FIN_JUEGO:
                String ganador = modelo.getTablero().getJugadorActual().getNombre();
                JOptionPane.showMessageDialog(this, "¡Felicidades " + ganador + "! Has ganado la partida.");
                System.exit(0);
                break;
        }
    }
}