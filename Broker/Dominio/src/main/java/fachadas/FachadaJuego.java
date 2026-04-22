/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;
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
    public void pasarTurno() {
        System.out.println("[Fachada] Pasando turno...");
        tablero.siguienteTurno();
    }
}