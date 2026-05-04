package com.mycompany.eventotraductor;

import Cambiar_Color.Implementacion.SwingSeleccionColor;
import Ejercer_Turno.Interfaces.IServicioSeleccionColor;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.FrameTablero;
import Ejercer_Turno.MVC.ModeloJuego;
import contenido.AudioManager;
import dtos.CartaDTO;
import dtos.JugadorDTO;
import entidades.Colores;
import entidades.Valor;
import java.util.Arrays;
import java.util.List;
import org.eventos.ejercer_turno.EventoActualizarTurno;

/**
 * Punto de entrada del nodo Publicador/Consumidor.
 *
 * Cablea la frontera de red vía BootstrapRed y arranca el MVC. Los
 * parámetros de conexión y la identidad del jugador los suministrará el
 * módulo Directorio (lobby) en una iteración futura; por ahora se
 * hardcodean para debug.
 */
public class Ejecutador {

    /** Misma IP que el directorio del broker para recibir broadcasts. */
    private static final String HOST_BROKER = "192.168.100.12";
    private static final int PUERTO_BROKER = 5001;
    /** Coincide con MainBroker / SimuladorCliente: jugador "n" escucha en 5001+n. */
    private static final String ID_JUGADOR_LOCAL = "1";
    private static final int PUERTO_LOCAL = 5001 + Integer.parseInt(ID_JUGADOR_LOCAL);

    public static void main(String[] args) {
        BootstrapRed bootstrap = BootstrapRed.iniciar(HOST_BROKER, PUERTO_BROKER, PUERTO_LOCAL, ID_JUGADOR_LOCAL);
        ModeloJuego modelo = bootstrap.getModelo();
        EventoTraductor eventos = bootstrap.getTraductor();

        // Estado inicial de demo mientras el Directorio/lobby no provea datos reales.
        // Equivale al estado que antes construía FachadaDominio en el Ejecutador viejo.
        List<CartaDTO> manoLocal = Arrays.asList(
                new CartaDTO(Valor.CINCO,   Colores.ROJO),
                new CartaDTO(Valor.TRES,    Colores.AZUL),
                new CartaDTO(Valor.OCHO,    Colores.VERDE),
                new CartaDTO(Valor.REVERSA, Colores.AMARILLO),
                new CartaDTO(Valor.MASDOS,  Colores.ROJO),
                new CartaDTO(Valor.MASCUATRO, Colores.NEGRO),
                new CartaDTO(Valor.UNO,     Colores.AZUL)
        );
        List<CartaDTO> manoRival1 = Arrays.asList(
                new CartaDTO(Valor.DOS, Colores.VERDE),
                new CartaDTO(Valor.SEIS, Colores.ROJO),
                new CartaDTO(Valor.NUEVE, Colores.AMARILLO),
                new CartaDTO(Valor.PROHIBIDO, Colores.AZUL),
                new CartaDTO(Valor.CERO, Colores.VERDE),
                new CartaDTO(Valor.CUATRO, Colores.ROJO),
                new CartaDTO(Valor.SIETE, Colores.AMARILLO)
        );
        List<CartaDTO> manoRival2 = Arrays.asList(
                new CartaDTO(Valor.CINCO,   Colores.AZUL),
                new CartaDTO(Valor.TRES,    Colores.VERDE),
                new CartaDTO(Valor.OCHO,    Colores.ROJO),
                new CartaDTO(Valor.REVERSA, Colores.VERDE),
                new CartaDTO(Valor.UNO,     Colores.AMARILLO),
                new CartaDTO(Valor.DOS,     Colores.ROJO),
                new CartaDTO(Valor.CUATRO,  Colores.AZUL)
        );
        List<CartaDTO> manoRival3 = Arrays.asList(
                new CartaDTO(Valor.SIETE,   Colores.VERDE),
                new CartaDTO(Valor.NUEVE,   Colores.AZUL),
                new CartaDTO(Valor.CERO,    Colores.ROJO),
                new CartaDTO(Valor.SEIS,    Colores.AMARILLO),
                new CartaDTO(Valor.MASDOS,  Colores.VERDE),
                new CartaDTO(Valor.PROHIBIDO, Colores.ROJO),
                new CartaDTO(Valor.TRES,    Colores.AZUL)
        );

        List<JugadorDTO> jugadoresDemo = Arrays.asList(
                new JugadorDTO("1", "Rafael",    manoLocal,  false),
                new JugadorDTO("2", "Jugador 2", manoRival1, false),
                new JugadorDTO("3", "Jugador 3", manoRival2, false),
                new JugadorDTO("4", "Jugador 4", manoRival3, false)
        );

        CartaDTO descarteInicial = new CartaDTO(Valor.SIETE, Colores.ROJO);

        modelo.aplicarActualizacion(new EventoActualizarTurno(
                jugadoresDemo, descarteInicial, ID_JUGADOR_LOCAL, "init"));

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
