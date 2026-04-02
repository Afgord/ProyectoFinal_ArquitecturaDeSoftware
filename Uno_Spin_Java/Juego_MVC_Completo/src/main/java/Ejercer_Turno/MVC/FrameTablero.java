package Ejercer_Turno.MVC;

import Ejercer_Turno.Interfaces.Observador;
import Ejercer_Turno.Interfaces.IModeloDatos;
import Ejercer_Turno.MVC.PanelesVista.PanelTablero;
import org.uno.dto.CartaDTO;
import org.uno.dto.JugadorDTO;
import contenido.AudioManager;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/**
 * 
 * @author lagar
 */
public class FrameTablero extends JFrame implements Observador {

    private final ControlJuego control;
    private PanelTablero panelTablero;
    private final AudioManager audio;
    
    private int cantidadMazoPrevio;
    private String idCartaCimaPrevia;

    public FrameTablero(ControlJuego control, IModeloDatos modeloInicial, AudioManager audio) {
        this.control = control;
        this.audio = audio;
        
        modeloInicial.registrarObservador(this);
        
        this.cantidadMazoPrevio = modeloInicial.getMazoDTO().getCantidadCartas();
        CartaDTO cimaInicial = modeloInicial.getCartaDescarteDTO();
        this.idCartaCimaPrevia = (cimaInicial != null) ? cimaInicial.getId() : "";

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
        
        panelTablero.actualizarVisualizacionTotal();
    }

    @Override
    public void update(IModeloDatos contexto) {
        SwingUtilities.invokeLater(() -> {
            procesarEfectosSonoros(contexto);           
            panelTablero.actualizarVisualizacionTotal();
            
            List<JugadorDTO> jugadores = contexto.getJugadoresDTO();
            for (JugadorDTO j : jugadores) {
                if (j.getNumCartas() == 0) {
                    if (audio != null) audio.stopMusic();
                    JOptionPane.showMessageDialog(this, "¡Felicidades " + j.getNombre() + "! Has ganado.");
                    System.exit(0);
                }
            }
        });
    }

    private void procesarEfectosSonoros(IModeloDatos contexto) {
        if (audio == null) return;
        
        int mazoActual = contexto.getMazoDTO().getCantidadCartas();
        CartaDTO cartaCimaActual = contexto.getCartaDescarteDTO();
        String idCimaActual = (cartaCimaActual != null) ? cartaCimaActual.getId() : "";
        
        if (mazoActual < cantidadMazoPrevio) {
            audio.playEffect("jalar");
        } else if (!idCimaActual.equals(idCartaCimaPrevia) && !idCimaActual.isEmpty()) {
            audio.playEffect("tirar");
        }

        this.cantidadMazoPrevio = mazoActual;
        this.idCartaCimaPrevia = idCimaActual;
    }

    public PanelTablero getPanelTablero() { return panelTablero; }
}