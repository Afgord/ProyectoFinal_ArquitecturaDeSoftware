package Main;

import Infraestructura.MockRouter;
import MVCMenu.ControladorMenu;
import MVCMenu.ModeloMenuImpl;
import MVCMenu.VistaMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Ejecutar en el hilo de despacho de eventos de Swing mejor practica
        SwingUtilities.invokeLater(() -> {
            
            // Creo el modelo como primer paso
            ModeloMenuImpl modelo = new ModeloMenuImpl();

            // creo el navegador o router y los suscribo al modelo
            MockRouter router = MockRouter.getInstancia();
            modelo.registrarObservador(router);

            // creo la vista y le inyecto el modelo
            VistaMenu vista = new VistaMenu(modelo); // error
            modelo.registrarObservador(vista); // La vista también observa

            // creo e controlador y le inyecto el modelo
            ControladorMenu controlador = new ControladorMenu(modelo);

            // conecto el controlador a la vista
            vista.setControlador(controlador);

            // se muestras
            vista.setVisible(true);
            
            System.out.println("Aplicación iniciada correctamente.");
        });
    }
}
