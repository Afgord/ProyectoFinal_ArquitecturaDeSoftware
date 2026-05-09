package Crear_Partida_Lobby.MVC;

import Crear_Partida_Lobby.Interfaces.IEventosLobby;

/**
 * Controlador del MVC del lobby. Recibe acciones del usuario desde la
 * vista (FrameAccesoLobby, FrameLobby, DialogSolicitud) y delega en
 * IEventosLobby para emitir los eventos correspondientes al broker.
 */
public class ControlLobby {

    private final IEventosLobby eventos;
    private final ModeloLobby modelo;
    private String nombre;
    private String urlAvatar;

    public ControlLobby(IEventosLobby eventos, ModeloLobby modelo) {
        this.eventos = eventos;
        this.modelo = modelo;
    }

    public void setIdentidad(String nombre, String urlAvatar) {
        this.nombre = nombre;
        this.urlAvatar = urlAvatar;
    }

    public String getNombre() { return nombre; }
    public String getUrlAvatar() { return urlAvatar; }
    public ModeloLobby getModelo() { return modelo; }

    public void solicitarCrearPartida() {
        eventos.emitirCrearPartida(nombre, urlAvatar);
    }

    public void solicitarUnirsePartida(String idPartida) {
        eventos.emitirUnirsePartida(idPartida, nombre, urlAvatar);
    }

    public void solicitarIniciarPartida() {
        eventos.emitirSolicitarInicio();
    }

    public void responderSolicitud(boolean acepta) {
        eventos.emitirResponderSolicitud(acepta);
    }

    public void abandonarLobby() {
        eventos.emitirAbandonarLobby();
        modelo.marcarAbandono();
    }
}
