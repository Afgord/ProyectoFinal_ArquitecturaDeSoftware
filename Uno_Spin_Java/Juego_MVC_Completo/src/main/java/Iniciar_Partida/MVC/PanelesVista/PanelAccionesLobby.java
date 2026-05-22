package Iniciar_Partida.MVC.PanelesVista;

import Iniciar_Partida.Interfaces.IModeloLobby;
import Iniciar_Partida.MVC.ControlLobby;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelAccionesLobby extends JPanel {

    private final ControlLobby control;
    private final IModeloLobby modelo;
    private final JLabel lblEstado;
    private final JButton btnIniciar;

    public PanelAccionesLobby(ControlLobby control, IModeloLobby modelo) {
        this.control = control;
        this.modelo = modelo;
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        lblEstado = new JLabel(modelo.getMensajeEstado());
        btnIniciar = new JButton("Iniciar partida");
        btnIniciar.addActionListener(e -> control.solicitarIniciarPartida());

        add(lblEstado);
        add(btnIniciar);
    }

    public void refrescar() {
        lblEstado.setText(modelo.getMensajeEstado());

        int jugadores = modelo.getJugadoresEnSala().size();
        int minimo = modelo.getJugadoresMinimos();
        int maximo = modelo.getCapacidadMaxima();
        boolean enRangoManual = jugadores >= minimo && jugadores < maximo;
        boolean puedeConfirmar = enRangoManual
                && !modelo.isPartidaIniciada()
                && !modelo.isJugadorLocalListo();

        btnIniciar.setVisible(enRangoManual);
        btnIniciar.setEnabled(puedeConfirmar);
        btnIniciar.setText(modelo.isJugadorLocalListo()
                ? "Esperando confirmación..."
                : "Iniciar partida");
    }
}
