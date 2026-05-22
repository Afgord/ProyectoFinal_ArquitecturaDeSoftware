package Iniciar_Partida.MVC.PanelesVista;

import Iniciar_Partida.Interfaces.IModeloLobby;
import Iniciar_Partida.MVC.ControlLobby;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class PanelLobby extends JPanel {

    private final PanelSlotsJugadores panelSlots;
    private final PanelAccionesLobby panelAcciones;

    public PanelLobby(ControlLobby control, IModeloLobby modelo) {
        setLayout(new BorderLayout(10, 10));
        panelSlots = new PanelSlotsJugadores(modelo);
        panelAcciones = new PanelAccionesLobby(control, modelo);
        add(panelSlots, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    public void refrescar() {
        panelSlots.refrescar();
        panelAcciones.refrescar();
    }
}
