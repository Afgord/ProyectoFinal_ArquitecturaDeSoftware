/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.List;
import java.util.Random;

/**
 * * @author lagar
 */
public class Ruleta {
    private final Random random = new Random();
    private Tablero tablero;

    public String girarYAplicar(Tablero tablero) {
        this.tablero = tablero;
        ResultadoRuleta[] resultados = ResultadoRuleta.values();
        ResultadoRuleta resultado = resultados[random.nextInt(resultados.length)];
        
        System.out.println("[Ruleta] ¡Girando...! Resultado: " + resultado);

        switch (resultado) {
            case CASI_UNO -> aplicarCasiUno();
            case DESCARTAR_NUMERO -> aplicarDescartarNumero();
            case DESCARTAR_COLOR -> aplicarDescartarColor();
            case ROBAR_HASTA_COLOR -> aplicarRobarHastaColor();
            case GUERRA -> { 
                return prepararGuerra(); 
            }
            case MOSTRAR_MANO -> aplicarMostrarMano();
            case INTERCAMBIO_MANOS -> aplicarIntercambioManos();
            case PUNTUACION_BAJA -> { 
                return prepararPuntuacionBaja(); 
            }
            default -> {
                System.out.println("[Ruleta] Sin efecto especial, pasando turno.");
                tablero.siguienteTurno();
            }
        }
        return resultado.toString();
    }

    private String prepararGuerra() {
        System.out.println("[Ruleta] Evento: GUERRA - Calculando ganador por carta más alta...");
        List<Jugador> jugadores = tablero.getJugadores();
        Jugador ganadorGuerra = null;
        int valorMasAlto = -1;

        for (Jugador j : jugadores) {
            Carta masAlta = j.getMano().getCartasReales().stream()
                    .filter(Carta::esNumerica)
                    .max((c1, c2) -> Integer.compare(c1.getValor().ordinal(), c2.getValor().ordinal()))
                    .orElse(null);

            if (masAlta != null) {
                System.out.println("[Ruleta] " + j.getNombre() + " tiene un " + masAlta.getValor());
                if (masAlta.getValor().ordinal() > valorMasAlto) {
                    valorMasAlto = masAlta.getValor().ordinal();
                    ganadorGuerra = j;
                }
            }
        }
        
        if (ganadorGuerra != null) {
            System.out.println("[Ruleta] El ganador es " + ganadorGuerra.getNombre() + ". Esperando elección de carta...");
            return "GUERRA_WAIT:" + ganadorGuerra.getIdJugador();
        }
        
        System.out.println("[Ruleta] Nadie tiene cartas numéricas. Guerra cancelada.");
        return "GUERRA_NADIE";
    }

    private String prepararPuntuacionBaja() {
        System.out.println("[Ruleta] Evento: PUNTUACIÓN BAJA - Calculando jugador con menos puntos...");
        Jugador ganador = tablero.getJugadores().stream()
                .min((j1, j2) -> Integer.compare(calcularPuntos(j1), calcularPuntos(j2)))
                .orElse(null);

        if (ganador != null) {
            System.out.println("[Ruleta] " + ganador.getNombre() + " tiene la puntuación más baja. Esperando descarte...");
            return "PUNTUACION_BAJA_WAIT:" + ganador.getIdJugador();
        }
        
        return "PUNTUACION_BAJA_NADIE";
    }

    private void aplicarCasiUno() {
        Jugador victima = tablero.getJugadorActual();
        System.out.println("[Ruleta] CASI UNO: " + victima.getNombre() + " se queda solo con 2 cartas.");
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
            System.out.println("[Ruleta] DESCARTAR NÚMERO: " + victima.getNombre() + " descarta todos sus " + valorMasFrecuente);
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
            System.out.println("[Ruleta] DESCARTAR COLOR: " + victima.getNombre() + " descarta todo lo de color " + colorMasFrecuente);
            cartas.removeIf(c -> c.getColor() == colorMasFrecuente);
        }
        
        tablero.siguienteTurno();
    }

    private void aplicarRobarHastaColor() {
        Jugador victima = tablero.getJugadorActual();
        Mazo mazo = tablero.getMazo();
        Colores objetivo = new Random().nextBoolean() ? Colores.ROJO : Colores.AZUL;
        System.out.println("[Ruleta] ROBAR HASTA COLOR: " + victima.getNombre() + " busca el color " + objetivo);

        boolean encontrado = false;
        int contador = 0;
        while (!encontrado && !mazo.estaVacio()) {
            Carta c = mazo.tomarUnaCarta();
            if (c != null) {
                victima.agregarCarta(c);
                contador++;
                if (c.getColor() == objetivo) {
                    encontrado = true;
                    System.out.println("[Ruleta] ¡Color encontrado tras robar " + contador + " cartas!");
                }
            }
        }
        tablero.siguienteTurno();
    }

    private void aplicarMostrarMano() {
        Jugador victima = tablero.getJugadorActual();
        System.out.println("[Ruleta] MOSTRAR MANO: " + victima.getNombre() + " revela sus cartas.");
        tablero.siguienteTurno();
    }

    private void aplicarIntercambioManos() {
        List<Jugador> jugadores = tablero.getJugadores();
        int numJugadores = jugadores.size();
        
        if (numJugadores < 2) {
            tablero.siguienteTurno();
            return;
        }

        System.out.println("[Ruleta] ¡INTERCAMBIO DE MANOS!");
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

    private int calcularPuntos(Jugador j) {
        return j.getMano().getCartasReales().stream()
                .mapToInt(c -> {
                    if (c.esComodin()) return 50;
                    if (c.esAccion()) return 20;
                    return c.getValor().ordinal(); 
                }).sum();
    }
}