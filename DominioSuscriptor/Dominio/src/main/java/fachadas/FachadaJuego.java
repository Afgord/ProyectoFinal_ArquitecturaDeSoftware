/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoGritoDTO;
import dtos.ResultadoJugadaDTO;
import dtos.ResultadoIniciarPartidaDTO;
import dtos.ResultadoUnirseDTO;
import entidades.Tablero;

public class FachadaJuego implements FachadaDominio {
    private Tablero tablero;

    public FachadaJuego(Tablero tablero) {
        this.tablero = tablero;
    }

    @Override
    public ResultadoJugadaDTO validarYPlay(String idJugador, CartaDTO carta) {
        System.out.println("[Fachada] Procesando jugada del jugador " + idJugador + "...");
        return tablero.ejecutarJugada(idJugador, carta);
    }

    @Override
    public ResultadoJugadaDTO robarCarta(String idJugador) {
        System.out.println("[Fachada] Procesando robo del jugador " + idJugador + "...");
        return tablero.robarYPasar(idJugador);
    }

    @Override
    public ResultadoJugadaDTO pasarTurno(String idJugador) {
        System.out.println("[Fachada] Procesando pasar turno del jugador " + idJugador + "...");
        return tablero.pasarTurno(idJugador);
    }
    
    @Override
    public ResultadoGritoDTO gritarUno(JugadorDTO datosGrito) {
        System.out.println("[Fachada] Procesando grito de UNO...");
        return tablero.procesarGritoUno(datosGrito);
    }

    @Override
    public Object unirseAPartida(JugadorDTO jugadorDTO) {
        System.out.println("[Fachada] Procesando unirse a partida...");
        return tablero.unirseAPartida(jugadorDTO);
    }

    @Override
    public ResultadoIniciarPartidaDTO iniciarPartida(String idSolicitante) {
        System.out.println("[Fachada] Procesando solicitud de inicio de partida...");

        boolean jugadorValido = tablero.getJugadores().stream()
            .anyMatch(j -> j.getIdJugador().equals(idSolicitante));

        if (!jugadorValido) {
            System.out.println("[Fachada] Rechazo: solicitante desconocido " + idSolicitante);
            return new ResultadoIniciarPartidaDTO(
                false,
                entidades.TipoEvento.INICIO_RECHAZADO,
                null,
                null,
                null
            );
        }

        return tablero.registrarListoParaIniciar(idSolicitante);
    }
}