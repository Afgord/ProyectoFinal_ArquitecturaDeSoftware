/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import DTOs.*;
import Entidades.*;
import Fachadas.FachadaDominio;
import Ejercer_Turno.Interfaces.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ModeloJuego implements IModeloDatos, IModeloAcciones {

    private final FachadaDominio fachada;
    private final List<Observador> observadores = new ArrayList<>();
    private boolean ultimaJugadaValida = true;

    public ModeloJuego(FachadaDominio fachada) {
        this.fachada = fachada;
    }

    @Override
    public void registrarObservador(Observador o) {
        observadores.add(o);
    }

    private void notificar() {
        for (Observador o : observadores) {
            o.notificarCambio(this);
        }
    }

    @Override
    public void tirarCarta(CartaDTO cartaDTO) {
        Carta cartaReal = buscarCartaEnMano(cartaDTO);
        if (cartaReal != null) {
            ultimaJugadaValida = fachada.validarYPlay(cartaReal);
            if (ultimaJugadaValida) {
                fachada.pasarTurno();
            }
            notificar();
        }
    }

    @Override
    public void tirarCartaNegra(CartaDTO cartaDTO, Color nuevoColor, String nombreColor) {
        Carta cartaReal = buscarCartaEnMano(cartaDTO);
        if (cartaReal != null) {
            ultimaJugadaValida = fachada.validarYPlay(cartaReal);
            if (ultimaJugadaValida) {
                fachada.aplicarEfectoCarta(cartaReal, nuevoColor);
                fachada.pasarTurno();
            }
            notificar();
        }
    }

    @Override
    public void robarCarta() {
        fachada.robarCarta();
        fachada.pasarTurno();
        this.ultimaJugadaValida = true;
        notificar();
    }

    @Override
    public void aplicarCastigo() {
        int cantidad = fachada.getAcumulacionCastigo();
        for (int i = 0; i < cantidad; i++) {
            fachada.robarCarta();
        }
        fachada.limpiarCastigo();
        fachada.pasarTurno();
        notificar();
    }

    private Carta buscarCartaEnMano(CartaDTO dto) {
        for (Carta c : fachada.getTablero().getJugadorActual().getCartasModelo()) {
            if (c.getRutaImagen().equals(dto.getId()) && c.getSimbolo().equals(dto.getSimbolo())) {
                return c;
            }
        }
        return null;
    }

    @Override
    public TableroDTO getTableroDTO() {
        Tablero t = fachada.getTablero();
        return new TableroDTO(t.getDescarte().getCartaCima().getColorExterno(), 
                              t.isSentidoReloj(), 
                              t.getJugadorActual().getNombre());
    }

    @Override
    public MazoDTO getMazoDTO() {
        return new MazoDTO(fachada.getTablero().getMazo().getCantidadCartas());
    }

    @Override
    public CartaDTO getCartaDescarteDTO() {
        Carta c = fachada.getTablero().getDescarte().getCartaCima();
        return new CartaDTO(c.getRutaImagen(), c.getColorExterno(), c.getSimbolo(), c.esComodin());
    }

    @Override
    public List<JugadorDTO> getJugadoresDTO() {
        List<JugadorDTO> lista = new ArrayList<>();
        Tablero t = fachada.getTablero();
        for (Jugador j : t.getJugadores()) {
            boolean esTurno = (j == t.getJugadorActual());
            List<CartaDTO> cartasDto = new ArrayList<>();
            if (esTurno) {
                for (Carta c : j.getCartasModelo()) {
                    cartasDto.add(new CartaDTO(c.getRutaImagen(), c.getColorExterno(), c.getSimbolo(), c.esComodin()));
                }
            }
            lista.add(new JugadorDTO(j.getUrlAvatar(), j.getNombre(), j.getNumCartas(), esTurno, cartasDto));
        }
        return lista;
    }

    @Override
    public boolean isUltimaJugadaValida() { return ultimaJugadaValida; }

    @Override
    public Color[] obtenerColoresConfigurados() {
        Mazo m = fachada.getTablero().getMazo();
        return new Color[]{m.getcAzul(), m.getcRojo(), m.getcAmarillo(), m.getcVerde()};
    }
}