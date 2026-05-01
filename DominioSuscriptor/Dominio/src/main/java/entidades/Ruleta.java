/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Ruleta {

    private final Random random = new Random();
    private Tablero tablero;
    private ResultadoRuleta ultimoResultado;

    public TipoEvento girarYAplicar(Tablero tablero) {
        this.tablero = tablero;
        ResultadoRuleta[] resultados = ResultadoRuleta.values();
        this.ultimoResultado = resultados[random.nextInt(resultados.length)];

        System.out.println("[Ruleta] ¡Girando...! Resultado: " + ultimoResultado);

        switch (ultimoResultado) {
            case CASI_UNO -> {
                aplicarCasiUno();
                return TipoEvento.RULETA_ACTIVADA;
            }
            case DESCARTAR_NUMERO -> {
                aplicarDescartarNumero();
                return TipoEvento.RULETA_ACTIVADA;
            }
            case DESCARTAR_COLOR -> {
                aplicarDescartarColor();
                return TipoEvento.RULETA_ACTIVADA;
            }
            case ROBAR_HASTA_COLOR -> {
                aplicarRobarHastaColor();
                return TipoEvento.RULETA_ACTIVADA;
            }
            case GUERRA -> {
                aplicarGuerra();
                return TipoEvento.RULETA_ACTIVADA;
            }
            case MOSTRAR_MANO -> {
                aplicarMostrarMano();
                return TipoEvento.RULETA_ACTIVADA;
            }
            case INTERCAMBIO_MANOS -> {
                aplicarIntercambioManos();
                return TipoEvento.RULETA_ACTIVADA;
            }
            case PUNTUACION_BAJA -> {
                aplicarPuntuacionBaja();
                return TipoEvento.RULETA_ACTIVADA;
            }
            default -> {
                tablero.siguienteTurno();
                return TipoEvento.CAMBIO_TURNO;
            }
        }
    }

    public ResultadoRuleta getUltimoResultado() {
        return ultimoResultado;
    }

    private void aplicarGuerra() {
        List<Jugador> jugadores = tablero.getJugadores();
        int valorMaximo = jugadores.stream()
                .flatMap(j -> j.getMano().getCartasReales().stream())
                .filter(Carta::esNumerica)
                .mapToInt(c -> c.getValor().ordinal())
                .max()
                .orElse(-1);

        if (valorMaximo != -1) {
            for (Jugador j : jugadores) {
                j.getMano().getCartasReales().removeIf(c -> 
                    c.esNumerica() && c.getValor().ordinal() == valorMaximo
                );
            }
        }
        tablero.siguienteTurno();
    }

    private void aplicarPuntuacionBaja() {
        Jugador victima = tablero.getJugadores().stream()
                .min((j1, j2) -> Integer.compare(calcularPuntos(j1), calcularPuntos(j2)))
                .orElse(null);

        if (victima != null && !victima.getMano().getCartasReales().isEmpty()) {
            List<Carta> cartas = victima.getMano().getCartasReales();
            cartas.remove(random.nextInt(cartas.size()));
        }
        tablero.siguienteTurno();
    }

    private void aplicarCasiUno() {
        Jugador victima = tablero.getJugadorActual();
        List<Carta> cartas = victima.getMano().getCartasReales();
        while (cartas.size() > 2) {
            cartas.remove(cartas.size() - 1);
        }
        tablero.siguienteTurno();
    }

    private void aplicarDescartarNumero() {
        Jugador victima = tablero.getJugadorActual();
        List<Carta> cartas = victima.getMano().getCartasReales();
        if (!cartas.isEmpty()) {
            Valor valorMasFrecuente = cartas.stream()
                .filter(Carta::esNumerica)
                .collect(Collectors.groupingBy(Carta::getValor, Collectors.counting()))
                .entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse(null);

            if (valorMasFrecuente != null) {
                cartas.removeIf(c -> c.getValor() == valorMasFrecuente);
            }
        }
        tablero.siguienteTurno();
    }

    private void aplicarDescartarColor() {
        Jugador victima = tablero.getJugadorActual();
        List<Carta> cartas = victima.getMano().getCartasReales();
        if (!cartas.isEmpty()) {
            Colores colorMasFrecuente = cartas.stream()
                .filter(c -> c.getColor() != Colores.NEGRO)
                .collect(Collectors.groupingBy(Carta::getColor, Collectors.counting()))
                .entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse(null);

            if (colorMasFrecuente != null) {
                cartas.removeIf(c -> c.getColor() == colorMasFrecuente);
            }
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
                if (c.getColor() == objetivo) encontrado = true;
            }
        }
        tablero.siguienteTurno();
    }

    private void aplicarMostrarMano() {
        tablero.siguienteTurno();
    }

    private void aplicarIntercambioManos() {
        List<Jugador> jugadores = tablero.getJugadores();
        int n = jugadores.size();
        if (n < 2) {
            tablero.siguienteTurno();
            return;
        }

        if (tablero.isSentidoReloj()) {
            Mano primeraMano = jugadores.get(0).getMano();
            for (int i = 0; i < n - 1; i++) {
                jugadores.get(i).setMano(jugadores.get(i + 1).getMano());
            }
            jugadores.get(n - 1).setMano(primeraMano);
        } else {
            Mano ultimaMano = jugadores.get(n - 1).getMano();
            for (int i = n - 1; i > 0; i--) {
                jugadores.get(i).setMano(jugadores.get(i - 1).getMano());
            }
            jugadores.get(0).setMano(ultimaMano);
        }
        tablero.siguienteTurno();
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