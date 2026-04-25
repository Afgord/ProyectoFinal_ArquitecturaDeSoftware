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

/**
 * Pruebas unitarias para la Fachada del Juego.
 * @author lagar
 */
public class FachadaJuegoTest {
    
    private FachadaDominio fachada;
    private Tablero tablero;
    private List<Jugador> listaJugadores;
    private Mazo mazo;
    private Descarte descarte;

    @Before
    public void setUp() {
        System.out.println("\n=== Configurando Entorno de Prueba ===");
        
        // 1. Crear manos y lista de jugadores (4 jugadores)
        listaJugadores = new ArrayList<>();
        listaJugadores.add(new Jugador("1", "Jugador 1", "avatar1.png", new Mano()));
        listaJugadores.add(new Jugador("2", "Jugador 2", "avatar2.png", new Mano()));
        listaJugadores.add(new Jugador("3", "Jugador 3", "avatar3.png", new Mano()));
        listaJugadores.add(new Jugador("4", "Jugador 4", "avatar4.png", new Mano()));

        // 2. Mazo y Carta Inicial
        mazo = new Mazo(0, 9, true, true, true, true, true);
        Carta inicio = mazo.sacarCartaInicialValida();
        
        // 3. Descarte (según tu inicialización: new Descarte(inicio))
        descarte = new Descarte(inicio);
        
        Ruleta ruleta = new Ruleta();
        
        // 4. Tablero
        tablero = new Tablero(mazo, descarte, listaJugadores, ruleta);
        
        // 5. Repartir 7 cartas a cada uno
        for (Jugador jugador : listaJugadores) {
            for (int i = 0; i < 7; i++) {
                Carta c = mazo.tomarUnaCarta();
                if (c != null) {
                    jugador.agregarCarta(c);
                }
            }
        }
        
        // 6. Inicializar Fachada
        fachada = new FachadaJuego(tablero);
        System.out.println("=== Configuración de Prueba Exitosa ===\n");
    }
    
    //@Test
    public void testRobarCarta() {
        System.out.println("[Test] Ejecutando: testRobarCarta");
        Jugador actualAntes = tablero.getJugadorActual();
        int cartasAntes = actualAntes.getNumCartas();
        
        // Uso del método correcto: robarCarta()
        Object resultado = fachada.robarCarta();
        
        assertTrue(resultado instanceof ResultadoJugadaDTO);
        ResultadoJugadaDTO dto = (ResultadoJugadaDTO) resultado;
        
        assertEquals("ROBO_Y_PASO", dto.getEventoTipo());
        assertEquals("El jugador debe tener una carta más", cartasAntes + 1, actualAntes.getNumCartas());
        assertNotEquals("El turno debe haber cambiado", actualAntes, tablero.getJugadorActual());
    }

    //@Test
    public void testValidarYPlayExitoso() {
        System.out.println("[Test] Ejecutando: testValidarYPlayExitoso");
        Jugador actual = tablero.getJugadorActual();
        Carta cima = descarte.getCartaCima();
        
        // Buscamos una carta que se pueda jugar o forzamos una para el test
        Carta cartaParaJugar = actual.getMano().getCartasReales().stream()
                .filter(c -> c.getValor() == cima.getValor() || c.getColor() == cima.getColor() || c.esComodin())
                .findFirst()
                .orElse(null);

        if (cartaParaJugar == null) {
            // Si el azar no nos dio carta, le damos una válida para no romper el test
            cartaParaJugar = new Carta(cima.getValor(), cima.getColor());
            actual.agregarCarta(cartaParaJugar);
        }

        CartaDTO dtoEnvio = new CartaDTO(cartaParaJugar.getValor(), cartaParaJugar.getColor());
        
        // Uso del método correcto: validarYPlay()
        Object resultado = fachada.validarYPlay(dtoEnvio);
        
        assertTrue(resultado instanceof ResultadoJugadaDTO);
        ResultadoJugadaDTO resDTO = (ResultadoJugadaDTO) resultado;
        assertTrue("La jugada debería ser válida", resDTO.isExito());
    }

    //@Test
    public void testGritoUnoInvalidoPorCantidad() {
        System.out.println("[Test] Ejecutando: testGritoUnoInvalidoPorCantidad");
        Jugador actual = tablero.getJugadorActual();
        
        // Jugador con 7 cartas intenta gritar UNO
        JugadorDTO datosGrito = new JugadorDTO(actual.getIdJugador(), actual.getNombre());
        
        Object resultado = fachada.gritarUno(datosGrito);
        
        assertTrue(resultado instanceof ResultadoGritoDTO);
        ResultadoGritoDTO res = (ResultadoGritoDTO) resultado;
        
        assertFalse("No debe ser exitoso con 7 cartas", res.isExitoGrito());
        assertEquals("GRITO_INVALIDO", res.getMensaje());
    }

