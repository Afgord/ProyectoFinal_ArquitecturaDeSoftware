package Iniciar_Partida.MVC;

import Iniciar_Partida.Interfaces.IModeloLobby;
import Iniciar_Partida.Interfaces.IReceptorEstadoLobby;
import Iniciar_Partida.Interfaces.ObservadorLobby;
import dtos.JugadorDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eventos.ejercer_turno.EventoFallo;
import org.eventos.ejercer_turno.EventoListosIniciar;
import org.eventos.ejercer_turno.EventoUnirseExitoso;

public class ModeloLobby implements IModeloLobby, IReceptorEstadoLobby {

    private static final int CAPACIDAD_MAXIMA = 4;
    private static final int JUGADORES_MINIMOS = 2;

    private final List<ObservadorLobby> observadores = new ArrayList<>();
    private final String idJugadorLocal;

    private List<JugadorDTO> jugadoresEnSala = new ArrayList<>();
    private Set<String> jugadoresListos = new HashSet<>();
    private boolean partidaIniciada;
    private String mensajeEstado = "Conectando al lobby...";

    public ModeloLobby(String idJugadorLocal) {
        this.idJugadorLocal = idJugadorLocal;
    }

    @Override
    public void registrarObservador(ObservadorLobby o) {
        if (o != null && !observadores.contains(o)) {
            observadores.add(o);
        }
    }

    @Override
    public void aplicarUnirseExitoso(EventoUnirseExitoso e) {
        if (e.getJugadoresEnSala() != null) {
            this.jugadoresEnSala = new ArrayList<>(e.getJugadoresEnSala());
        }
        int total = jugadoresEnSala.size();
        if (total >= CAPACIDAD_MAXIMA) {
            this.mensajeEstado = "Sala completa. Iniciando partida...";
        } else if (total >= JUGADORES_MINIMOS) {
            this.mensajeEstado = total + "/" + CAPACIDAD_MAXIMA
                    + " conectados (mín. " + JUGADORES_MINIMOS
                    + "). Todos deben confirmar para iniciar.";
        } else {
            this.mensajeEstado = total + "/" + CAPACIDAD_MAXIMA
                    + " jugadores conectados (mín. " + JUGADORES_MINIMOS + " para iniciar)";
        }
        notificar();
    }

    @Override
    public void aplicarListosIniciar(EventoListosIniciar e) {
        if (e.getJugadoresListos() != null) {
            this.jugadoresListos = new HashSet<>(e.getJugadoresListos());
        }
        int total = e.getTotalJugadoresEnSala() > 0
                ? e.getTotalJugadoresEnSala()
                : jugadoresEnSala.size();
        int listos = jugadoresListos.size();
        this.mensajeEstado = listos + "/" + total + " listos para iniciar (todos deben confirmar)";
        notificar();
    }

    @Override
    public void aplicarPartidaIniciada() {
        this.partidaIniciada = true;
        this.mensajeEstado = "Partida iniciada";
        notificar();
    }

    @Override
    public void aplicarFallo(EventoFallo e) {
        this.mensajeEstado = "Error: " + e.getError();
        notificar();
    }

    private void notificar() {
        for (ObservadorLobby o : observadores) {
            o.notificarCambio(this);
        }
    }

    @Override
    public String getIdJugadorLocal() {
        return idJugadorLocal;
    }

    @Override
    public List<JugadorDTO> getJugadoresEnSala() {
        return Collections.unmodifiableList(jugadoresEnSala);
    }

    @Override
    public int getCapacidadMaxima() {
        return CAPACIDAD_MAXIMA;
    }

    @Override
    public int getJugadoresMinimos() {
        return JUGADORES_MINIMOS;
    }

    @Override
    public boolean isPartidaIniciada() {
        return partidaIniciada;
    }

    @Override
    public String getMensajeEstado() {
        return mensajeEstado;
    }

    @Override
    public Set<String> getJugadoresListos() {
        return Collections.unmodifiableSet(jugadoresListos);
    }

    @Override
    public boolean isJugadorLocalListo() {
        return idJugadorLocal != null && jugadoresListos.contains(idJugadorLocal);
    }

    @Override
    public boolean isJugadorListo(String id) {
        return id != null && jugadoresListos.contains(id);
    }
}
