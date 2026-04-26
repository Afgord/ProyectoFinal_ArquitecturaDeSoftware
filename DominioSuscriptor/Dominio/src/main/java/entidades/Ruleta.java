/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.List;
import java.util.Random;

public class Ruleta {
    private final Random random = new Random();
    private Tablero tablero;
    private String detalleUltimoEfecto = "";

    public TipoEvento girarYAplicar(Tablero tablero) {
        this.tablero = tablero;
        this.detalleUltimoEfecto = "";
        
        ResultadoRuleta[] resultados = ResultadoRuleta.values();
        ResultadoRuleta resultado = resultados[random.nextInt(resultados.length)];
        
        System.out.println("[Ruleta] ¡Girando...! Resultado: " + resultado);

        switch (resultado) {
            case CASI_UNO -> {
                aplicarCasiUno();
                return TipoEvento.CASI_UNO_APLICADO;
            }
            case DESCARTAR_NUMERO -> {
                aplicarDescartarNumero();
                return TipoEvento.CARTAS_DESCARTADAS;
            }
            case DESCARTAR_COLOR -> {
                aplicarDescartarColor();
                return TipoEvento.CARTAS_DESCARTADAS;
            }
            case ROBAR_HASTA_COLOR -> {
                aplicarRobarHastaColor();
                return TipoEvento.ROBO_HASTA_COLOR;
            }
            case GUERRA -> { 
                return prepararGuerra(); 
            }
            case MOSTRAR_MANO -> {
                aplicarMostrarMano();
                return TipoEvento.MANO_REVELADA;
            }
            case INTERCAMBIO_MANOS -> {
                aplicarIntercambioManos();
                return TipoEvento.MANOS_INTERCAMBIADAS;
            }
            case PUNTUACION_BAJA -> { 
                return prepararPuntuacionBaja(); 
            }
            default -> {
                tablero.siguienteTurno();
                return TipoEvento.CAMBIO_TURNO;
            }
        }
    }

    private TipoEvento prepararGuerra() {
        List<Jugador> jugadores = tablero.getJugadores();
        Jugador ganadorGuerra = null;
        Carta cartaGanadora = null;
        int valorMasAlto = -1;

        for (Jugador j : jugadores) {
            Carta masAlta = j.getMano().getCartasReales().stream()
                    .filter(Carta::esNumerica)
                    .max((c1, c2) -> Integer.compare(c1.getValor().ordinal(), c2.getValor().ordinal()))
                    .orElse(null);
            
            if (masAlta != null) {
                if (masAlta.getValor().ordinal() > valorMasAlto) {
                    valorMasAlto = masAlta.getValor().ordinal();
                    ganadorGuerra = j;
                    cartaGanadora = masAlta;
                }
            }
        }

        if (ganadorGuerra != null && cartaGanadora != null) {
            ganadorGuerra.tirarCarta(cartaGanadora); 
            tablero.siguienteTurno();
            this.detalleUltimoEfecto = ganadorGuerra.getNombre();
            return TipoEvento.GUERRA_CONCLUIDA;
        }

        tablero.siguienteTurno();
        return TipoEvento.GUERRA_SIN_GANADOR;
    }

    private TipoEvento prepararPuntuacionBaja() {
        Jugador ganador = tablero.getJugadores().stream()
                .min((j1, j2) -> Integer.compare(calcularPuntos(j1), calcularPuntos(j2)))
                .orElse(null);

        if (ganador != null) {
            this.detalleUltimoEfecto = ganador.getIdJugador();
            return TipoEvento.PUNTUACION_BAJA_WAIT;
        }
        
        tablero.siguienteTurno();
        return TipoEvento.CAMBIO_TURNO;
    }

    private void aplicarCasiUno() {
        Jugador victima = tablero.getJugadorActual();
        List<Carta> cartas = victima.getMano().getCartasReales();
        while (cartas.size() > 2) {
            Carta removida = cartas.remove(cartas.size() - 1);
            if (removida.esComodin()) {
                removida.setColor(Colores.NEGRO);
            }
        }
        tablero.siguienteTurno();
    }

    private void aplicarDescartarNumero() {
        Jugador victima = tablero.getJugadorActual();
        List<Carta> cartas = victima.getMano().getCartasReales();
        if (cartas.isEmpty()) {
            tablero.siguienteTurno();
            return;
        }
        Valor valorMasFrecuente = cartas.stream()
                .filter(Carta::esNumerica)
                .collect(java.util.stream.Collectors.groupingBy(Carta::getValor, java.util.stream.Collectors.counting()))
                .entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse(null);
        if (valorMasFrecuente != null) {
            cartas.removeIf(c -> c.getValor() == valorMasFrecuente);
        }
        tablero.siguienteTurno();
    }

    private void aplicarDescartarColor() {
        Jugador victima = tablero.getJugadorActual();
        List<Carta> cartas = victima.getMano().getCartasReales();
        if (cartas.isEmpty()) {
            tablero.siguienteTurno();
            return;
        }
        Colores colorMasFrecuente = cartas.stream()
                .filter(c -> c.getColor() != Colores.NEGRO)
                .collect(java.util.stream.Collectors.groupingBy(Carta::getColor, java.util.stream.Collectors.counting()))
                .entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse(null);
        if (colorMasFrecuente != null) {
            cartas.removeIf(c -> c.getColor() == colorMasFrecuente);
        }
        tablero.siguienteTurno();
    }

    private void aplicarRobarHastaColor() {
        Jugador victima = tablero.getJugadorActual();
        Mazo mazo = tablero.getMazo();
        Colores objetivo = random.nextBoolean() ? Colores.ROJO : Colores.AZUL;
        boolean encontrado = false;
        while (!encontrado && !mazo.estaVacio()) {
            Carta c = mazo.tomarUnaCarta();
            if (c != null) {
                victima.agregarCarta(c);
                if (c.getColor() == objetivo) {
                    encontrado = true;
                }
            }
        }
        tablero.siguienteTurno();
    }

    private void aplicarMostrarMano() {
        tablero.siguienteTurno();
    }

    private void aplicarIntercambioManos() {
        List<Jugador> jugadores = tablero.getJugadores();
        int numJugadores = jugadores.size();
        if (numJugadores < 2) {
            tablero.siguienteTurno();
            return;
        }
        Mano primeraMano = jugadores.get(0).getMano();
        if (tablero.isSentidoReloj()) {
            for (int i = 0; i < numJugadores - 1; i++) {
                jugadores.get(i).setMano(jugadores.get(i + 1).getMano());
            }
            jugadores.get(numJugadores - 1).setMano(primeraMano);
        } else {
            Mano ultimaMano = jugadores.get(numJugadores - 1).getMano();
            for (int i = numJugadores - 1; i > 0; i--) {
                jugadores.get(i).setMano(jugadores.get(i - 1).getMano());
            }
            jugadores.get(0).setMano(ultimaMano);
        }
        tablero.siguienteTurno();
    }

    public String getDetalleUltimoEfecto() {
        return detalleUltimoEfecto;
    }

    private int calcularPuntos(Jugador j) {
        return j.getMano().getCartasReales().stream()
                .mapToInt(c -> {
                    if (c.esComodin()) return 50;
                    if (c.esAccion()) return 20;
                    return c.getValor().ordinal(); 
                }).sum();
    }
}