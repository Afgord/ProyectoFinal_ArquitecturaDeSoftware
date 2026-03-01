package MVCJugarTurno;

import Contracts.IMatchReadModel;
import Contracts.IObserver;
import Dominio.Card;
import Dominio.CardColor;
import Dominio.Player;
import javax.swing.*;
import java.awt.*;

/**
 * VISTA PASIVA (Bajo el rigor de Martin Fowler).
 * No tiene lógica. Solo sabe "pintar" lo que lee del IMatchReadModel 
 * y avisar al MatchController cuando el usuario hace clic.
 */
public class MatchView extends JFrame implements IObserver {
    private final String localPlayerId;
    private final MatchController controller;
    
    // Elementos de Interfaz
    private final JLabel lblTurn = new JLabel("Turno: -");
    private final JLabel lblStatus = new JLabel("Mesa: -");
    private final JLabel lblLastSpin = new JLabel("Última Ruleta: Ninguna");
    private final JPanel pnlHand = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
    private final JPanel pnlOpponents = new JPanel();
    private final JButton btnDraw = new JButton("Robar Carta");
    private final JButton btnSpin = new JButton("Girar Ruleta");
    private final JButton btnUno = new JButton("¡UNO!");
    private final JTextArea txtLog = new JTextArea(8, 30);
    
    private String lastSeenLog = "";

    public MatchView(String playerId, MatchController controller, IMatchReadModel model) {
        this.localPlayerId = playerId;
        this.controller = controller;
        
        setupWindow();
        setupLayout();
        setupEvents();

        // Suscripción al modelo (Publisher)
        model.subscribe(this);
    }

    private void setupWindow() {
        setTitle("UNO SPIN - Jugador: " + localPlayerId);
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(15, 15));

        // PANEL NORTE: Información del turno y estado de la ruleta
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        pnlNorth.setBackground(new Color(235, 235, 235));

        JPanel pnlGameInfo = new JPanel(new GridLayout(2, 1));
        pnlGameInfo.setOpaque(false);
        lblTurn.setFont(new Font("Arial", Font.BOLD, 18));
        pnlGameInfo.add(lblTurn);
        pnlGameInfo.add(lblStatus);
        
        lblLastSpin.setFont(new Font("Arial", Font.BOLD, 14));
        lblLastSpin.setForeground(new Color(180, 0, 0));
        lblLastSpin.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLastSpin.setBorder(BorderFactory.createTitledBorder("Efecto Activo"));

        pnlNorth.add(pnlGameInfo, BorderLayout.WEST);
        pnlNorth.add(lblLastSpin, BorderLayout.EAST);
        add(pnlNorth, BorderLayout.NORTH);

        // PANEL ESTE: Seguimiento de Oponentes (Solicitud del usuario)
        pnlOpponents.setLayout(new BoxLayout(pnlOpponents, BoxLayout.Y_AXIS));
        pnlOpponents.setBorder(BorderFactory.createTitledBorder("Estado Oponentes"));
        pnlOpponents.setPreferredSize(new Dimension(220, 0));
        pnlOpponents.setBackground(new Color(245, 245, 245));
        add(new JScrollPane(pnlOpponents), BorderLayout.EAST);

        // PANEL CENTRAL: Mano del jugador
        pnlHand.setBackground(new Color(50, 50, 50));
        JScrollPane scrollHand = new JScrollPane(pnlHand);
        scrollHand.setBorder(BorderFactory.createTitledBorder(null, "Tu Mano (Cartas)", 0, 0, null, Color.WHITE));
        add(scrollHand, BorderLayout.CENTER);

        // PANEL SUR: Botones de control e Historial (Log)
        JPanel pnlSouth = new JPanel(new BorderLayout());
        
        JPanel pnlBtns = new JPanel();
        btnUno.setBackground(new Color(200, 0, 0));
        btnUno.setForeground(Color.WHITE);
        btnUno.setFont(new Font("Arial", Font.BOLD, 12));
        pnlBtns.add(btnDraw);
        pnlBtns.add(btnSpin);
        pnlBtns.add(btnUno);
        
        txtLog.setEditable(false);
        txtLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setPreferredSize(new Dimension(0, 150));

