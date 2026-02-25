package Infraestructura;

/**
 * @author Angel Bletran
 * MOCK ROUTER 
 * como observer pero de alto nivel
 * Escucha eventos del sistema para decidir qué pantalla mostrar.
 */
public class MockRouter implements Observer {
    
    private static MockRouter instancia;

    private MockRouter() {}

    public static MockRouter getInstancia() {
        if (instancia == null) {
            instancia = new MockRouter();
        }
        return instancia;
    }

    public void navegarA(String pantallaDestino) {
        System.out.println(">>> ROUTER: Navegando a pantalla [" + pantallaDestino + "] <<<");
        // Aquí en el futuro ocultaríamos el JFrame actual y mostraríamos el nuevo
    }

    @Override
    public void update(EventoJuego evento) {
        // El Router reacciona a cambios de estado importantes (Navegación)
        if (evento.getTipo() == TipoEvento.PARTIDA_INICIADA) {
            System.out.println(">>> ROUTER: Detecté evento PARTIDA_INICIADA. Cambiando de escena...");
            navegarA("PANTALLA_JUEGO (Tablero)");
        }
    }
}