    //@Test
    public void testPasarTurno() {
        System.out.println("[Test] Ejecutando: testPasarTurno");
        Jugador primero = tablero.getJugadorActual();
        
        fachada.pasarTurno();
        
        Jugador segundo = tablero.getJugadorActual();
        assertNotEquals("El turno debe ser diferente al inicial", primero.getIdJugador(), segundo.getIdJugador());
    }
    
    
    //@Test
    public void testEfectoProhibido() {
        Jugador j1 = tablero.getJugadorActual();
        Carta prohibido = new Carta(Valor.PROHIBIDO, descarte.getCartaCima().getColor());
        j1.agregarCarta(prohibido);

        fachada.validarYPlay(new CartaDTO(prohibido.getValor(), prohibido.getColor()));

        // El turno debería ser del Jugador 3
        assertEquals("Jugador 3", tablero.getJugadorActual().getNombre());
    }
    
    //@Test
    public void testEfectoMasDos() {
        Jugador j2 = listaJugadores.get(1);
        Carta masDos = new Carta(Valor.MASDOS, descarte.getCartaCima().getColor());
        tablero.getJugadorActual().agregarCarta(masDos);

        fachada.validarYPlay(new CartaDTO(masDos.getValor(), masDos.getColor()));

        assertEquals(9, j2.getNumCartas());
    }
    
    //@Test
    public void testMazoVacioPasaTurnoAutomatico() {
        System.out.println("\n--- TEST: Mazo Vacío -> Pasar Turno ---");
        while (mazo.tomarUnaCarta() != null); 

        // 2. Registramos quién es el jugador actual y el siguiente
        String jugadorActualAntes = tablero.getJugadorActual().getNombre();

        // 3. El jugador intenta robar
        // Según tus reglas, esto no debe rellenar el mazo, debe pasar el turno
        ResultadoJugadaDTO resultado = (ResultadoJugadaDTO) fachada.robarCarta();

        // 4. Verificaciones
        // El turno ya no debe ser del jugador que intentó robar
        assertNotEquals("El turno debería haber pasado", 
                        jugadorActualAntes, tablero.getJugadorActual().getNombre());

        // Verificamos que el mazo siga vacío (porque no hay re-barajado)
        assertNull("El mazo debería seguir vacío", mazo.tomarUnaCarta());

        System.out.println("[Test] Turno pasado automáticamente por mazo vacío.");
    }
    
    @Test
    public void testEfectoComodínCambioColor() {
        System.out.println("\n--- TEST: Validación de Comodín (Ruleta de Colores) ---");

        Jugador j1 = tablero.getJugadorActual();
        // 1. Buscamos o creamos un comodín en la mano del jugador
        Carta comodin = new Carta(Valor.CAMBIOCOLOR, Colores.NEGRO);
        j1.agregarCarta(comodin);

        // 2. Simulamos que el jugador elige un color (ej. AZUL)
        // Usamos el DTO para enviar la intención
        CartaDTO jugada = new CartaDTO(Valor.CAMBIOCOLOR, Colores.AZUL);

        System.out.println("[Test] Jugador intenta cambiar el color a AZUL");
        fachada.validarYPlay(jugada);

        // 3. Verificaciones
        // El color actual del descarte debería ser ahora AZUL
        assertEquals("El color del juego debería haber cambiado a AZUL", 
                     Colores.AZUL, descarte.getCartaCima().getColor());

        // El turno debe haber pasado al Jugador 2
        assertNotEquals("El turno debería haber avanzado", 
                        j1.getNombre(), tablero.getJugadorActual().getNombre());

        System.out.println("[Test] Cambio de color exitoso y validado.");
    }
    
    @Test
    public void testActivacionRuletaPorCartaNumerica() {
        System.out.println("\n--- TEST: Activación de Ruleta (Carta 1-5) ---");

        // 1. Miramos qué hay en el descarte para no fallar la validación
        Carta cimaActual = descarte.getCartaCima();

        // 2. Creamos un TRES que coincida en COLOR con la cima para que sea legal
        Carta cartaRuleta = new Carta(Valor.TRES, cimaActual.getColor());
        tablero.getJugadorActual().agregarCarta(cartaRuleta);

        System.out.println("[Test] La cima es " + cimaActual.getValor() + " " + cimaActual.getColor());
        System.out.println("[Test] Jugador 1 tira un TRES " + cimaActual.getColor() + " (Jugada Legal)");

        // 3. Ejecutamos la jugada a través de la fachada
        fachada.validarYPlay(new CartaDTO(Valor.TRES, cimaActual.getColor()));

        // 4. Verificación de logs
        // Ahora sí deberíamos ver: [Tablero] Carta del 1 al 5 detectada. ¡Activando Ruleta!
        // Y luego el log de la clase Ruleta: [Ruleta] ¡Girando...!
    }
}