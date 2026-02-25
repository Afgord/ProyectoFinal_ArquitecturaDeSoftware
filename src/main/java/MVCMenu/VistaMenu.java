package MVCMenu;

import Infraestructura.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * La Vista.
 * Implementa Observer tipado a IModeloMenu.
 * No contiene lógica de validación, solo dibuja el estado del modelo.
 */
public class VistaMenu extends JFrame implements Observer<IModeloMenu> {

    private IControladorMenu controlador;
    private final IModeloMenu modelo;

    private JTextField txtNombre;
    private JButton btnJugar;
    private JLabel lblEstado;

    // Regla estricta: Se inyecta el modelo en el constructor para lectura
    public VistaMenu(IModeloMenu modelo) {
        this.modelo = modelo;
        configurarVentana();
        inicializarComponentes();
    }

    public void setControlador(IControladorMenu controlador) {
        this.controlador = controlador;
    }

    private void configurarVentana() {
        setTitle("UNO Spin - Inicio");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        add(new JLabel("Bienvenido a UNO Spin", SwingConstants.CENTER));

        txtNombre = new JTextField();
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (controlador != null) {
                    controlador.actualizarNombre(txtNombre.getText());
                }
            }
        });
        add(txtNombre);

        lblEstado = new JLabel("Esperando jugador...", SwingConstants.CENTER);
        add(lblEstado);

        btnJugar = new JButton("INICIAR PARTIDA");
        btnJugar.addActionListener(e -> {
            if (controlador != null) {
                controlador.solicitarInicio();
            }
        });
        add(btnJugar);
    }

    // --- PATRÓN OBSERVER: PULL MODEL ---
    @Override
    public void update(IModeloMenu modeloActualizado) {
        // La vista SOLO mapea el estado del modelo a la UI. No toma decisiones de negocio.
        
        if (modeloActualizado.isListoParaJugar()) {
            lblEstado.setText("¡Partida lista para comenzar!");
            lblEstado.setForeground(Color.GREEN);
            btnJugar.setEnabled(false); // Bloquea interacción adicional
            txtNombre.setEnabled(false);
            
            // Aquí el controlador (si hablara con otros controles) 
            // o un orquestador tomaría el relevo.
            
        } else if (!modeloActualizado.getMensajeError().isEmpty()) {
            lblEstado.setText(modeloActualizado.getMensajeError());
            lblEstado.setForeground(Color.RED);
        } else {
            lblEstado.setText("Jugador: " + modeloActualizado.getNombreJugador());
            lblEstado.setForeground(Color.BLUE);
        }
    }
}