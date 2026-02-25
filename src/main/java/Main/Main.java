import MVCMenu.ControladorMenu;
import MVCMenu.ModeloMenuImpl;
import MVCMenu.VistaMenu;

// Importaciones futuras de tu siguiente paquete
// import MVCJuego.ControladorJuego;
// import MVCJuego.ModeloJuegoImpl;
// import MVCJuego.VistaTablero;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            // 
            //INICIALIZAR PAQUETE MENÚ (El que ya tenemos)
            // 
            ModeloMenuImpl modeloMenu = new ModeloMenuImpl();
            VistaMenu vistaMenu = new VistaMenu(modeloMenu);
            modeloMenu.registrarObservador(vistaMenu);
            ControladorMenu controladorMenu = new ControladorMenu(modeloMenu);
            vistaMenu.setControlador(controladorMenu);

            //
            //INICIALIZAR PAQUETE JUEGO (El que haremos después)
            //
            /*
            ModeloJuegoImpl modeloJuego = new ModeloJuegoImpl();
            VistaTablero vistaJuego = new VistaTablero(modeloJuego);
            modeloJuego.registrarObservador(vistaJuego);
            ControladorJuego controladorJuego = new ControladorJuego(modeloJuego);
            vistaJuego.setControlador(controladorJuego);
            */

            //
            //CONECTAR PAQUETES (Tu regla: Control habla a Control)
            //
            // Aquí le pasamos el Controlador del Juego al Controlador del Menú.
            // Así, cuando el Menú termine, el ControladorMenu puede hacer algo como:
            // controladorSiguiente.iniciarPartida(modeloMenu.getNombreJugador());
            
            // controladorMenu.setControladorSiguiente(controladorJuego);

            // 
            // ARRANCAR LA APLICACIÓN
            // 
            // Empezamos mostrando solo la vista del menú
            vistaMenu.setVisible(true);
        });
    }
}
