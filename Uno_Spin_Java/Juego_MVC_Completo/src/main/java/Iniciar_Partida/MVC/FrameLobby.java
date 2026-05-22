package Iniciar_Partida.MVC;

import Iniciar_Partida.Interfaces.IModeloLobby;
import Iniciar_Partida.Interfaces.ObservadorLobby;
import Iniciar_Partida.MVC.PanelesVista.PanelLobby;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FrameLobby extends JFrame implements ObservadorLobby {

    private final PanelLobby panelLobby;
    private Runnable onPartidaIniciada;

    public FrameLobby(ControlLobby control, IModeloLobby modelo) {
        modelo.registrarObservador(this);

        setTitle("UNO Spin - Lobby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        panelLobby = new PanelLobby(control, modelo);
        add(panelLobby);
        panelLobby.refrescar();
    }

    public void setOnPartidaIniciada(Runnable callback) {
        this.onPartidaIniciada = callback;
    }

    @Override
    public void notificarCambio(IModeloLobby contexto) {
        SwingUtilities.invokeLater(() -> {
            panelLobby.refrescar();

            if (contexto.isPartidaIniciada() && onPartidaIniciada != null) {
                Runnable callback = onPartidaIniciada;
                onPartidaIniciada = null;
                callback.run();
            }
        });
    }
}
