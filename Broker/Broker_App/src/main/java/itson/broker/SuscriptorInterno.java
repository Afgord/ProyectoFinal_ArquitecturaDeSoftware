/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package itson.broker;

import entidades.*;
import fachadas.FachadaDominio;
import org.eventos.comun.Evento;
import org.eventos.tipos.*;
import org.dto.ConfiguracionDTO;
import org.decodificador.Deserializador; 
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class SuscriptorInterno {
    private final FachadaDominio dominio;
    private final List<Jugador> listaEsperaJugadores;
    private final Deserializador deserializador;

    public SuscriptorInterno(FachadaDominio dominio) {
        this.dominio = dominio;
        this.listaEsperaJugadores = new ArrayList<>();
        this.deserializador = new Deserializador();
    }

    public void atenderEventoDesdeRed(byte[] datos) {
        Evento evento = deserializador.bytesAObjeto(datos, Evento.class);
        if (evento != null) {
            atenderEvento(evento);
        }
    }

    public void atenderEvento(Evento evento) {
        if (evento instanceof EventoControl ec) {
            manejarControl(ec);
        } else if (evento instanceof EventoJugada ej) {
            manejarJugada(ej);
        }
    }

    private void manejarControl(EventoControl e) {
        switch (e.getAccion().toUpperCase()) {
            case "UNIR_JUGADOR" -> {
                Jugador nuevo = new Jugador(e.getMensaje(), "ruta/default.png");
                listaEsperaJugadores.add(nuevo);
                System.out.println("Jugador unido: " + nuevo.getNombre());
            }
            case "INICIAR_PARTIDA" -> {
                if (e.getConfiguracion() instanceof ConfiguracionDTO dto) {
                    Tablero tablero = new Tablero(
                        new ArrayList<>(listaEsperaJugadores), 
                        dto.getRangoInicio(), dto.getRangoFinal(),
                        dto.isTieneMasDos(), dto.isTieneProhibido(),
                        dto.isTieneReversa(), dto.isTieneMasCuatro(),
                        dto.isTieneCambioColor()
                    );
                    dominio.inyectarTablero(tablero);
                    System.out.println("Partida Inicializada en el Dominio.");
                }
            }
            case "ROBAR" -> {
                dominio.robarCarta();
                verificarGanador();
            }
            case "PASAR_TURNO" -> dominio.pasarTurno();
        }
    }

    private void manejarJugada(EventoJugada e) {
        Carta carta = (Carta) e.getCartaObjeto();
        if (dominio.validarYPlay(carta)) {
            if (carta.esComodin() && e.getColorNombre() != null) {
                dominio.aplicarEfectoCarta(carta, e.getColorNombre());
            }
            verificarGanador();
        }
    }

    private void verificarGanador() {
        Jugador posibleGanador = ((fachadas.FachadaJuego) dominio).verificarGanador();
        if (posibleGanador != null) {
            System.out.println("TENEMOS UN GANADOR: " + posibleGanador.getNombre());
        }
    }
}