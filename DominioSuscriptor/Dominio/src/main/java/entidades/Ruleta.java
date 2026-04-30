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

    public TipoEvento girarYAplicar(Tablero tablero) {
        this.tablero = tablero;
        
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
                aplicarGuerra(); 
                // Verificamos si realmente hubo cartas numéricas para determinar el evento
                return hayCartasNumericasEnJuego() ? TipoEvento.GUERRA_CONCLUIDA : TipoEvento.GUERRA_SIN_GANADOR;
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
                aplicarPuntuacionBaja(); 
                return TipoEvento.PUNTUACION_BAJA_APLICADA;
            }
            default -> {
                tablero.siguienteTurno();
                return TipoEvento.CAMBIO_TURNO;
            }
        }
    }

    private void aplicarGuerra() {
        List<Jugador> jugadores = tablero.getJugadores();
        
        // Buscar el valor ordinal más alto entre todas las cartas numéricas de todos los jugadores
        int valorMaximo = jugadores.stream()
                .flatMap(j -> j.getMano().getCartasReales().stream())
                .filter(Carta::esNumerica)
                .mapToInt(c -> c.getValor().ordinal())
                .max()
                .orElse(-1);

        if (valorMaximo != -1) {
            // Quitar todas las cartas que tengan ese mismo valor a todos los jugadores
            for (Jugador j : jugadores) {
                j.getMano().getCartasReales().removeIf(c -> 
                    c.esNumerica() && c.getValor().ordinal() == valorMaximo
                );
            }
            System.out.println("[Ruleta] Guerra: Cartas de valor " + valorMaximo + " eliminadas.");
        }
        tablero.siguienteTurno();
    }

    private void aplicarPuntuacionBaja() {
        // Buscar al jugador (o uno de ellos) con la puntuación mínima
        Jugador victima = tablero.getJugadores().stream()
                .min((j1, j2) -> Integer.compare(calcularPuntos(j1), calcularPuntos(j2)))
                .orElse(null);

        if (victima != null && !victima.getMano().getCartasReales().isEmpty()) {
            List<Carta> cartas = victima.getMano().getCartasReales();
            cartas.remove(random.nextInt(cartas.size()));
            System.out.println("[Ruleta] Puntuación Baja: Carta aleatoria quitada a " + victima.getNombre());
        }
        tablero.siguienteTurno();
    }

    private void aplicarCasiUno() {
        Jugador victima = tablero.getJugadorActual();
        List<Carta> cartas = victima.getMano().getCartasReales();
        // Deja al jugador con solo las últimas 2 cartas
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
        // En lógica automática, este efecto simplemente permite que el 
        // Traductor envíe el estado de todas las manos al cliente.
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

    private boolean hayCartasNumericasEnJuego() {
        return tablero.getJugadores().stream()
                .flatMap(j -> j.getMano().getCartasReales().stream())
                .anyMatch(Carta::esNumerica);
    }
}