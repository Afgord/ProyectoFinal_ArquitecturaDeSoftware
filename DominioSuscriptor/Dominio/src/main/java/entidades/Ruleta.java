/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * 
 * @author lagar
 */
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
            case CASI_UNO -> aplicarCasiUno();
            case DESCARTAR_NUMERO -> aplicarDescartarNumero();
            case DESCARTAR_COLOR -> aplicarDescartarColor();
            case ROBAR_HASTA_COLOR -> aplicarRobarHastaColor();
            case GUERRA -> aplicarGuerra();
            case MOSTRAR_MANO -> aplicarMostrarMano();
            case INTERCAMBIO_MANOS -> aplicarIntercambioManos();
            case PUNTUACION_BAJA -> aplicarPuntuacionBaja();
            default -> {
                tablero.siguienteTurno();
                return TipoEvento.CAMBIO_TURNO;
            }
        }
        return TipoEvento.RULETA_ACTIVADA;
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
            System.out.println("[Ruleta - Guerra] Eliminando cartas de valor: " + valorMaximo);
            for (Jugador j : jugadores) {
                j.getMano().getCartasReales().removeIf(c -> 
                    c.esNumerica() && c.getValor().ordinal() == valorMaximo
                );
            }
        }
        tablero.siguienteTurno();
    }

    private void aplicarPuntuacionBaja() {
        List<Jugador> jugadores = tablero.getJugadores();
        if (jugadores.isEmpty()) return;

        int minPuntos = jugadores.stream()
                .mapToInt(this::calcularPuntos)
                .min()
                .orElse(0);

        List<Jugador> victimas = jugadores.stream()
                .filter(j -> calcularPuntos(j) == minPuntos)
                .collect(Collectors.toList());

        System.out.println("[Ruleta] Castigando a " + victimas.size() + " jugador(es) con " + minPuntos + " puntos.");

        for (Jugador victima : victimas) {
            List<Carta> cartas = victima.getMano().getCartasReales();
            if (!cartas.isEmpty()) {
                cartas.remove(random.nextInt(cartas.size()));
            }
        }
        tablero.siguienteTurno();
    }

    private void aplicarCasiUno() {
        Jugador victima = tablero.getJugadorActual();
        List<Carta> cartas = victima.getMano().getCartasReales();
        if (cartas.size() > 2) {
            System.out.println("[Ruleta] Podando mano de: " + victima.getNombre());
            List<Carta> nuevasCartas = new ArrayList<>(cartas.subList(0, 2));
            cartas.clear();
            cartas.addAll(nuevasCartas);
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
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

            if (valorMasFrecuente != null) {
                System.out.println("[Ruleta] Descarte masivo de: " + valorMasFrecuente);
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
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

            if (colorMasFrecuente != null) {
                System.out.println("[Ruleta] Descarte masivo de color: " + colorMasFrecuente);
                cartas.removeIf(c -> c.getColor() == colorMasFrecuente);
            }
        }
        tablero.siguienteTurno();
    }

    private void aplicarRobarHastaColor() {
        Jugador victima = tablero.getJugadorActual();
        Mazo mazo = tablero.getMazo();
        
        Colores[] coloresValidos = {Colores.ROJO, Colores.AZUL, Colores.VERDE, Colores.AMARILLO};
        Colores objetivo = coloresValidos[random.nextInt(coloresValidos.length)];
        
        System.out.println("[Ruleta] " + victima.getNombre() + " busca " + objetivo + " en el mazo...");
        
        boolean encontrado = false;
        while (!encontrado && !mazo.estaVacio()) {
            Carta c = mazo.tomarUnaCarta();
            if (c != null) {
                victima.agregarCarta(c);
                if (c.getColor() == objetivo) {
                    encontrado = true;
                    System.out.println("[Ruleta] ¡Carta encontrada!");
                }
            }
        }
        tablero.siguienteTurno();
    }

    private void aplicarMostrarMano() {
        System.out.println("[Ruleta] Evento: MANO_REVELADA para " + tablero.getJugadorActual().getNombre());
        tablero.siguienteTurno();
    }

    private void aplicarIntercambioManos() {
        List<Jugador> jugadores = tablero.getJugadores();
        int n = jugadores.size();
        if (n < 2) return;

        System.out.println("[Ruleta] Rotando manos de todos los jugadores...");
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

    public ResultadoRuleta getUltimoResultado() {
        return ultimoResultado;
    }
}