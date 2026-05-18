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

    public ResultadoUnirseDTO(
            boolean exito,
            TipoEvento eventoTipo,
            JugadorDTO jugadorUnido,
            List<JugadorDTO> jugadoresEnSala) {

        this.exito = exito;
        this.eventoTipo = eventoTipo;
        this.jugadorUnido = jugadorUnido;
        this.jugadoresEnSala = jugadoresEnSala;
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
}