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
    public void testRobarCarta() {
        System.out.println("[Test] Ejecutando: testRobarCarta");
        Jugador actualAntes = tablero.getJugadorActual();
        int cartasAntes = actualAntes.getNumCartas();
        
        Object resultado = fachada.robarCarta();
        
        assertTrue(resultado instanceof ResultadoJugadaDTO);
        ResultadoJugadaDTO dto = (ResultadoJugadaDTO) resultado;
        
        // CORRECCIÓN: Comparar con el Enum TipoEvento
        assertEquals(TipoEvento.ROBO_Y_PASO, dto.getEventoTipo());
        assertEquals("El jugador debe tener una carta más", cartasAntes + 1, actualAntes.getNumCartas());
        assertNotEquals("El turno debe haber cambiado", actualAntes, tablero.getJugadorActual());
    }

    @Test
    public void testValidarYPlayExitoso() {
        System.out.println("[Test] Ejecutando: testValidarYPlayExitoso");
        Jugador actual = tablero.getJugadorActual();
        Carta cima = descarte.getCartaCima();
        
        Carta cartaParaJugar = actual.getMano().getCartasReales().stream()
                .filter(c -> c.getValor() == cima.getValor() || c.getColor() == cima.getColor() || c.esComodin())
                .findFirst()
                .orElse(null);

        if (cartaParaJugar == null) {
            cartaParaJugar = new Carta(cima.getValor(), cima.getColor());
            actual.agregarCarta(cartaParaJugar);
        }

        CartaDTO dtoEnvio = new CartaDTO(cartaParaJugar.getValor(), cartaParaJugar.getColor());
        
        Object resultado = fachada.validarYPlay(dtoEnvio);
        
        assertTrue(resultado instanceof ResultadoJugadaDTO);
        ResultadoJugadaDTO resDTO = (ResultadoJugadaDTO) resultado;
        assertTrue("La jugada debería ser válida", resDTO.isExito());
    }

    @Test
    public void testGritoUnoInvalidoPorCantidad() {
        System.out.println("[Test] Ejecutando: testGritoUnoInvalidoPorCantidad");
        Jugador actual = tablero.getJugadorActual();
        
        JugadorDTO datosGrito = new JugadorDTO(actual.getIdJugador(), actual.getNombre());
        
        Object resultado = fachada.gritarUno(datosGrito);
        
        assertTrue(resultado instanceof ResultadoGritoDTO);
        ResultadoGritoDTO res = (ResultadoGritoDTO) resultado;
        
        assertFalse("No debe ser exitoso con 7 cartas", res.isExitoGrito());
        // CORRECCIÓN: Si tu DTO devuelve String, usar toString() del Enum. 
        // Si tu DTO ya usa TipoEvento, comparar directamente.
        assertEquals(TipoEvento.GRITO_INVALIDO, res.getMensaje());
    }

    @Test
    public void testPasarTurno() {
        System.out.println("[Test] Ejecutando: testPasarTurno");
        Jugador primero = tablero.getJugadorActual();
        
        fachada.pasarTurno();
        
        Jugador segundo = tablero.getJugadorActual();
        assertNotEquals("El turno debe ser diferente al inicial", primero.getIdJugador(), segundo.getIdJugador());
    }
    
    @Test
    public void testEfectoProhibido() {
        Jugador j1 = tablero.getJugadorActual();
        Carta prohibido = new Carta(Valor.PROHIBIDO, descarte.getCartaCima().getColor());
        j1.agregarCarta(prohibido);

        fachada.validarYPlay(new CartaDTO(prohibido.getValor(), prohibido.getColor()));

        assertEquals("Jugador 3", tablero.getJugadorActual().getNombre());
    }
    
    @Test
    public void testEfectoMasDos() {
        Jugador j2 = listaJugadores.get(1);
        Carta masDos = new Carta(Valor.MASDOS, descarte.getCartaCima().getColor());
        tablero.getJugadorActual().agregarCarta(masDos);

        fachada.validarYPlay(new CartaDTO(masDos.getValor(), masDos.getColor()));

        assertEquals(9, j2.getNumCartas());
    }
    
    @Test
    public void testMazoVacioPasaTurnoAutomatico() {
        System.out.println("\n--- TEST: Mazo Vacío -> Pasar Turno ---");
        while (mazo.tomarUnaCarta() != null); 

        String jugadorActualAntes = tablero.getJugadorActual().getNombre();

        ResultadoJugadaDTO resultado = (ResultadoJugadaDTO) fachada.robarCarta();

        assertNotEquals("El turno debería haber pasado", 
                        jugadorActualAntes, tablero.getJugadorActual().getNombre());

        assertNull("El mazo debería seguir vacío", mazo.tomarUnaCarta());
    }
    
    @Test
    public void testEfectoComodínCambioColor() {
        System.out.println("\n--- TEST: Validación de Comodín ---");

        Jugador j1 = tablero.getJugadorActual();
        Carta comodin = new Carta(Valor.CAMBIOCOLOR, Colores.NEGRO);
        j1.agregarCarta(comodin);

        CartaDTO jugada = new CartaDTO(Valor.CAMBIOCOLOR, Colores.AZUL);

        fachada.validarYPlay(jugada);

        assertEquals("El color del juego debería haber cambiado a AZUL", 
                     Colores.AZUL, descarte.getCartaCima().getColor());

        assertNotEquals("El turno debería haber avanzado", 
                        j1.getNombre(), tablero.getJugadorActual().getNombre());
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