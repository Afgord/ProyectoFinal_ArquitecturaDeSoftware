package Ejercer_Turno.MVC;

import Ejercer_Turno.Interfaces.IModelEventos;
import Ejercer_Turno.Interfaces.IServicioSeleccionColor;
import dtos.CartaDTO;
import entidades.Colores;
import java.awt.Frame;

/**
 * Controlador del MVC.
 *
 * No mantiene estado: traduce las acciones de la vista en intenciones
 * publicables hacia la red a través de IModelEventos. La vista se actualiza
 * cuando el ModeloJuego recibe el evento de estado correspondiente.
 */
public class ControlJuego {

    private final IModelEventos eventos;
    private final IServicioSeleccionColor servicioColor;

    public ControlJuego(IModelEventos eventos, IServicioSeleccionColor servicioColor) {
        this.eventos = eventos;
        this.servicioColor = servicioColor;
    }

    public void solicitarTirarCarta(CartaDTO carta) {
        eventos.emitirTirarCarta(carta);
    }

    public void solicitarRobarCarta() {
        eventos.emitirRobarCarta();
    }

    public void solicitarPasarTurno() {
        eventos.emitirPasarTurno();
    }

    public void solicitarGritar() {
        eventos.emitirGritar();
    }

    public void solicitarSeleccionColor(CartaDTO comodin, Frame padre) {
        servicioColor.solicitarColor(padre, UtilCarta.coloresConfigurados(), (Colores elegido) -> {
            if (elegido != null) {
                CartaDTO conColor = new CartaDTO(comodin.getValor(), elegido);
                eventos.emitirTirarCarta(conColor);
            }
        });
    }
}
