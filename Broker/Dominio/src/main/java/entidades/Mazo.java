/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * * @author lagar
 */
public class Mazo {
    private final List<Carta> baraja;

    public Mazo(int rangoInicio, int rangoFinal, boolean masDos, boolean prohibido, 
                boolean reversa, boolean masCuatro, boolean cambioColor) {
        
        this.baraja = new ArrayList<>();
        
        generarCartas(rangoInicio, rangoFinal, masDos, prohibido, reversa, masCuatro, cambioColor);
        
        Collections.shuffle(baraja);
    }

    private void generarCartas(int rI, int rF, boolean m2, boolean pro, boolean rev, boolean m4, boolean cc) {
        Colores[] coloresValidos = {Colores.AZUL, Colores.ROJO, Colores.AMARILLO, Colores.VERDE};
        Valor[] valoresNumericos = Valor.values(); 

        for (int n = rI; n <= rF; n++) {
            for (Colores col : coloresValidos) {
                baraja.add(new Carta(valoresNumericos[n], col));
                baraja.add(new Carta(valoresNumericos[n], col));
            }
        }

        for (Colores col : coloresValidos) {
            if (m2) baraja.add(new Carta(Valor.MASDOS, col));
            if (rev) baraja.add(new Carta(Valor.REVERSA, col));
            if (pro) baraja.add(new Carta(Valor.PROHIBIDO, col));
        }

        if (m4) {
            for (int i = 0; i < 4; i++) baraja.add(new Carta(Valor.MASCUATRO, Colores.NEGRO));
        }
        if (cc) {
            for (int i = 0; i < 4; i++) baraja.add(new Carta(Valor.CAMBIOCOLOR, Colores.NEGRO));
        }
    }

    public Carta sacarCartaInicialValida() {
        for (int i = 0; i < baraja.size(); i++) {
            Carta c = baraja.get(i);
            if (c.getColor() != Colores.NEGRO && esNumerica(c.getValor())) {
                return baraja.remove(i); 
            }
        }
        return baraja.isEmpty() ? null : baraja.remove(0);
    }

    private boolean esNumerica(Valor valor) {
        return valor.ordinal() <= Valor.NUEVE.ordinal();
    }

    public Carta tomarUnaCarta() {
        if (baraja.isEmpty()) return null;
        return baraja.remove(0);
    }
    
    public List<Carta> tomarDosCartas() {
        return tomarVariasCartas(2);
    }
    
    public List<Carta> tomarCuatroCartas() {
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
        return cartasTomadas;
    }
    
    public int getCantidadCartas() { 
        return baraja.size(); 
    }

    public boolean estaVacio() { 
        return baraja.isEmpty(); 
    }
}