        pnlSouth.add(pnlBtns, BorderLayout.NORTH);
        pnlSouth.add(scrollLog, BorderLayout.SOUTH);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        btnDraw.addActionListener(e -> controller.onDrawCard(localPlayerId));
        btnSpin.addActionListener(e -> controller.onSpinWheel(localPlayerId));
        btnUno.addActionListener(e -> controller.onShoutUno(localPlayerId));
    }

    @Override
    public void update(IMatchReadModel model) {
        // 1. Identificar estado del turno y del jugador local
        int currentIdx = model.getCurrentPlayerIndex();
        boolean isMe = model.getPlayer(currentIdx).getId().equals(localPlayerId);
        
        // 2. Actualizar Textos Principales
        lblTurn.setText("TURNO: " + model.getPlayer(currentIdx).getName() + (isMe ? " (¡TU TURNO!)" : ""));
        lblStatus.setText("CARTA EN MESA: [" + model.getTopDiscard() + "] | COLOR: " + model.getCurrentColor());
        lblLastSpin.setText("<html><div style='text-align:right;'><b>" + model.getLastSpinMessage() + "</b></div></html>");

        // 3. Actualizar Historial (Log de eventos)
        if (!model.getLastEventLog().equals(lastSeenLog)) {
            txtLog.append("> " + model.getLastEventLog() + "\n");
            txtLog.setCaretPosition(txtLog.getDocument().getLength());
            lastSeenLog = model.getLastEventLog();
        }

        // 4. Actualizar Panel de Oponentes (Conteos)
        pnlOpponents.removeAll();
        for (int i = 0; i < model.getPlayerCount(); i++) {
            Player p = model.getPlayer(i);
            if (!p.getId().equals(localPlayerId)) {
                JLabel lblOp = new JLabel(String.format("<html><b>%s</b><br>Cartas: %d %s</html>", 
                    p.getName(), 
                    p.getHand().size(),
                    (p.getHand().size() == 1 ? "⚠️" : "")));
                lblOp.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                pnlOpponents.add(lblOp);
            }
        }

        // 5. Renderizar Mano (Botones de cartas)
        pnlHand.removeAll();
        Player localPlayer = null;
        for (int i = 0; i < model.getPlayerCount(); i++) {
            if (model.getPlayer(i).getId().equals(localPlayerId)) {
                localPlayer = model.getPlayer(i);
                break;
            }
        }
        
        if (localPlayer != null) {
            for (Card c : localPlayer.getHand().getCards()) {
                JButton btn = new JButton(c.toString());
                // Lógica de habilitación estricta
                btn.setEnabled(isMe && !model.isWaitingForSpin() && !model.isWaitingForColorSelection() && !model.isFinished());
                btn.addActionListener(e -> controller.onPlayCard(localPlayerId, c));
                pnlHand.add(btn);
            }
        }

        // 6. Estado de Botones de Acción
        btnDraw.setEnabled(isMe && !model.isWaitingForSpin() && !model.isFinished());
        btnSpin.setEnabled(isMe && model.isWaitingForSpin() && !model.isFinished());
        btnUno.setEnabled(localPlayer != null && localPlayer.getHand().size() <= 2 && !model.isFinished());

        // 7. Disparadores de Diálogos (Ruleta / Color)
        if (isMe && model.isWaitingForColorSelection()) {
            SwingUtilities.invokeLater(this::handleColorPick);
        }

        if (model.isFinished()) {
            JOptionPane.showMessageDialog(this, "PARTIDA FINALIZADA\nGanador: " + model.getPlayer(currentIdx).getName());
        }

        revalidate();
        repaint();
    }

    private void handleColorPick() {
        String[] opts = {"RED", "BLUE", "GREEN", "YELLOW"};
        int r = JOptionPane.showOptionDialog(this, "Selecciona el nuevo color:", "Comodín / Ruleta", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);
        
        // CORRECCIÓN CLÍNICA: Cambiado ColorCarta por CardColor para coincidir con el Dominio
        if (r >= 0) {
            controller.onSelectColor(localPlayerId, CardColor.valueOf(opts[r]));
        }
    }
}
