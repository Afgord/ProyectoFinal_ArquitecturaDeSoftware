package Crear_Partida_Lobby.MVC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Pantalla de acceso al lobby: el usuario ingresa nombre y elige
 * "Crear Partida" o "Unirse a Partida" (con ID). Tras la accion, se
 * cierra y abre FrameLobby.
 */
public class FrameAccesoLobby extends JFrame {

    public interface CallbackAcceso {
        void crearPartida();
        void unirsePartida(String idPartida);
    }

    public FrameAccesoLobby(ControlLobby control, CallbackAcceso callback) {
        setTitle("UNO SPIN - Acceso al Lobby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(540, 360);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(35, 50, 65));

        JLabel titulo = new JLabel("UNO SPIN", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel formulario = new JPanel(new GridLayout(0, 1, 8, 8));
        formulario.setOpaque(false);
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        JLabel lblNombre = new JLabel("Nombre del jugador");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JTextField txtNombre = new JTextField();
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombre.setPreferredSize(new Dimension(0, 30));

        JLabel lblId = new JLabel("ID de partida (solo para unirse)");
        lblId.setForeground(Color.WHITE);
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JTextField txtId = new JTextField();
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtId.setPreferredSize(new Dimension(0, 30));

        formulario.add(lblNombre);
        formulario.add(txtNombre);
        formulario.add(lblId);
        formulario.add(txtId);

        JButton btnCrear = new JButton("Crear partida");
        JButton btnUnirse = new JButton("Unirse a partida");
        estilizar(btnCrear, new Color(120, 200, 120));
        estilizar(btnUnirse, new Color(120, 158, 230));

        JPanel botonera = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botonera.setOpaque(false);
        botonera.add(btnCrear);
        botonera.add(btnUnirse);

        btnCrear.addActionListener((ActionEvent e) -> {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa un nombre para continuar.",
                        "Falta nombre", JOptionPane.WARNING_MESSAGE);
                return;
            }
            control.setIdentidad(nombre, "/avatares/avatar1.png");
            callback.crearPartida();
            dispose();
        });

        btnUnirse.addActionListener((ActionEvent e) -> {
            String nombre = txtNombre.getText().trim();
            String id = txtId.getText().trim().toUpperCase();
            if (nombre.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa nombre e ID de partida para unirte.",
                        "Faltan datos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            control.setIdentidad(nombre, "/avatares/avatar1.png");
            callback.unirsePartida(id);
            dispose();
        });

        getContentPane().add(titulo, BorderLayout.NORTH);
        getContentPane().add(formulario, BorderLayout.CENTER);
        getContentPane().add(botonera, BorderLayout.SOUTH);
    }

    private void estilizar(JButton btn, Color fondo) {
        btn.setFocusPainted(false);
        btn.setBackground(fondo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    }
}
