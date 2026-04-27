/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fachadas;

import dtos.*;
import entidades.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FachadaJuegoTest {
    
    private FachadaDominio fachada;
    private Tablero tablero;
    private List<Jugador> listaJugadores;
    private Mazo mazo;
    private Descarte descarte;

    @Before
    public void setUp() {
        System.out.println("\n=== Configurando Entorno de Prueba ===");
        
        listaJugadores = new ArrayList<>();
        listaJugadores.add(new Jugador("1", "Jugador 1", "avatar1.png", new Mano()));
        listaJugadores.add(new Jugador("2", "Jugador 2", "avatar2.png", new Mano()));
        listaJugadores.add(new Jugador("3", "Jugador 3", "avatar3.png", new Mano()));
        listaJugadores.add(new Jugador("4", "Jugador 4", "avatar4.png", new Mano()));

        mazo = new Mazo(0, 9, true, true, true, true, true);
        Carta inicio = mazo.sacarCartaInicialValida();
        
        descarte = new Descarte(inicio);
        Ruleta ruleta = new Ruleta();
        tablero = new Tablero(mazo, descarte, listaJugadores, ruleta);
        
        for (Jugador jugador : listaJugadores) {
            for (int i = 0; i < 7; i++) {
                Carta c = mazo.tomarUnaCarta();
                if (c != null) {
                    jugador.agregarCarta(c);
                }
            }
        }
        
        fachada = new FachadaJuego(tablero);
        System.out.println("=== Configuración de Prueba Exitosa ===\n");
    }
    
    
    @Test
    public void testActivacionRuletaPorCartaNumerica() {
        System.out.println("\n--- TEST: Activación de Ruleta (Carta 1-5) ---");

        Carta cimaActual = descarte.getCartaCima();
        Carta cartaRuleta = new Carta(Valor.TRES, cimaActual.getColor());
        tablero.getJugadorActual().agregarCarta(cartaRuleta);

        fachada.validarYPlay(new CartaDTO(Valor.TRES, cimaActual.getColor()));
    }
}