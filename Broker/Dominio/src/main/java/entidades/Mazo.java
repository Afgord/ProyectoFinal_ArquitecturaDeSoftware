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

    private void generarCartas(int rI, int rF, boolean m2, boolean pro, boolean rev,
                               boolean m4, boolean cc) {

        String[] nombresColores = {"azul", "rojo", "amarillo", "verde"};
        String[] nombresArchivos = {
            "cero", "uno", "dos", "tres", "cuatro",
            "cinco", "seis", "siete", "ocho", "nueve"
        };
        
        for (int n = rI; n <= rF; n++) {
            for (int i = 0; i < 4; i++) {
                String ruta = "/cartas/" + nombresArchivos[n] + ".png";
                baraja.add(new CartaNumerica(String.valueOf(n), nombresColores[i], ruta));
                baraja.add(new CartaNumerica(String.valueOf(n), nombresColores[i], ruta));
            }
        }

        for (int i = 0; i < 4; i++) {
            if (m2) {
                baraja.add(new CartaAccion("+2", nombresColores[i], "/cartas/mas_dos.png"));
                baraja.add(new CartaAccion("+2", nombresColores[i], "/cartas/mas_dos.png"));
            }
            if (rev) {
                baraja.add(new CartaAccion("REV", nombresColores[i], "/cartas/reversa.png"));
                baraja.add(new CartaAccion("REV", nombresColores[i], "/cartas/reversa.png"));
            }
            if (pro) {
                baraja.add(new CartaAccion("PRO", nombresColores[i], "/cartas/prohibido.png"));
                baraja.add(new CartaAccion("PRO", nombresColores[i], "/cartas/prohibido.png"));
            }
        }

        if (m4) {
            for (int i = 0; i < 8; i++) {
                baraja.add(new CartaComodin("+4", "/cartas/mas_cuatro.png"));
            }
        }
        if (cc) {
            for (int i = 0; i < 8; i++) {
                baraja.add(new CartaComodin("CC", "/cartas/cambio_color.png"));
            }
        }
    }

    public Carta sacarCartaInicialValida() {
        for (int i = 0; i < baraja.size(); i++) {
            Carta c = baraja.get(i);
            if (!c.getColorInterno().equalsIgnoreCase("negro") && 
                c.getSimbolo().matches("[0-9]")) {
                return baraja.remove(i); 
            }
        }
        return baraja.remove(0);
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