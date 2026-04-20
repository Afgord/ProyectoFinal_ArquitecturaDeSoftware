/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import entidades.Colores;
import entidades.Tablero;

public class FachadaJuego implements FachadaDominio {
    private Tablero tablero;

    public FachadaJuego(Tablero tablero) {
        this.tablero = tablero;
    }

    @Override
    public boolean validarYPlay(CartaDTO carta) {
        System.out.println("[Fachada] Intentando validar y jugar carta...");

        boolean resultado = tablero.ejecutarJugada(carta);

        System.out.println("[Fachada] Resultado de la jugada: " 
            + (resultado ? "Éxito" : "Falló"));

        return resultado;
    }

    @Override
    public void robarCarta() {
        System.out.println("[Fachada] Jugador actual roba carta");
        tablero.robarYPasar();
    }

    @Override
    public void cambiarColorDescarte(Colores color) {
        System.out.println("[Fachada] Cambiando color del descarte a: " + color);

        tablero.getDescarte().cambiarColorCartaCima(color);
    }

    @Override
    public JugadorDTO verificarGanador() {
        System.out.println("[Fachada] Verificando si hay ganador...");

        JugadorDTO ganador = tablero.obtenerGanadorDTO();

        if (ganador != null) {
            System.out.println("[Fachada] Ganador: " + ganador.getNombre());
        } else {
            System.out.println("[Fachada] Aún no hay ganador");
        }

        return ganador;
    }

    @Override
    public void pasarTurno() {
        System.out.println("[Fachada] Pasando turno...");
        tablero.siguienteTurno();
    }

    @Override
    public Tablero getTablero() { 
        return this.tablero; 
    }
}