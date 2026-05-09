package Ejercer_Turno.MVC;

import Ejercer_Turno.Interfaces.IModeloDatos;
import Ejercer_Turno.Interfaces.Observador;
import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Modelo del MVC en modo event-driven.
 *
 * Su estado solo se modifica al recibir actualizaciones desde la red,
 * pero los metodos aplicar* aceptan unicamente DTOs/primitivos. La
 * traduccion desde Evento* la hace un aplicador en la frontera de red
 * (EventoTraductor). Por diseno, este modulo MVC no conoce el paquete
 * org.eventos.*.
 */
public class ModeloJuego implements IModeloDatos {

    private final List<Observador> observadores = new ArrayList<>();
    private final String idJugadorLocal;

    private List<JugadorDTO> jugadores = new ArrayList<>();
    private CartaDTO cartaCima = null;
    private String idJugadorTurnoActual = null;
    private boolean ultimaJugadaValida = true;
    private String ganador = null;

    public ModeloJuego(String idJugadorLocal) {
        this.idJugadorLocal = idJugadorLocal;
    }

    @Override
    public void registrarObservador(Observador o) {
        if (o != null && !observadores.contains(o)) {
            observadores.add(o);
        }
    }

    private void notificar() {
        for (Observador o : observadores) {
            o.notificarCambio(this);
        }
    }

    public void aplicarActualizacion(List<JugadorDTO> jugadores, CartaDTO cartaCima, String idJugadorTurnoActual) {
        this.jugadores = (jugadores != null) ? new ArrayList<>(jugadores) : new ArrayList<>();
        this.cartaCima = cartaCima;
        this.idJugadorTurnoActual = idJugadorTurnoActual;
        this.ultimaJugadaValida = true;
        notificar();
    }

    public void aplicarFallo() {
        this.ultimaJugadaValida = false;
        notificar();
    }

    public void aplicarResultadoRuleta(List<JugadorDTO> jugadores, CartaDTO cartaCima, String idJugadorTurnoActual) {
        this.jugadores = (jugadores != null) ? new ArrayList<>(jugadores) : new ArrayList<>();
        this.cartaCima = cartaCima;
        this.idJugadorTurnoActual = idJugadorTurnoActual;
        this.ultimaJugadaValida = true;
        notificar();
    }

    public void aplicarResultadoGrito(List<JugadorDTO> jugadores) {
        if (jugadores != null) {
            this.jugadores = new ArrayList<>(jugadores);
        }
        notificar();
    }

    public void aplicarGanador(String nombreGanador) {
        if (nombreGanador != null) {
            this.ganador = nombreGanador;
        }
        notificar();
    }

    @Override
    public CartaDTO getCartaDescarteDTO() {
        return cartaCima;
    }

    @Override
    public List<JugadorDTO> getJugadoresDTO() {
        return Collections.unmodifiableList(jugadores);
    }

    @Override
    public boolean isUltimaJugadaValida() {
        return ultimaJugadaValida;
    }

    @Override
    public String getIdJugadorLocal() {
        return idJugadorLocal;
    }

    @Override
    public String getIdJugadorTurnoActual() {
        return idJugadorTurnoActual;
    }

    @Override
    public String getGanador() {
        return ganador;
    }
}
