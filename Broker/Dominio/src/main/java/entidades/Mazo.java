/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazo {
    private final List<Carta> baraja;

    public Mazo(int rangoInicio, int rangoFinal, boolean masDos, boolean prohibido, 
                boolean reversa, boolean masCuatro, boolean cambioColor) {
        
        this.baraja = new ArrayList<>();
        
        System.out.println("[Mazo] Generando baraja...");
        
        generarCartas(rangoInicio, rangoFinal, masDos, prohibido, reversa, masCuatro, cambioColor);
        
        System.out.println("[Mazo] Total de cartas generadas: " + baraja.size());

        Collections.shuffle(baraja);
        System.out.println("[Mazo] Baraja mezclada");
    }

    private void generarCartas(int rI, int rF, boolean m2, boolean pro, boolean rev, boolean m4, boolean cc) {
        Colores[] coloresValidos = {Colores.AZUL, Colores.ROJO, Colores.AMARILLO, Colores.VERDE};
        Valor[] valoresNumericos = Valor.values(); 

        System.out.println("[Mazo] Generando cartas numéricas...");
        
        for (int n = rI; n <= rF; n++) {
            for (Colores col : coloresValidos) {
                baraja.add(new Carta(valoresNumericos[n], col));
                baraja.add(new Carta(valoresNumericos[n], col));
            }
        }

        System.out.println("[Mazo] Generando cartas especiales...");

        for (Colores col : coloresValidos) {
            if (m2) {
                baraja.add(new Carta(Valor.MASDOS, col));
                System.out.println("[Mazo] Añadida carta +2 de color " + col);
            }
            if (rev) {
                baraja.add(new Carta(Valor.REVERSA, col));
                System.out.println("[Mazo] Añadida carta REVERSA de color " + col);
            }
            if (pro) {
                baraja.add(new Carta(Valor.PROHIBIDO, col));
                System.out.println("[Mazo] Añadida carta PROHIBIDO de color " + col);
            }
        }

        if (m4) {
            for (int i = 0; i < 4; i++) {
                baraja.add(new Carta(Valor.MASCUATRO, Colores.NEGRO));
            }
            System.out.println("[Mazo] Añadidas cartas +4 negras");
        }

        if (cc) {
            for (int i = 0; i < 4; i++) {
                baraja.add(new Carta(Valor.CAMBIOCOLOR, Colores.NEGRO));
            }
            System.out.println("[Mazo] Añadidas cartas de cambio de color");
        }
    }

    public Carta sacarCartaInicialValida() {
        System.out.println("[Mazo] Buscando carta inicial válida...");

        for (int i = 0; i < baraja.size(); i++) {
            Carta c = baraja.get(i);

            if (c.getColor() != Colores.NEGRO && esNumerica(c.getValor())) {
                System.out.println("[Mazo] Carta inicial encontrada: " 
                    + c.getValor() + " de color " + c.getColor());

                return baraja.remove(i); 
            }
        }

        System.out.println("[Mazo] No se encontró carta ideal, tomando la primera disponible");

        return baraja.isEmpty() ? null : baraja.remove(0);
    }

    private boolean esNumerica(Valor valor) {
        return valor.ordinal() <= Valor.NUEVE.ordinal();
    }

    public Carta tomarUnaCarta() {
        if (baraja.isEmpty()) {
            System.out.println("[Mazo] No hay cartas para tomar");
            return null;
        }

        Carta c = baraja.remove(0);

        System.out.println("[Mazo] Carta tomada: " 
            + c.getValor() + " de color " + c.getColor());
        System.out.println("[Mazo] Cartas restantes: " + baraja.size());

        return c;
    }
    
    public List<Carta> tomarDosCartas() {
        System.out.println("[Mazo] Tomando 2 cartas...");
        return tomarVariasCartas(2);
    }
    
    public List<Carta> tomarCuatroCartas() {
        System.out.println("[Mazo] Tomando 4 cartas...");
        return tomarVariasCartas(4);
    }
     
    private List<Carta> tomarVariasCartas(int cantidad) {
        List<Carta> cartasTomadas = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            Carta c = tomarUnaCarta();
            if (c != null) {
                cartasTomadas.add(c);
            }
        }

        System.out.println("[Mazo] Total cartas tomadas: " + cartasTomadas.size());

        return cartasTomadas;
    }
    
    public int getCantidadCartas() { 
        return baraja.size(); 
    }

    public boolean estaVacio() { 
        return baraja.isEmpty(); 
    }
}