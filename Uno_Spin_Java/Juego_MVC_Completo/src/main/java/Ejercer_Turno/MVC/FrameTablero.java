package Ejercer_Turno.MVC;

import Ejercer_Turno.Interfaces.IModeloDatos;
import Ejercer_Turno.Interfaces.Observador;
import Ejercer_Turno.MVC.PanelesVista.PanelTablero;
import contenido.AudioManager;
import dtos.CartaDTO;
import dtos.JugadorDTO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class FrameTablero extends JFrame implements Observador {

    private final ControlJuego control;
    private PanelTablero panelTablero;
    private final AudioManager audio;

    private String rutaCimaPrevia;

    public FrameTablero(ControlJuego control, IModeloDatos modeloInicial, AudioManager audio) {
        this.control = control;
        this.audio = audio;

        modeloInicial.registrarObservador(this);

        CartaDTO cimaInicial = modeloInicial.getCartaDescarteDTO();
        this.rutaCimaPrevia = (cimaInicial != null) ? UtilCarta.rutaImagen(cimaInicial) : "";

        setTitle("UNO SPIN - MVC Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1280, 800);
        if (audio != null) audio.playMusicLoop();
        this.panelTablero = new PanelTablero(control, modeloInicial, audio);
        add(panelTablero);

        try {
            setIconImage(new ImageIcon(getClass().getResource("/otros/icono.png")).getImage());
        } catch (Exception e) {
            System.err.println("Error icono: " + e.getMessage());
        }

        setLocationRelativeTo(null);
        setVisible(true);

        panelTablero.actualizarManos();
    }

    @Override
    public void notificarCambio(IModeloDatos contexto) {
        SwingUtilities.invokeLater(() -> {
            procesarEfectosSonoros(contexto);
            panelTablero.actualizarEstadoVisual(contexto);
            panelTablero.actualizarMazo();
            panelTablero.actualizarDescarte();
            panelTablero.refrescarTurno();

            String idLocal = contexto.getIdJugadorLocal();
            for (JugadorDTO j : contexto.getJugadoresDTO()) {
                if (idLocal != null && idLocal.equals(j.idJugador()) && j.getNumCartas() == 0) {
                    if (audio != null) audio.stopMusic();
                    JOptionPane.showMessageDialog(this, "¡Felicidades " + j.getNombre() + "! Has ganado.");
                    System.exit(0);
                }
            }

            String ganador = contexto.getGanador();
            if (ganador != null) {
                if (audio != null) audio.stopMusic();
                JOptionPane.showMessageDialog(this, "Ganador: " + ganador);
                System.exit(0);
            }
        });
    }

    private void procesarEfectosSonoros(IModeloDatos contexto) {
        if (audio == null) return;

        CartaDTO cartaCimaActual = contexto.getCartaDescarteDTO();
        String rutaActual = (cartaCimaActual != null) ? UtilCarta.rutaImagen(cartaCimaActual) : "";

        if (!rutaActual.equals(rutaCimaPrevia) && !rutaActual.isEmpty()) {
            audio.playEffect("tirar");
        }
        this.rutaCimaPrevia = rutaActual;
    }

    public PanelTablero getPanelTablero() { return panelTablero; }
}
