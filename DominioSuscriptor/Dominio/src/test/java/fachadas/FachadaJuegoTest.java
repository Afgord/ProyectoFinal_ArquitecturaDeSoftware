/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fachadas;

import dtos.CartaDTO;
import dtos.JugadorDTO;
import dtos.ResultadoGritoDTO;
import dtos.ResultadoJugadaDTO;
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
        listaJugadores = new ArrayList<>();
        listaJugadores.add(new Jugador("1", "David", "avatar1.png", new Mano()));
        listaJugadores.add(new Jugador("2", "IA", "avatar2.png", new Mano()));
        listaJugadores.add(new Jugador("3", "Oportunidad", "avatar3.png", new Mano()));
        listaJugadores.add(new Jugador("4", "Digital", "avatar4.png", new Mano()));

        mazo = new Mazo(0, 9, true, true, true, true, true);
        // Forzamos una cima neutral (ej. un 8 Rojo) para las pruebas
        descarte = new Descarte(new Carta(Valor.OCHO, Colores.ROJO));
        tablero = new Tablero(mazo, descarte, listaJugadores, new Ruleta());
        fachada = new FachadaJuego(tablero);
    }

    @Test
    public void testCasosDeJugadas() {
        System.out.println("--- Probando: Validación de Colores y Números ---");
        // Caso 1: Carta del mismo color (Éxito)
        tablero.getJugadorActual().agregarCarta(new Carta(Valor.CINCO, Colores.ROJO));
        ResultadoJugadaDTO res1 = (ResultadoJugadaDTO) fachada.validarYPlay(new CartaDTO(Valor.CINCO, Colores.ROJO));
        assertTrue("Debería aceptar mismo color", res1.isExito());

        // Caso 2: Carta de distinto color pero mismo número (Éxito)
        // Ahora la cima es un 5 Rojo (del test anterior)
        tablero.getJugadorActual().agregarCarta(new Carta(Valor.CINCO, Colores.VERDE));
        ResultadoJugadaDTO res2 = (ResultadoJugadaDTO) fachada.validarYPlay(new CartaDTO(Valor.CINCO, Colores.VERDE));
        assertTrue("Debería aceptar mismo valor aunque cambie el color", res2.isExito());

        // Caso 3: Carta totalmente diferente (Fallo)
        // Cima es 5 Verde. Intentamos tirar un 2 Azul.
        tablero.getJugadorActual().agregarCarta(new Carta(Valor.DOS, Colores.AZUL));
        ResultadoJugadaDTO res3 = (ResultadoJugadaDTO) fachada.validarYPlay(new CartaDTO(Valor.DOS, Colores.AZUL));
        assertFalse("Debería rechazar carta que no coincide en nada", res3.isExito());
    }

    @Test
    public void testComodinesYAsignacionDeColor() {
        System.out.println("--- Probando: Comodines y Cambio de Color ---");
        // La cima es 8 Rojo. Tiramos un Cambio de Color y pedimos que sea Azul.
        Carta comodin = new Carta(Valor.CAMBIOCOLOR, Colores.NEGRO);
        tablero.getJugadorActual().agregarCarta(comodin);

        ResultadoJugadaDTO res = (ResultadoJugadaDTO) fachada.validarYPlay(new CartaDTO(Valor.CAMBIOCOLOR, Colores.AZUL));
        
        assertTrue(res.isExito());
        assertEquals("La cima en el DTO debe ser Azul", Colores.AZUL, res.getCartaCima().getColor());
        assertEquals("La carta real en el dominio debe ser Azul", Colores.AZUL, descarte.getCartaCima().getColor());
    }

    @Test
    public void testEfectosDeCastigo() {
        System.out.println("--- Probando: Efectos de Castigo (MÁS DOS) ---");
        Jugador victima = listaJugadores.get(1); // El siguiente
        int cartasAntes = victima.getNumCartas();
        
        tablero.getJugadorActual().agregarCarta(new Carta(Valor.MASDOS, Colores.ROJO));
        fachada.validarYPlay(new CartaDTO(Valor.MASDOS, Colores.ROJO));

        assertEquals("La víctima debería tener 2 cartas más", cartasAntes + 2, victima.getNumCartas());
    }

    @Test
    public void testActivacionRuletaSpin() {
        System.out.println("--- Probando: Activación de Ruleta (Carta 1-5) ---");
        // Forzamos una carta Spin (Valor DOS)
        tablero.getJugadorActual().agregarCarta(new Carta(Valor.DOS, Colores.ROJO));
        
        ResultadoJugadaDTO res = (ResultadoJugadaDTO) fachada.validarYPlay(new CartaDTO(Valor.DOS, Colores.ROJO));
        
        assertTrue(res.isExito());
        // Verificamos que el evento no sea el de descarte normal si la ruleta hizo algo
        assertNotNull("El evento debería reflejar el resultado de la ruleta", res.getEventoTipo());
    }

    @Test
    public void testGritoUnoPenalizacion() {
        System.out.println("--- Probando: Grito de UNO (Atrapado) ---");
        Jugador actual = tablero.getJugadorActual();
        
        // Simulamos que al jugador le queda 1 carta pero NO ha gritado
        actual.getMano().getCartasReales().clear();
        actual.agregarCarta(new Carta(Valor.SIETE, Colores.AMARILLO));
        
        // Otro jugador (el "3") lo delata usando el botón de pánico
        JugadorDTO delator = new JugadorDTO("3", "Oportunidad");
        ResultadoGritoDTO res = (ResultadoGritoDTO) fachada.gritarUno(delator);

        assertTrue("El grito de denuncia debe ser exitoso", res.isExitoGrito());
        assertEquals(TipoEvento.ATRAPADO, res.getEvento());
        assertEquals("David debe ser el castigado", "1", res.getIdCastigado());
        assertEquals("Debería tener 3 cartas ahora (1 original + 2 de castigo)", 3, actual.getNumCartas());
    }

    @Test
    public void testRobarYPasar() {
        System.out.println("--- Probando: Robar y Pasar ---");
        Jugador anterior = tablero.getJugadorActual();
        int cartasAntes = anterior.getNumCartas();
        
        fachada.robarCarta();
        
        assertEquals("El jugador debería tener una carta más", cartasAntes + 1, anterior.getNumCartas());
        assertNotEquals("El turno debería haber pasado", anterior.getIdJugador(), tablero.getJugadorActual().getIdJugador());
    }
}