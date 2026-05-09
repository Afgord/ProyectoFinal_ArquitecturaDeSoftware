package Crear_Partida_Lobby.MVC;

import Crear_Partida_Lobby.Interfaces.IModeloLobbyDatos;
import Crear_Partida_Lobby.Interfaces.ObservadorLobby;
import Crear_Partida_Lobby.MVC.PanelesVista.PanelSlotJugador;
import Solicitud_Iniciar_Partida.MVC.ControlSolicitud;
import Solicitud_Iniciar_Partida.MVC.DialogSolicitud;
import dtos.JugadorDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Vista principal del lobby (pantalla "CREANDO PARTIDA"): muestra el ID
 * de partida, los slots de jugadores con avatar de color por posicion,
 * el bloque "Iniciar partida? SI / NO" (solo para el host) y el boton
 * ABANDONAR. Reacciona a cambios del ModeloLobby:
 *  - SOLICITANDO_INICIO -> abre DialogSolicitud
 *  - INICIADA -> dispara el callback al Ejecutador para abrir el FrameTablero
 */
public class FrameLobby extends JFrame implements ObservadorLobby {

    public interface CallbackPartidaIniciada {
        void abrirTablero(IModeloLobbyDatos modelo);
    }

    private final ControlLobby control;
    private final IModeloLobbyDatos modelo;
    private final CallbackPartidaIniciada callbackIniciada;

    private JLabel lblId;
    private final PanelSlotJugador[] slots = new PanelSlotJugador[4];
    private JButton btnIniciarSi;
    private JButton btnIniciarNo;
    private JLabel lblPregunta;
    private JButton btnAbandonar;
    private DialogSolicitud dialogSolicitud;

    public FrameLobby(ControlLobby control, IModeloLobbyDatos modelo, CallbackPartidaIniciada callback) {
        this.control = control;
        this.modelo = modelo;
        this.callbackIniciada = callback;

        setTitle("UNO SPIN - Lobby");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 560);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(35, 50, 65));
        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(construirEncabezado(), BorderLayout.NORTH);
        getContentPane().add(construirCentro(), BorderLayout.CENTER);
        getContentPane().add(construirInferior(), BorderLayout.SOUTH);

        modelo.registrarObservador(this);
        refrescar();
    }

    private JPanel construirEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel titulo = new JLabel("CREANDO PARTIDA");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Black", Font.BOLD, 28));
        header.add(titulo, BorderLayout.WEST);

        lblId = new JLabel("ID: ----", SwingConstants.RIGHT);
        lblId.setForeground(Color.WHITE);
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.add(lblId, BorderLayout.EAST);
        return header;
    }

    private JPanel construirCentro() {
        JPanel tarjeta = new JPanel(new GridLayout(4, 1, 8, 8));
        tarjeta.setBackground(new Color(255, 235, 175));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        for (int i = 0; i < 4; i++) {
            slots[i] = new PanelSlotJugador(i);
            tarjeta.add(slots[i]);
        }
        JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        tarjeta.setPreferredSize(new Dimension(560, 360));
        contenedor.add(tarjeta);
        return contenedor;
    }

    private JPanel construirInferior() {
        JPanel inferior = new JPanel(new BorderLayout());
        inferior.setOpaque(false);
        inferior.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        JPanel pregunta = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        pregunta.setOpaque(false);
        lblPregunta = new JLabel("¿Iniciar partida?");
        lblPregunta.setForeground(Color.WHITE);
        lblPregunta.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnIniciarSi = boton("SI", new Color(120, 200, 120));
        btnIniciarNo = boton("NO", new Color(231, 90, 90));
        pregunta.add(lblPregunta);
        pregunta.add(btnIniciarSi);
        pregunta.add(btnIniciarNo);
        inferior.add(pregunta, BorderLayout.CENTER);

        btnAbandonar = boton("ABANDONAR", new Color(231, 90, 90));
        JPanel pAbandonar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pAbandonar.setOpaque(false);
        pAbandonar.add(btnAbandonar);
        inferior.add(pAbandonar, BorderLayout.WEST);

        btnIniciarSi.addActionListener(e -> control.solicitarIniciarPartida());
        btnIniciarNo.addActionListener(e -> { /* no-op: el host puede esperar */ });
        btnAbandonar.addActionListener(e -> {
            control.abandonarLobby();
            dispose();
            System.exit(0);
        });

        return inferior;
    }

    private JButton boton(String texto, Color fondo) {
        JButton b = new JButton(texto);
        b.setFocusPainted(false);
        b.setBackground(fondo);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return b;
    }

    private void refrescar() {
        String id = modelo.getIdPartida();
        lblId.setText("ID: " + (id == null ? "----" : id));

        var jugadores = modelo.getJugadores();
        for (int i = 0; i < slots.length; i++) {
            JugadorDTO j = (i < jugadores.size()) ? jugadores.get(i) : null;
            slots[i].setJugador(j);
        }

        boolean soyHost = modelo.isSoyHost();
        boolean enLobby = modelo.getEstado() == IModeloLobbyDatos.Estado.EN_LOBBY;
        boolean visible = soyHost && enLobby;
        lblPregunta.setVisible(visible);
        btnIniciarSi.setVisible(visible);
        btnIniciarNo.setVisible(visible);

        // Solicitud activa: abrir el modal si no esta abierto.
        if (modelo.getEstado() == IModeloLobbyDatos.Estado.SOLICITANDO_INICIO) {
            if (dialogSolicitud == null || !dialogSolicitud.isVisible()) {
                ControlSolicitud cs = new ControlSolicitud(control);
                dialogSolicitud = new DialogSolicitud(this, cs, modelo);
                dialogSolicitud.setVisible(true);
            } else {
                dialogSolicitud.refrescar();
            }
        } else if (dialogSolicitud != null && dialogSolicitud.isVisible()) {
            dialogSolicitud.dispose();
            dialogSolicitud = null;
        }

        // Partida iniciada: cerrar lobby, abrir tablero.
        if (modelo.getEstado() == IModeloLobbyDatos.Estado.INICIADA && callbackIniciada != null) {
            if (dialogSolicitud != null) {
                dialogSolicitud.dispose();
                dialogSolicitud = null;
            }
            callbackIniciada.abrirTablero(modelo);
            dispose();
        }
    }

    @Override
    public void notificarCambioLobby(IModeloLobbyDatos contexto) {
        SwingUtilities.invokeLater(this::refrescar);
    }
}
