/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.List;
import java.util.Random;
/**
 * 
 * @author lagar
 */
public class Ruleta {
    private final Random random = new Random();
    private Tablero tablero;

    public void girarYAplicar(Tablero tablero) {
        this.tablero = tablero;
        ResultadoRuleta[] resultados = ResultadoRuleta.values();
        ResultadoRuleta resultado = resultados[random.nextInt(resultados.length)];
        
        switch (resultado) {
            case CASI_UNO -> aplicarCasiUno();
            case DESCARTAR_NUMERO -> aplicarDescartarNumero();
            case DESCARTAR_COLOR -> aplicarDescartarColor();
            case ROBAR_HASTA_COLOR -> aplicarRobarHastaColor();
            case GUERRA -> aplicarGuerra();
            case MOSTRAR_MANO -> aplicarMostrarMano();
            case INTERCAMBIO_MANOS -> aplicarIntercambioManos();
            case PUNTUACION_BAJA -> aplicarPuntuacionBaja();
            default -> tablero.siguienteTurno();
        }
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
        Colores objetivo = new Random().nextBoolean() ? Colores.ROJO : Colores.AZUL;
        System.out.println("[Ruleta] " + victima.getNombre() + " debe robar hasta encontrar: " + objetivo);

        boolean encontrado = false;
        while (!encontrado && !mazo.estaVacio()) {
            Carta c = mazo.tomarUnaCarta();
            if (c != null) {
                victima.agregarCarta(c);
                if (c.getColor() == objetivo) {
                    encontrado = true;
                    System.out.println("[Ruleta] ¡Carta encontrada! Se detiene el robo.");
                }
            }
        }
        tablero.siguienteTurno();
    }
    
    private void aplicarGuerra() {
        List<Jugador> jugadores = tablero.getJugadores();
        Carta cartaMasAltaGlobal = null;
        Jugador ganadorGuerra = null;

        System.out.println("[Ruleta] ¡Inicia la GUERRA!");

        for (Jugador j : jugadores) {
            Carta masAlta = j.getMano().getCartasReales().stream()
                    .filter(Carta::esNumerica)
                    .max((c1, c2) -> Integer.compare(c1.getValor().ordinal(), c2.getValor().ordinal()))
                    .orElse(null);

            if (masAlta != null) {
                System.out.println(j.getNombre() + " muestra un " + masAlta.getValor());
                if (ganadorGuerra == null || masAlta.getValor().ordinal() > cartaMasAltaGlobal.getValor().ordinal()) {
                    cartaMasAltaGlobal = masAlta;
                    ganadorGuerra = j;
                }
            }
        }
        
        if (ganadorGuerra != null) {
            final Valor valorGanador = cartaMasAltaGlobal.getValor();
            System.out.println("Ganador de la guerra: " + ganadorGuerra.getNombre());
            ganadorGuerra.getMano().getCartasReales().removeIf(c -> c.getValor() == valorGanador);
        }

        tablero.siguienteTurno();
    }
    
    private void aplicarMostrarMano() {
        Jugador victima = tablero.getJugadorActual();
        System.out.println("[Ruleta] " + victima.getNombre() + " debe mostrar su mano.");
        
        victima.getMano().getCartasReales().forEach(c -> 
            System.out.println("Visible: " + c.getValor() + " " + c.getColor())
        );

        tablero.siguienteTurno();
    }
    
    private void aplicarIntercambioManos() {
        List<Jugador> jugadores = tablero.getJugadores();
        int numJugadores = jugadores.size();
        
        if (numJugadores < 2) {
            tablero.siguienteTurno();
            return;
        }

        System.out.println("[Ruleta] ¡INTERCAMBIO DE MANOS! Todos pasan sus cartas.");
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
    
    private void aplicarPuntuacionBaja() {
        Jugador ganador = tablero.getJugadores().stream()
                .min((j1, j2) -> Integer.compare(calcularPuntos(j1), calcularPuntos(j2)))
                .orElse(null);

        if (ganador != null) {
            System.out.println("El jugador con menos puntos es: " + ganador.getNombre());
            List<Carta> cartas = ganador.getMano().getCartasReales();
            if (!cartas.isEmpty()) {
                cartas.remove(0);
                System.out.println(ganador.getNombre() + " ha descartado una carta por tener la puntuación más baja.");
            }
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