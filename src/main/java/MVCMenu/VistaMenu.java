package MVCMenu;

import Infraestructura.EventoJuego;
import Infraestructura.Observer;
import Infraestructura.TipoEvento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VistaMenu extends JFrame implements Observer {

    // Referencias MVC
    private IControladorMenu controlador;
    private final IModeloMenu modelo; // Referencia para el PULL MODEL (leer datos)

    // Componentes de la GUI
    private JTextField txtNombre;
    private JButton btnJugar;
    private JLabel lblEstado;
    private JLabel lblTitulo;

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
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10)); // Layout simple para prototipo
        setLocationRelativeTo(null); // Centrar en pantalla
    }

    private void inicializarComponentes() {
        //Título
        lblTitulo = new JLabel("Bienvenido a UNO Spin", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo);

        //Instrucción
        add(new JLabel("Ingresa tu nombre:", SwingConstants.CENTER));

        //Campo de Texto
        txtNombre = new JTextField();
        // Listener para enviar cada tecla al controlador
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (controlador != null) {
                    controlador.actualizarNombre(txtNombre.getText());
                }
            }
        });
        add(txtNombre);

        // 4. Etiqueta de Estado/Error
        lblEstado = new JLabel("Esperando jugador...", SwingConstants.CENTER);
        lblEstado.setForeground(Color.GRAY);
        add(lblEstado);

        // 5. Botón de Acción
        btnJugar = new JButton("ENTRAR AL JUEGO");
        btnJugar.addActionListener(e -> {
            if (controlador != null) {
                controlador.solicitarInicio();
            }
        });
        add(btnJugar);
    }

    //IMPLEMENTACIÓN DEL PATRÓN OBSERVER la vista reacciona

    @Override
    public void update(EventoJuego evento) { // error va en el modelo 
        switch (evento.getTipo()) {
            case NOMBRE_JUGADOR_ACTUALIZADO:
                // Ejemplo: Podríamos habilitar/deshabilitar el botón si está vacío
                String nombreActual = modelo.getNombreJugador(); // PULL data
                // Solo para debug visual en este prototipo:
                lblEstado.setText("Escribiendo: " + nombreActual);
                lblEstado.setForeground(Color.BLUE);
                break;

            case ERROR_VALIDACION:
                String error = modelo.getMensajeError(); // PULL data
                lblEstado.setText("Error: " + error);
                lblEstado.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, error, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                break;

            case PARTIDA_INICIADA:
                lblEstado.setText("¡Partida Iniciada!");
                lblEstado.setForeground(Color.GREEN);
                break;
                
            default:
                break;
        }
    }
}
