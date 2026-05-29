package Ejercer_Turno.MVC;

import Ejercer_Turno.Interfaces.IModeloDatos;
import Ejercer_Turno.Interfaces.IReceptorEstadoJuego;
import Ejercer_Turno.Interfaces.Observador;
import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eventos.ejercer_turno.EventoActualizarTurno;
import org.eventos.ejercer_turno.EventoAnuciarGanador;
import org.eventos.ejercer_turno.EventoFallo;
import org.eventos.ejercer_turno.EventoPartidaIniciada;
import org.eventos.ejercer_turno.EventoResultadoGrito;
import org.eventos.ejercer_turno.EventoResultadoRuleta;
import org.eventos.ejercer_turno.EventoUnirseExitoso;

/**
 * Modelo del MVC en modo event-driven.
 *
 * Su estado solo se modifica al recibir eventos entrantes desde la red
 * (a través de los métodos aplicarXxx que invoca el ReceptorProcesador).
 * No invoca lógica de negocio local: cualquier validación corre en
 * DominioSuscriptor (remoto), del otro lado del broker.
 */
public class ModeloJuego implements IModeloDatos, IReceptorEstadoJuego {

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

    @Override
    public void aplicarActualizacion(EventoActualizarTurno e) {
        this.jugadores = (e.getJugadores() != null) ? new ArrayList<>(e.getJugadores()) : new ArrayList<>();
        this.cartaCima = e.getCartaEnCima();
        this.idJugadorTurnoActual = e.getIdJugadorTurnoActual();
        this.ultimaJugadaValida = true;
        notificar();
    }

    @Override
    public void aplicarPartidaIniciada(EventoPartidaIniciada e) {
        this.jugadores = (e.getJugadores() != null) ? new ArrayList<>(e.getJugadores()) : new ArrayList<>();
        this.cartaCima = e.getCartaEnCima();
        this.idJugadorTurnoActual = e.getIdJugadorTurnoActual();
        this.ultimaJugadaValida = true;
        notificar();
    }

    @Override
    public void aplicarUnirseExitoso(EventoUnirseExitoso e) {
        if (e.getJugadoresEnSala() != null) {
            this.jugadores = new ArrayList<>(e.getJugadoresEnSala());
        }
        notificar();
    }

    @Override
    public void aplicarFallo(EventoFallo e) {
        this.ultimaJugadaValida = false;
        notificar();
    }

    @Override
    public void aplicarResultadoRuleta(EventoResultadoRuleta e) {
        this.jugadores = (e.getJugadores() != null) ? new ArrayList<>(e.getJugadores()) : new ArrayList<>();
        this.cartaCima = e.getCartaEnCima();
        this.idJugadorTurnoActual = e.getIdJugadorTurnoActual();
        this.ultimaJugadaValida = true;
        notificar();
    }

    @Override
    public void aplicarResultadoGrito(EventoResultadoGrito e) {
        if (e.getEstadoJugadores() != null) {
            this.jugadores = new ArrayList<>(e.getEstadoJugadores());
        }
        notificar();
    }

    @Override
    public void aplicarGanador(EventoAnuciarGanador e) {
        if (e.getGanador() != null) {
            this.ganador = e.getGanador().getNombre();
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
