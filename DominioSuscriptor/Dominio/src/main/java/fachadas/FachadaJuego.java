package fachadas;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoGritoDTO;
import dtos.ResultadoJugadaDTO;
import dtos.ResultadoIniciarPartidaDTO;
import dtos.ResultadoUnirseDTO;
import entidades.Partida;

public class FachadaJuego implements FachadaDominio {
    private final Partida partida;

    public FachadaJuego(Partida partida) {
        this.partida = partida;
    }

    @Override
    public ResultadoJugadaDTO validarYPlay(String idJugador, CartaDTO carta) {
        System.out.println("[Fachada] Procesando jugada del jugador " + idJugador + "...");
        return partida.getTablero().ejecutarJugada(idJugador, carta);
    }

    @Override
    public ResultadoJugadaDTO robarCarta(String idJugador) {
        System.out.println("[Fachada] Procesando robo del jugador " + idJugador + "...");
        return partida.getTablero().robarYPasar(idJugador);
    }

    @Override
    public ResultadoJugadaDTO pasarTurno(String idJugador) {
        System.out.println("[Fachada] Procesando pasar turno del jugador " + idJugador + "...");
        return partida.getTablero().pasarTurno(idJugador);
    }

    @Override
    public ResultadoGritoDTO gritarUno(JugadorDTO datosGrito) {
        System.out.println("[Fachada] Procesando grito de UNO...");
        return partida.getTablero().procesarGritoUno(datosGrito);
    }

    @Override
    public Object unirseAPartida(JugadorDTO jugadorDTO) {
        System.out.println("[Fachada] Procesando unirse a partida...");
        return partida.unirseAPartida(jugadorDTO);
    }

    @Override
    public ResultadoIniciarPartidaDTO iniciarPartida(String idSolicitante) {
        System.out.println("[Fachada] Procesando solicitud de inicio de partida...");
        return partida.registrarListoParaIniciar(idSolicitante);
    }
}
