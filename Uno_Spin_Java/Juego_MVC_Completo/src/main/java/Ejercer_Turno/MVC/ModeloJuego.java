/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Dominio.TableroDTO;
import Ejercer_Turno.Dominio.CartaDTO;
import Ejercer_Turno.Dominio.Mazo;
import Ejercer_Turno.Dominio.Tablero;
import Ejercer_Turno.Dominio.Descarte;
import Ejercer_Turno.Dominio.Jugador;
import Ejercer_Turno.Dominio.JugadorDTO;
import Ejercer_Turno.Dominio.MazoDTO;
import Ejercer_Turno.Dominio.Carta;
import Ejercer_Turno.Dominio.FachadaJuego;
import Ejercer_Turno.Dominio.FachadaDominio;
import Ejercer_Turno.Interfaces.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Luis Rafael
 */
public class ModeloJuego implements IModeloAcciones, IModeloDatos {

    private final FachadaDominio fachada;
    private final List<Observador> observadores = new ArrayList<>();
    private boolean ultimaJugadaValida = true;

    public ModeloJuego(List<Jugador> jugadores, Mazo mazo, Descarte descarte, Tablero tablero) {
        this.fachada = new FachadaJuego();
        this.fachada.inyectarTablero(tablero);
    }

    @Override
    public void tirarCarta(CartaDTO cartaDTO) {
        Carta cartaReal = buscarCartaReal(cartaDTO);
        if (cartaReal != null && fachada.validarYPlay(cartaReal)) {
            ultimaJugadaValida = true;
            if (fachada.getTablero().getJugadorActual().getNumCartas() != 0) {
                fachada.pasarTurno();
            }
        } else {
            ultimaJugadaValida = false;
            System.out.println("Jugada denegada por el Dominio para: " + (cartaReal != null ? cartaReal.getSimbolo() : "Nula"));
        }
        notificarObservadores();
    }

    @Override
    public void tirarCartaNegra(CartaDTO cartaDTO, Color nuevoColor, String nombreColor) {
        Carta cartaReal = buscarCartaReal(cartaDTO);
        if (cartaReal != null && fachada.validarYPlay(cartaReal)) {
            ultimaJugadaValida = true;
            fachada.aplicarEfectoCarta(cartaReal, nuevoColor);
            cartaReal.setColorNombre(nombreColor); 
            fachada.pasarTurno();
        } else {
            ultimaJugadaValida = false;
        }
        notificarObservadores();
    }

    @Override
    public void robarCarta() {
        ultimaJugadaValida = true;
        if (fachada.getAcumulacionCastigo() > 0) {
            aplicarCastigo();
        } else {
            fachada.robarCarta();
            notificarObservadores();
        }
    }

    @Override
    public void aplicarCastigo() {
        int cantidad = fachada.getAcumulacionCastigo();
        fachada.limpiarCastigo();
        for (int i = 0; i < cantidad; i++) {
            fachada.robarCarta();
        }
        fachada.pasarTurno();
        notificarObservadores();
    }

    @Override
    public TableroDTO getTableroDTO() {
        Tablero t = fachada.getTablero();
        Color colorCima = (t.getDescarte().getCartaCima() != null) 
                          ? t.getDescarte().getCartaCima().getColorExterno() 
                          : Color.BLACK;
                          
        return new TableroDTO(
            colorCima, 
            t.isSentidoReloj(), 
            t.getJugadorActual().getNombre()
        );
    }

    @Override
    public MazoDTO getMazoDTO() {
        return new MazoDTO(fachada.getTablero().getMazo().getCantidadCartas());
    }

    @Override
    public CartaDTO getCartaDescarteDTO() {
        Carta c = fachada.getTablero().getDescarte().getCartaCima();
        return (c == null) ? null : convertirACartaDTO(c);
    }

    @Override
    public List<JugadorDTO> getJugadoresDTO() {
        List<JugadorDTO> lista = new ArrayList<>();
        Jugador actual = fachada.getTablero().getJugadorActual();

        for (Jugador j : fachada.getTablero().getJugadores()) {
            boolean esTurno = j.equals(actual);
            List<CartaDTO> cartasMano = new ArrayList<>();

            for (Carta c : j.getCartasModelo()) {
                cartasMano.add(convertirACartaDTO(c));
            }

            lista.add(new JugadorDTO(
                j.getUrlAvatar(),
                j.getNombre(), 
                j.getNumCartas(), 
                esTurno, 
                cartasMano
            ));
        }
        return lista;
    }

    @Override
    public boolean isUltimaJugadaValida() {
        return ultimaJugadaValida;
    }

    @Override
    public Color[] obtenerColoresConfigurados() {
        Mazo m = fachada.getTablero().getMazo();
        return new Color[]{m.getcAzul(), m.getcRojo(), m.getcAmarillo(), m.getcVerde()};
    }

    @Override
    public void registrarObservador(Observador o) {
        observadores.add(o);
    }

    public void notificarObservadores() {
        for (Observador o : observadores) {
            o.notificarCambio(this);
        }
    }

    private CartaDTO convertirACartaDTO(Carta c) {
        return new CartaDTO(
            c.getRutaImagen(), 
            c.getColorExterno(), 
            c.getSimbolo(), 
            c.esComodin() 
        );
    }

    private Carta buscarCartaReal(CartaDTO dto) {
        List<Carta> mano = fachada.getTablero().getJugadorActual().getCartasModelo();
        for (Carta c : mano) {
            if (c.getRutaImagen().equals(dto.getId()) && 
                c.getSimbolo().equals(dto.getSimbolo()) &&
                c.getColorExterno().equals(dto.getColor())) {
                return c;
            }
        }
        return null;
    }
}