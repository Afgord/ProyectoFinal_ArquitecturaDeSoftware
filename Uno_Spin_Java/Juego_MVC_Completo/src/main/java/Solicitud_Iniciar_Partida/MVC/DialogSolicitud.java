package Solicitud_Iniciar_Partida.MVC;

import Crear_Partida_Lobby.Interfaces.IModeloLobbyDatos;
import dtos.AceptacionDTO;
import dtos.EstadoAceptacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Modal "El jugador X ha solicitado iniciar partida".
 *
 * Lee directamente IModeloLobbyDatos para pintar la lista de
 * aceptaciones. Los botones ACEPTAR / ESPERAR delegan en
 * ControlSolicitud, y solo estan habilitados si el jugador local
 * todavia no ha aceptado. La instancia se reutiliza via refrescar()
 * cuando llegan EventoEstadoAceptacionActualizado.
 */
public class DialogSolicitud extends JDialog {

    private final ControlSolicitud control;
    private final IModeloLobbyDatos modelo;

    private JLabel lblTitulo;
    private JPanel listaAceptaciones;
    private JButton btnAceptar;
    private JButton btnEsperar;

    public DialogSolicitud(Window padre, ControlSolicitud control, IModeloLobbyDatos modelo) {
        super(padre, "Solicitud de inicio", ModalityType.MODELESS);
        this.control = control;
        this.modelo = modelo;

        setSize(540, 380);
        setLocationRelativeTo(padre);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(new Color(20, 20, 25));
        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(construirEncabezado(), BorderLayout.NORTH);
        getContentPane().add(construirCentro(), BorderLayout.CENTER);
        getContentPane().add(construirInferior(), BorderLayout.SOUTH);

        refrescar();
    }

    private JPanel construirEncabezado() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        lblTitulo = new JLabel("El jugador ha solicitado iniciar partida", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        header.add(lblTitulo);
        return header;
    }

    private JPanel construirCentro() {
        JPanel central = new JPanel(new BorderLayout());
        central.setOpaque(false);
        central.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        JLabel jugadores = new JLabel("Jugadores:");
        jugadores.setForeground(Color.WHITE);
        jugadores.setFont(new Font("Segoe UI", Font.BOLD, 14));
        central.add(jugadores, BorderLayout.NORTH);

        listaAceptaciones = new JPanel(new GridLayout(0, 1, 4, 4));
        listaAceptaciones.setOpaque(false);
        listaAceptaciones.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        central.add(listaAceptaciones, BorderLayout.CENTER);
        return central;
    }

    private JPanel construirInferior() {
        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 12));
        inferior.setOpaque(false);
        btnAceptar = boton("ACEPTAR", new Color(120, 200, 120));
        btnEsperar = boton("ESPERAR", new Color(231, 90, 90));
        btnAceptar.addActionListener(e -> control.aceptar());
        btnEsperar.addActionListener(e -> control.esperar());
        inferior.add(btnAceptar);
        inferior.add(Box.createHorizontalStrut(10));
        inferior.add(btnEsperar);
        return inferior;
    }

    private JButton boton(String texto, Color fondo) {
        JButton b = new JButton(texto);
        b.setFocusPainted(false);
        b.setBackground(fondo);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        b.setPreferredSize(new Dimension(140, 38));
        return b;
    }

    public void refrescar() {
        String nombreSolicitante = modelo.getNombreSolicitante();
        if (nombreSolicitante != null) {
            lblTitulo.setText("El jugador " + nombreSolicitante + " ha solicitado iniciar partida");
        }

        listaAceptaciones.removeAll();
        String idLocal = modelo.getIdJugadorLocal();
        EstadoAceptacion miEstado = EstadoAceptacion.PENDIENTE;
        for (AceptacionDTO a : modelo.getAceptaciones()) {
            JPanel fila = new JPanel(new BorderLayout());
            fila.setOpaque(false);
            JLabel nombre = new JLabel(a.getNombre());
            nombre.setForeground(Color.WHITE);
            nombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
            JLabel estado = new JLabel(textoEstado(a.getEstado()), SwingConstants.RIGHT);
            estado.setForeground(colorEstado(a.getEstado()));
            estado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            fila.add(nombre, BorderLayout.WEST);
            fila.add(estado, BorderLayout.EAST);
            listaAceptaciones.add(fila);
            if (idLocal != null && idLocal.equals(a.getIdJugador())) {
                miEstado = a.getEstado();
            }
        }
        listaAceptaciones.revalidate();
        listaAceptaciones.repaint();

        boolean yaAcepto = miEstado == EstadoAceptacion.ACEPTADO;
        btnAceptar.setEnabled(!yaAcepto);
        btnEsperar.setEnabled(true);
    }

    private static String textoEstado(EstadoAceptacion e) {
        if (e == null) return "...";
        return switch (e) {
            case ACEPTADO -> "Acepto";
            case ESPERANDO -> ".. esperando";
            case PENDIENTE -> "...";
        };
    }

    private static Color colorEstado(EstadoAceptacion e) {
        if (e == null) return Color.LIGHT_GRAY;
        return switch (e) {
            case ACEPTADO -> new Color(120, 200, 120);
            case ESPERANDO -> new Color(231, 175, 90);
            case PENDIENTE -> Color.LIGHT_GRAY;
        };
    }
}
