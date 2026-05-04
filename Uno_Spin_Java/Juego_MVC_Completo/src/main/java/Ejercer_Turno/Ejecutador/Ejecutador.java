package Ejercer_Turno.Ejecutador;

import Cambiar_Color.Implementacion.SwingSeleccionColor;
import Ejercer_Turno.Interfaces.IServicioSeleccionColor;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.FrameTablero;
import Ejercer_Turno.MVC.ModeloJuego;
import com.mycompany.eventotraductor.BootstrapRed;
import com.mycompany.eventotraductor.EventoTraductor;
import contenido.AudioManager;

/**
 * Punto de entrada del nodo Publicador/Consumidor.
 *
 * Cablea la frontera de red vía BootstrapRed y arranca el MVC. Los
 * parámetros de conexión y la identidad del jugador los suministrará el
 * módulo Directorio (lobby) en una iteración futura; por ahora se
 * hardcodean para debug.
 */
public class Ejecutador {

    private static final String HOST_BROKER = "127.0.0.1";
    private static final int PUERTO_BROKER = 5555;
    private static final int PUERTO_LOCAL = 6001;
    private static final String ID_JUGADOR_LOCAL = "jugador-local";

    public static void main(String[] args) {
        BootstrapRed bootstrap = BootstrapRed.iniciar(HOST_BROKER, PUERTO_BROKER, PUERTO_LOCAL, ID_JUGADOR_LOCAL);
        ModeloJuego modelo = bootstrap.getModelo();
        EventoTraductor eventos = bootstrap.getTraductor();

        IServicioSeleccionColor servicioColor = new SwingSeleccionColor();
        ControlJuego control = new ControlJuego(eventos, servicioColor);

        AudioManager audioModel = new AudioManager();
        audioModel.loadMusic("/sound/music/dkc1_achuatic.wav");
        audioModel.loadEffect("tirar", "/sound/effect/tirar.wav", 5);
        audioModel.loadEffect("jalar", "/sound/effect/jalar.wav", 5);
        audioModel.loadEffect("uno", "/sound/effect/uno.wav", 5);
        audioModel.loadEffect("alerta", "/sound/effect/alerta.wav", 5);

        java.awt.EventQueue.invokeLater(() -> new FrameTablero(control, modelo, audioModel).setVisible(true));
    }
}
