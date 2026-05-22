package Iniciar_Partida.MVC.PanelesVista;

import Iniciar_Partida.Interfaces.IModeloLobby;
import dtos.JugadorDTO;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelSlotsJugadores extends JPanel {

    private static final int CAPACIDAD = 4;

    private final JLabel[] slots = new JLabel[CAPACIDAD];
    private final IModeloLobby modelo;

    public PanelSlotsJugadores(IModeloLobby modelo) {
        this.modelo = modelo;
        setLayout(new GridLayout(2, 2, 8, 8));
        setBorder(BorderFactory.createTitledBorder("Jugadores en sala"));

        for (int i = 0; i < CAPACIDAD; i++) {
            slots[i] = new JLabel("Esperando jugador...", SwingConstants.CENTER);
            slots[i].setOpaque(true);
            slots[i].setBackground(new Color(240, 240, 240));
            slots[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            add(slots[i]);
        }
    }

    public void refrescar() {
        var jugadores = modelo.getJugadoresEnSala();
        String idLocal = modelo.getIdJugadorLocal();

        for (int i = 0; i < CAPACIDAD; i++) {
            if (i < jugadores.size()) {
                JugadorDTO j = jugadores.get(i);
                boolean esLocal = idLocal != null && idLocal.equals(j.idJugador());
                boolean listo = modelo.isJugadorListo(j.idJugador());
                slots[i].setText(j.getNombre() + " (ID: " + j.idJugador() + ")"
                        + (esLocal ? " [Tú]" : "")
                        + (listo ? " [Listo]" : ""));
                slots[i].setBackground(esLocal ? new Color(200, 230, 255) : Color.WHITE);
            } else {
                slots[i].setText("Esperando jugador...");
                slots[i].setBackground(new Color(240, 240, 240));
            }
        }
    }
}
