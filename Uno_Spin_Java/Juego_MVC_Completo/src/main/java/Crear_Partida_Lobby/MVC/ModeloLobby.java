package Crear_Partida_Lobby.MVC;

import Crear_Partida_Lobby.Interfaces.IModeloLobbyDatos;
import Crear_Partida_Lobby.Interfaces.ObservadorLobby;
import dtos.AceptacionDTO;
import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Modelo del MVC del lobby. Su estado solo se modifica via los metodos
 * aplicar*, que reciben unicamente DTOs y primitivos para mantener el
 * MVC desacoplado de la capa de eventos / red. Los aplicadores en
 * EventoTraductor se encargan de traducir Evento -> aplicar*.
 */
public class ModeloLobby implements IModeloLobbyDatos {

    private final List<ObservadorLobby> observadores = new ArrayList<>();
    private final String idJugadorLocal;

    private Estado estado = Estado.ACCESO;
    private String idPartida;
    private String idHost;
    private final List<JugadorDTO> jugadores = new ArrayList<>();
    private String idJugadorSolicitante;
    private String nombreSolicitante;
    private final List<AceptacionDTO> aceptaciones = new ArrayList<>();
    private List<JugadorDTO> jugadoresIniciales;
    private CartaDTO descarteInicial;
    private String idJugadorTurnoInicial;
    private String mensajeError;

    public ModeloLobby(String idJugadorLocal) {
        this.idJugadorLocal = idJugadorLocal;
    }

    @Override
    public void registrarObservador(ObservadorLobby o) {
        if (o != null && !observadores.contains(o)) observadores.add(o);
    }

    private void notificar() {
        for (ObservadorLobby o : observadores) o.notificarCambioLobby(this);
    }

    public void marcarAcceso() {
        this.estado = Estado.ACCESO;
        notificar();
    }

    public void marcarAbandono() {
        this.estado = Estado.ABANDONADO;
        this.idPartida = null;
        this.idHost = null;
        this.jugadores.clear();
        this.aceptaciones.clear();
        this.idJugadorSolicitante = null;
        this.nombreSolicitante = null;
        notificar();
    }

    public void aplicarLobbyActualizado(String idPartida, String idHost, List<JugadorDTO> jugadores) {
        this.idPartida = idPartida;
        this.idHost = idHost;
        this.jugadores.clear();
        if (jugadores != null) this.jugadores.addAll(jugadores);
        if (this.estado != Estado.SOLICITANDO_INICIO || this.idJugadorSolicitante == null) {
            this.estado = Estado.EN_LOBBY;
        }
        notificar();
    }

    public void aplicarSolicitudInicio(String idJugadorSolicitante, String nombreSolicitante,
                                       List<AceptacionDTO> aceptaciones) {
        this.estado = Estado.SOLICITANDO_INICIO;
        this.idJugadorSolicitante = idJugadorSolicitante;
        this.nombreSolicitante = nombreSolicitante;
        this.aceptaciones.clear();
        if (aceptaciones != null) this.aceptaciones.addAll(aceptaciones);
        notificar();
    }

    public void aplicarEstadoAceptacion(List<AceptacionDTO> aceptaciones) {
        this.aceptaciones.clear();
        if (aceptaciones != null) this.aceptaciones.addAll(aceptaciones);
        notificar();
    }

    public void aplicarPartidaIniciada(String idPartida, List<JugadorDTO> jugadores,
                                       CartaDTO descarteInicial, String idJugadorTurnoActual) {
        this.estado = Estado.INICIADA;
        this.idPartida = idPartida;
        this.jugadoresIniciales = jugadores;
        this.descarteInicial = descarteInicial;
        this.idJugadorTurnoInicial = idJugadorTurnoActual;
        notificar();
    }

    // === Lectura ===

    @Override public String getIdJugadorLocal() { return idJugadorLocal; }
    @Override public String getIdPartida() { return idPartida; }
    @Override public String getIdHost() { return idHost; }
    @Override public boolean isSoyHost() { return idHost != null && idHost.equals(idJugadorLocal); }
    @Override public List<JugadorDTO> getJugadores() { return Collections.unmodifiableList(jugadores); }
    @Override public Estado getEstado() { return estado; }
    @Override public String getNombreSolicitante() { return nombreSolicitante; }
    @Override public String getIdJugadorSolicitante() { return idJugadorSolicitante; }
    @Override public List<AceptacionDTO> getAceptaciones() { return Collections.unmodifiableList(aceptaciones); }
    @Override public List<JugadorDTO> getJugadoresIniciales() { return jugadoresIniciales; }
    @Override public CartaDTO getDescarteInicial() { return descarteInicial; }
    @Override public String getIdJugadorTurnoInicial() { return idJugadorTurnoInicial; }
    @Override public String getMensajeError() { return mensajeError; }
}
