/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachadas;

import entidades.Carta;
import entidades.CartaComodin;
import entidades.CartaAccion;
import entidades.Jugador;
import entidades.Tablero;
import java.awt.Color;
import java.util.List;

/**
 * 
 * @author lagar
 */
public class FachadaJuego implements FachadaDominio {
    private Tablero tablero;

    @Override
    public void inyectarTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    @Override
    public void abandonarPartida(Jugador jugador) {
        if (tablero != null) {
            tablero.eliminarJugador(jugador);
        }
    }

    @Override
    public Jugador verificarGanador() {
        if (tablero != null && tablero.getJugadores().size() == 1) {
            return tablero.getJugadores().get(0);
        }
        return null;
    }

    @Override
    public boolean validarYPlay(Carta carta) {
        if (tablero == null) return false;
        if (tablero.getDescarte().validarJugada(carta)) {
            tablero.getJugadorActual().tirarCarta(carta);
            tablero.getDescarte().recibirCarta(carta);
            procesarEfectos(carta);
            return true;
        }
        return false;
    }

    private void procesarEfectos(Carta carta) {
        String simbolo = carta.getSimbolo(); 
        if (carta instanceof CartaAccion) {
            switch (simbolo) {
                case "+2":
                    aplicarCastigoDirecto(2);
                    break;
                case "REV":
                    tablero.cambiarSentido();
                    break;
                case "PRO":
                    tablero.siguienteTurno();
                    break;
                default:
                    break;
            }
        } 
        else if (carta instanceof CartaComodin) {
            if (simbolo.equals("+4")) {
                aplicarCastigoDirecto(4);
            }
        }
    }

    private void aplicarCastigoDirecto(int cantidad) {
        tablero.siguienteTurno();
        Jugador victima = tablero.getJugadorActual();
        
        for (int i = 0; i < cantidad; i++) {
            Carta c = tablero.getMazo().tomarUnaCarta();
            if (c != null) {
                victima.agregarCarta(c);
            }
        }
        System.out.println("Efecto: " + victima.getNombre() + " roba " + cantidad + " cartas y pierde su turno.");
    }

    @Override
    public void aplicarEfectoCarta(Carta carta, String nombreColorElegido) {
        if (carta instanceof CartaComodin && nombreColorElegido != null) {
            carta.setColorNombre(nombreColorElegido);
            tablero.getDescarte().setColorActivo(nombreColorElegido);
        }
    }

    @Override
    public void robarCarta() {
        if (tablero == null) return;
        
        Carta c = tablero.getMazo().tomarUnaCarta();
        if (c != null) {
            tablero.getJugadorActual().agregarCarta(c);
        }
    }

    @Override
    public void pasarTurno() {
        if (tablero != null) {
            tablero.siguienteTurno();
        }
    }

    @Override
    public Tablero getTablero() {
        return this.tablero;
    }

    @Override
    public void procesarSeleccion(Color color, String nombre) {
        if (color == null || nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("Seleccion de color invalida");
        }
        System.out.println("Logica de dominio: Color validado -> " + nombre);
    }

    @Override
    public void inicializarPartida(List<Jugador> jugadores, int rangoInicio, int rangoFinal, 
                                   boolean masDos, boolean prohibido, boolean reversa, 
                                   boolean masCuatro, boolean cambioColor) {
        
        if (jugadores == null || jugadores.size() < 2 || jugadores.size() > 4) {
            throw new IllegalArgumentException("La partida debe tener entre 2 y 4 jugadores.");
        }
        this.tablero = new Tablero(jugadores, rangoInicio, rangoFinal, 
                                   masDos, prohibido, reversa, 
                                   masCuatro, cambioColor);
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < 7; i++) {
                Carta c = tablero.getMazo().tomarUnaCarta();
                if (c != null) {
                    jugador.agregarCarta(c);
                }
            }
        }
        System.out.println("Partida inicializada con " + jugadores.size() + " jugadores.");
    }
}