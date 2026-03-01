package MVCLobby;

import Dominio.CardColor;
import Dominio.Player;
import ContractsLobby.ILobbyReadModel;
import ContractsLobby.IObserverLobby;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * VISTA PASIVA DEL LOBBY.
 * Implementa una transición simple entre Registro y Espera.
 */
public class LobbyView extends JFrame implements IObserverLobby {
    private final LobbyController controller;
    private final ILobbyReadModel model;
    private String localPlayerId = null;

    // Componentes de Layout
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    // Componentes de Registro
    private final JTextField txtName = new JTextField(15);
    private final JComboBox<CardColor> comboColors = new JComboBox<>();
    private final JComboBox<String> comboAvatars = new JComboBox<>();
    private final JButton btnRegister = new JButton("Unirse a Partida");

    // Componentes de Espera
    private final DefaultListModel<String> listModelPlayers = new DefaultListModel<>();
    private final JList<String> listPlayers = new JList<>(listModelPlayers);
    private final JCheckBox chkReady = new JCheckBox("Estoy Listo");
    private final JButton btnStart = new JButton("Iniciar Juego");

    public LobbyView(LobbyController controller, ILobbyReadModel model) {
        this.controller = controller;
        this.model = model;

        setupWindow();
        setupRegistrationPanel();
        setupWaitingPanel();
        
        add(mainPanel);
        model.subscribe(this);
        
        // Carga inicial de datos
        refreshRegistryOptions();
    }

    private void setupWindow() {
        setTitle("UNO SPIN - Lobby");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setupRegistrationPanel() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBorder(BorderFactory.createTitledBorder("Registro de Jugador"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; pnl.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; pnl.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 1; pnl.add(new JLabel("Color:"), gbc);
        gbc.gridx = 1; pnl.add(comboColors, gbc);

        gbc.gridx = 0; gbc.gridy = 2; pnl.add(new JLabel("Avatar:"), gbc);
        gbc.gridx = 1; pnl.add(comboAvatars, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        pnl.add(btnRegister, gbc);

        btnRegister.addActionListener(e -> {
            String name = txtName.getText().trim();
            CardColor color = (CardColor) comboColors.getSelectedItem();
            String avatar = (String) comboAvatars.getSelectedItem();

            if (!name.isEmpty() && color != null && avatar != null) {
                if (controller.onRegisterPlayer(name, avatar, color)) {
                    // Buscamos nuestro ID en el modelo (recién agregado)
                    for (Player p : model.getJoinedPlayers()) {
                        if (p.getName().equals(name)) {
                            localPlayerId = p.getId();
                            break;
                        }
                    }
                    cardLayout.show(mainPanel, "WAITING");
                } else {
                    JOptionPane.showMessageDialog(this, "Registro fallido. El nombre, color o avatar ya están en uso.");
                }
            }
        });

        mainPanel.add(pnl, "REGISTRY");
    }

    private void setupWaitingPanel() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnl.add(new JLabel("Jugadores en el Lobby:"), BorderLayout.NORTH);
        pnl.add(new JScrollPane(listPlayers), BorderLayout.CENTER);

        JPanel pnlSouth = new JPanel(new GridLayout(2, 1));
        pnlSouth.add(chkReady);
        pnlSouth.add(btnStart);
        pnl.add(pnlSouth, BorderLayout.SOUTH);

        chkReady.addActionListener(e -> {
            if (localPlayerId != null) {
                controller.onToggleReady(localPlayerId, chkReady.isSelected());
            }
        });

        btnStart.addActionListener(e -> controller.onRequestStart());

        mainPanel.add(pnl, "WAITING");
    }

    private void refreshRegistryOptions() {
        comboColors.removeAllItems();
        for (CardColor c : model.getAvailableColors()) comboColors.addItem(c);

        comboAvatars.removeAllItems();
        for (String a : model.getAvailableAvatars()) comboAvatars.addItem(a);
    }

    @Override
    public void update(ILobbyReadModel lobby) {
        // 1. Si no estamos registrados, actualizamos las opciones disponibles
        if (localPlayerId == null) {
            refreshRegistryOptions();
        }

        // 2. Actualizar la lista de jugadores visibles
        listModelPlayers.clear();
        for (Player p : lobby.getJoinedPlayers()) {
            String status = p.isReady() ? "[LISTO]" : "[ESPERANDO]";
            listModelPlayers.addElement(p.getName() + " " + status);
        }

        // 3. Gestionar botón de inicio (Req #5)
        btnStart.setEnabled(lobby.canStartMatch());

        // 4. Si la partida está iniciando, cerramos esta ventana
        if (lobby.isMatchStarting()) {
            this.setVisible(false);
            this.dispose();
        }
    }
}
