/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class Mazo {
    private final List<Carta> baraja;
    private Color cAzul, cRojo, cAmarillo, cVerde;

    public Mazo(int rangoInicio, int rangoFinal, boolean masDos, boolean prohibido, 
                boolean reversa, boolean masCuatro, boolean cambioColor, 
                Color cAzul, Color cRojo, Color cAmarillo, Color cVerde, Color cNegro) {
        
        this.baraja = new ArrayList<>();
        this.cAzul = cAzul;
        this.cRojo = cRojo;
        this.cAmarillo = cAmarillo;
        this.cVerde = cVerde;
        
        generarCartas(rangoInicio, rangoFinal, masDos, prohibido, reversa, 
                      masCuatro, cambioColor, cAzul, cRojo, cAmarillo, cVerde, cNegro);
        
        Collections.shuffle(baraja);
    }

    private void generarCartas(int rI, int rF, boolean m2, boolean pro, boolean rev,
            boolean m4, boolean cc, Color cAzul, Color cRojo,
            Color cAmarillo, Color cVerde, Color cNegro) {

        Color[] coloresAwt = {cAzul, cRojo, cAmarillo, cVerde};
        String[] nombresColores = {"azul", "rojo", "amarillo", "verde"};
        String[] nombresArchivos = {
            "cero", "uno", "dos", "tres", "cuatro",
            "cinco", "seis", "siete", "ocho", "nueve"
        };

        for (int n = rI; n <= rF; n++) {
            for (int i = 0; i < 4; i++) {
                String ruta = "/cartas/" + nombresArchivos[n] + ".png";
                baraja.add(new Carta(String.valueOf(n), coloresAwt[i], nombresColores[i], ruta));
                baraja.add(new Carta(String.valueOf(n), coloresAwt[i], nombresColores[i], ruta));
            }
        }

        for (int i = 0; i < 4; i++) {
            if (m2) {
                baraja.add(new Carta("+2", coloresAwt[i], nombresColores[i], "/cartas/mas_dos.png"));
                baraja.add(new Carta("+2", coloresAwt[i], nombresColores[i], "/cartas/mas_dos.png"));
            }

            if (rev) {
                baraja.add(new Carta("REV", coloresAwt[i], nombresColores[i], "/cartas/reversa.png"));
                baraja.add(new Carta("REV", coloresAwt[i], nombresColores[i], "/cartas/reversa.png"));
            }

            if (pro) {
                baraja.add(new Carta("PRO", coloresAwt[i], nombresColores[i], "/cartas/prohibido.png"));
                baraja.add(new Carta("PRO", coloresAwt[i], nombresColores[i], "/cartas/prohibido.png"));
            }
        }

        if (m4) {
            for (int i = 0; i < 8; i++) {
                baraja.add(new Carta("+4", cNegro, "negro", "/cartas/mas_cuatro.png"));
            }
        }

        if (cc) {
            for (int i = 0; i < 8; i++) {
                baraja.add(new Carta("CC", cNegro, "negro", "/cartas/cambio_color.png"));
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
                try {
                    Thread.sleep(500); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
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

    public Color getcAzul() { return cAzul; }
    public Color getcRojo() { return cRojo; }
    public Color getcAmarillo() { return cAmarillo; }
    public Color getcVerde() { return cVerde; }
}