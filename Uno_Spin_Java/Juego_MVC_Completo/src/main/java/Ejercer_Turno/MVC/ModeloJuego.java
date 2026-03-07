/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import Ejercer_Turno.Dominio.*;
import Ejercer_Turno.Interfaces.ContextoEvento;
import Ejercer_Turno.Interfaces.Observador; 
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ModeloJuego {

    private final Tablero tablero;
    private final Mazo mazo;
    private final Descarte descarte;
    private final List<Jugador> jugadores;
    private final List<Observador> observadores = new ArrayList<>();

    public ModeloJuego(List<Jugador> jugadores, Mazo mazo, Descarte descarte, Tablero tablero) {
        this.jugadores = jugadores;
        this.mazo = mazo;
        this.descarte = descarte;
        this.tablero = tablero;
    }

    public void registrarObservador(Observador o) {
        observadores.add(o);
    }

    public void notificarObservadores(ContextoEvento evento) {
        for (Observador o : observadores) {
            o.notificarCambio(evento);
        }
    }
    public void notificarError() {
        notificarObservadores(ContextoEvento.ALERTA_SONIDO_ERROR);
    }
    
    public void tirarCarta(Carta carta) {
        Jugador actual = tablero.getJugadorActual();

        if (descarte.validarJugada(carta)) {
            actual.tirarCarta(carta);
            descarte.recibirCarta(carta);

            notificarObservadores(ContextoEvento.ALERTA_SONIDO_TIRAR);
            notificarObservadores(ContextoEvento.DESCARTE_ACTUALIZADO);
            notificarObservadores(ContextoEvento.MANO_JUGADOR_ACTUALIZADO);

            verificarEstadoVictoria();
        } else {
            notificarObservadores(ContextoEvento.ALERTA_SONIDO_ERROR);
        }
    }

    public void tirarCartaNegra(Carta carta, Color colorElegido, String nombreColor) {
        Jugador actual = tablero.getJugadorActual();
        actual.tirarCarta(carta);
        
        carta.setColorExterno(colorElegido);
        descarte.setColorActivo(nombreColor); 
        descarte.recibirCarta(carta);

        notificarObservadores(ContextoEvento.ALERTA_SONIDO_TIRAR);
        notificarObservadores(ContextoEvento.DESCARTE_ACTUALIZADO);
        notificarObservadores(ContextoEvento.MANO_JUGADOR_ACTUALIZADO);

        verificarEstadoVictoria();
    }

    public void robarCarta() {
        if (!mazo.estaVacio()) {
            Carta robada = mazo.tomarUnaCarta();
            tablero.getJugadorActual().agregarCarta(robada);

            notificarObservadores(ContextoEvento.ALERTA_SONIDO_JALAR);
            notificarObservadores(ContextoEvento.MAZO_ACTUALIZADO);
            notificarObservadores(ContextoEvento.MANO_JUGADOR_ACTUALIZADO);
        }
    }

    public void gritarUno() {
        notificarObservadores(ContextoEvento.ALERTA_SONIDO_UNO);
    }

    public void pasarTurno() {
        tablero.siguienteTurno();
        notificarObservadores(ContextoEvento.TURNO_CAMBIADO);
    }

    private void verificarEstadoVictoria() {
        if (tablero.getJugadorActual().getNumCartas() == 0) {
            notificarObservadores(ContextoEvento.FIN_JUEGO);
        } else {
            pasarTurno();
        }
    }

    public Tablero getTablero() { return tablero; }
    public Mazo getMazo() { return mazo; }
    public Descarte getDescarte() { return descarte; }
    public List<Jugador> getJugadores() { return jugadores; }
}