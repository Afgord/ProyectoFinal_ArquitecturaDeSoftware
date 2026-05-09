/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoLobbyDTO;
import dtos.ResultadoSolicitudDTO;

/**
 * 
 * @author lagar
 */
public interface FachadaDominio {
    public Object robarCarta();
    public Object validarYPlay(CartaDTO carta);
    public Object pasarTurno();
    public Object gritarUno(JugadorDTO datosGrito);

    // Caso de uso: Iniciar Partida (lobby + solicitud + arranque).
    public ResultadoLobbyDTO crearPartida(JugadorDTO host);
    public ResultadoLobbyDTO unirsePartida(String idPartida, JugadorDTO jugador);
    public ResultadoLobbyDTO abandonarLobby(String idJugador);
    public ResultadoSolicitudDTO solicitarInicio(String idJugadorSolicitante);
    public ResultadoSolicitudDTO responderSolicitud(String idJugador, boolean acepta);
}
