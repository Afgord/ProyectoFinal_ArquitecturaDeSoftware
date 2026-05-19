/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;
import dtos.ConfiguracionPartidaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoConfiguracionDTO;
import entidades.Carta;
import entidades.ConfiguracionPartida;
import entidades.Descarte;
import entidades.Jugador;
import entidades.Mano;
import entidades.Mazo;
import entidades.Ruleta;
import entidades.Tablero;
import java.util.List;

public class FachadaJuego implements FachadaDominio {

    private Tablero tablero;
    private ConfiguracionPartida configuracionPartida;

    public FachadaJuego(Tablero tablero) {
        this.tablero = tablero;
    }

    @Override
    public Object validarYPlay(CartaDTO carta) {
        System.out.println("[Fachada] Procesando jugada...");
        return tablero.ejecutarJugada(carta);
    }

    @Override
    public Object robarCarta() {
        System.out.println("[Fachada] Procesando robo...");
        return tablero.robarYPasar();
    }

    @Override
    public Object pasarTurno() {
        System.out.println("[Fachada] Procesando pasar turno...");
        return tablero.pasarTurno();
    }

    @Override
    public Object gritarUno(JugadorDTO datosGrito) {
        System.out.println("[Fachada] Procesando grito de UNO...");
        return tablero.procesarGritoUno(datosGrito);
    }

    @Override
    public ResultadoConfiguracionDTO configurarPartida(ConfiguracionPartidaDTO configuracionDTO) {
        System.out.println("[Fachada] Procesando configuración de partida...");

        ConfiguracionPartida configuracion = new ConfiguracionPartida(configuracionDTO);
        String motivoInvalidez = configuracion.obtenerMotivoInvalidez();

        if (motivoInvalidez != null) {
            System.out.println("[Fachada] Configuración rechazada: " + motivoInvalidez);
            return new ResultadoConfiguracionDTO(false, motivoInvalidez);
        }

        this.configuracionPartida = configuracion;

        Mazo mazoConfigurado = new Mazo(
                configuracion.getRangoMinimo(),
                configuracion.getRangoMaximo(),
                configuracion.getNumeroCartasAccion(),
                configuracion.getNumeroComodines()
        );

        Carta cartaInicial = mazoConfigurado.sacarCartaInicialValida();
        Descarte descarte = new Descarte(cartaInicial);
        Ruleta ruleta = new Ruleta();

        List<Jugador> jugadores = tablero.getJugadores();

        for (Jugador jugador : jugadores) {
            jugador.setMano(new Mano());

            for (int i = 0; i < 7; i++) {
                Carta carta = mazoConfigurado.tomarUnaCarta();
                if (carta != null) {
                    jugador.agregarCarta(carta);
                }
            }
        }

        this.tablero = new Tablero(mazoConfigurado, descarte, jugadores, ruleta);

        System.out.println("[Fachada] Partida configurada correctamente.");
        return new ResultadoConfiguracionDTO(true, "Partida configurada correctamente.");
    }
}
