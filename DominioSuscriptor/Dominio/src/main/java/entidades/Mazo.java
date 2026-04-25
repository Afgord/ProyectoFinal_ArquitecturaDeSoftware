/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que gestiona la baraja del juego.
 * @author lagar
 */
public class Mazo {
    private final List<Carta> baraja;

    public Mazo(int rangoInicio, int rangoFinal, boolean masDos, boolean prohibido, 
                boolean reversa, boolean masCuatro, boolean cambioColor) {
        
        this.baraja = new ArrayList<>();
        
        System.out.println("[Mazo] --- Iniciando generación de baraja ---");
        
        generarCartas(rangoInicio, rangoFinal, masDos, prohibido, reversa, masCuatro, cambioColor);
        
        System.out.println("[Mazo] Total de cartas en baraja: " + baraja.size());

        Collections.shuffle(baraja);
        System.out.println("[Mazo] Baraja mezclada correctamente.");
    }

    private void generarCartas(int rI, int rF, boolean m2, boolean pro, boolean rev, boolean m4, boolean cc) {
        Colores[] coloresValidos = {Colores.AZUL, Colores.ROJO, Colores.AMARILLO, Colores.VERDE};
        Valor[] valoresNumericos = Valor.values(); 

        System.out.println("[Mazo] Generando cartas numéricas (Rango: " + rI + " a " + rF + ")...");
        
        for (int n = rI; n <= rF; n++) {
            for (Colores col : coloresValidos) {
                // Se añaden dos de cada una según las reglas estándar
                baraja.add(new Carta(valoresNumericos[n], col));
                baraja.add(new Carta(valoresNumericos[n], col));
            }
        }

        System.out.println("[Mazo] Generando cartas de acción por color...");

        for (Colores col : coloresValidos) {
            if (m2) {
                baraja.add(new Carta(Valor.MASDOS, col));
                System.out.println("[Mazo] [+] Añadida: MAS DOS (" + col + ")");
            }
            if (rev) {
                baraja.add(new Carta(Valor.REVERSA, col));
                System.out.println("[Mazo] [+] Añadida: REVERSA (" + col + ")");
            }
            if (pro) {
                baraja.add(new Carta(Valor.PROHIBIDO, col));
                System.out.println("[Mazo] [+] Añadida: PROHIBIDO (" + col + ")");
            }
        }

        System.out.println("[Mazo] Generando cartas comodín (Negras)...");

        if (m4) {
            for (int i = 0; i < 4; i++) {
                baraja.add(new Carta(Valor.MASCUATRO, Colores.NEGRO));
            }
            System.out.println("[Mazo] [+] Añadidas: 4 cartas MAS CUATRO");
        }

        if (cc) {
            for (int i = 0; i < 4; i++) {
                baraja.add(new Carta(Valor.CAMBIOCOLOR, Colores.NEGRO));
            }
            System.out.println("[Mazo] [+] Añadidas: 4 cartas CAMBIO COLOR");
        }
    }

    public Carta sacarCartaInicialValida() {
        System.out.println("[Mazo] Buscando carta inicial válida (Numérica)...");
        for (int i = 0; i < baraja.size(); i++) {
            Carta c = baraja.get(i);
            if (!c.esComodin() && !c.esAccion() && c.esNumerica()) {
                System.out.println("[Mazo] Carta inicial seleccionada: " + c.getValor() + " " + c.getColor());
                return baraja.remove(i);
            }
        }
        System.out.println("[Mazo] [!] No se encontró numérica, tomando primera disponible.");
        return baraja.remove(0);
    }

    public Carta tomarUnaCarta() {
        if (baraja.isEmpty()) {
            System.out.println("[Mazo] [ALERTA] Intento de robar en mazo vacío.");
            return null;
        }

        Carta c = baraja.remove(0);

        System.out.println("[Mazo] Carta entregada: " + c.getValor() + " [" + c.getColor() + "]");
        System.out.println("[Mazo] Cartas restantes: " + baraja.size());

        return c;
    }

    public boolean estaVacio() { 
        boolean vacio = baraja.isEmpty();
        if (vacio) System.out.println("[Mazo] El mazo se ha agotado.");
        return vacio; 
    }
}