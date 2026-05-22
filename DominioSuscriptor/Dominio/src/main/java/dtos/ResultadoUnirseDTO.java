/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import entidades.TipoEvento;
import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class ResultadoUnirseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final boolean exito;
    private final TipoEvento eventoTipo;
    private final JugadorDTO jugadorUnido;
    private final List<JugadorDTO> jugadoresEnSala;
    private final CartaDTO cartaCima;
    private final String idJugadorTurnoActual;

    public ResultadoUnirseDTO(
            boolean exito,
            TipoEvento eventoTipo,
            JugadorDTO jugadorUnido,
            List<JugadorDTO> jugadoresEnSala) {
        this(exito, eventoTipo, jugadorUnido, jugadoresEnSala, null, null);
    }

    public ResultadoUnirseDTO(
            boolean exito,
            TipoEvento eventoTipo,
            JugadorDTO jugadorUnido,
            List<JugadorDTO> jugadoresEnSala,
            CartaDTO cartaCima,
            String idJugadorTurnoActual) {

        this.exito = exito;
        this.eventoTipo = eventoTipo;
        this.jugadorUnido = jugadorUnido;
        this.jugadoresEnSala = jugadoresEnSala;
        this.cartaCima = cartaCima;
        this.idJugadorTurnoActual = idJugadorTurnoActual;
    }

    public boolean isExito() {
        return exito;
    }

    public TipoEvento getEventoTipo() {
        return eventoTipo;
    }

    public JugadorDTO getJugadorUnido() {
        return jugadorUnido;
    }

    public List<JugadorDTO> getJugadoresEnSala() {
        return jugadoresEnSala;
    }

    public CartaDTO getCartaCima() {
        return cartaCima;
    }

    public String getIdJugadorTurnoActual() {
        return idJugadorTurnoActual;
    }
}