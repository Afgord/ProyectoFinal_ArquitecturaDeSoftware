package Crear_Partida_Lobby.Interfaces;

/**
 * Contrato Outbound del MVC del lobby.
 *
 * Define las intenciones del usuario (registrarse, crear/unirse a un
 * lobby, solicitar iniciar partida, responder a una solicitud,
 * abandonar) que el EventoTraductor convierte en eventos serializables
 * y publica al broker.
 */
public interface IEventosLobby {
    void emitirRegistroConexion(String nombre, String urlAvatar, String ip, int puerto);
    void emitirCrearPartida(String nombre, String urlAvatar);
    void emitirUnirsePartida(String idPartida, String nombre, String urlAvatar);
    void emitirSolicitarInicio();
    void emitirResponderSolicitud(boolean acepta);
    void emitirAbandonarLobby();
    void emitirBajaConexion();
}
