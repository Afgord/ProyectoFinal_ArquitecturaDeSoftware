/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lagar
 */
public class Mazo {
    private final List<PanelCarta> baraja;

    public Mazo() {
        baraja = new ArrayList<>();
        generarMazoCompleto(baraja);
        Collections.shuffle(baraja);
    }
    
    public List<PanelCarta> tomarCartas(int cantidad) {
        if (cantidad > baraja.size()) {
            throw new IllegalArgumentException("No hay suficientes cartas en el mazo");
        }
        Collections.shuffle(baraja);
        List<PanelCarta> cartasTomadas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            cartasTomadas.add(baraja.remove(0)); 
        }
        System.out.print(cartasTomadas.size());
        return cartasTomadas;
    }
    
    public PanelCarta tomarUnaCarta() {
        if (baraja.isEmpty()) {
            throw new IllegalStateException("El mazo está vacío");
        }

        return baraja.remove(0); 
    }
    
    private void generarMazoCompleto(List<PanelCarta> baraja){
        baraja.add(new PanelCarta("cero",Color.BLUE,"azul","/cartas/cero.png", true));
        baraja.add(new PanelCarta("cero",Color.BLUE,"azul","/cartas/cero.png", true));
        baraja.add(new PanelCarta("uno",Color.BLUE,"azul","/cartas/uno.png", true));
        baraja.add(new PanelCarta("uno",Color.BLUE,"azul","/cartas/uno.png", true));
        baraja.add(new PanelCarta("dos",Color.BLUE,"azul","/cartas/dos.png", true));
        baraja.add(new PanelCarta("dos",Color.BLUE,"azul","/cartas/dos.png", true));
        baraja.add(new PanelCarta("tres",Color.BLUE,"azul","/cartas/tres.png", true));
        baraja.add(new PanelCarta("tres",Color.BLUE,"azul","/cartas/tres.png", true));
        baraja.add(new PanelCarta("cuatro",Color.BLUE,"azul","/cartas/cuatro.png", true));
        baraja.add(new PanelCarta("cuatro",Color.BLUE,"azul","/cartas/cuatro.png", true));
        baraja.add(new PanelCarta("cinco",Color.BLUE,"azul","/cartas/cinco.png", true));
        baraja.add(new PanelCarta("cinco",Color.BLUE,"azul","/cartas/cinco.png", true));
        baraja.add(new PanelCarta("seis",Color.BLUE,"azul","/cartas/seis.png", true));
        baraja.add(new PanelCarta("seis",Color.BLUE,"azul","/cartas/seis.png", true));
        baraja.add(new PanelCarta("siete",Color.BLUE,"azul","/cartas/siete.png", true));
        baraja.add(new PanelCarta("siete",Color.BLUE,"azul","/cartas/siete.png", true));
        baraja.add(new PanelCarta("ocho",Color.BLUE,"azul","/cartas/ocho.png", true));
        baraja.add(new PanelCarta("ocho",Color.BLUE,"azul","/cartas/ocho.png", true));
        baraja.add(new PanelCarta("nueve",Color.BLUE,"azul","/cartas/nueve.png", true));
        baraja.add(new PanelCarta("nueve",Color.BLUE,"azul","/cartas/nueve.png", true));
        baraja.add(new PanelCarta("mas_dos",Color.BLUE,"azul","/cartas/mas_dos.png", true));
        baraja.add(new PanelCarta("mas_dos",Color.BLUE,"azul","/cartas/mas_dos.png", true));
        baraja.add(new PanelCarta("prohibido",Color.BLUE,"azul","/cartas/prohibido.png", true));
        baraja.add(new PanelCarta("prohibido",Color.BLUE,"azul","/cartas/prohibido.png", true));
        baraja.add(new PanelCarta("reversa",Color.BLUE,"azul","/cartas/reversa.png", true));
        baraja.add(new PanelCarta("reversa",Color.BLUE,"azul","/cartas/reversa.png", true));
        baraja.add(new PanelCarta("cero",Color.RED,"rojo","/cartas/cero.png", true));
        baraja.add(new PanelCarta("cero",Color.RED,"rojo","/cartas/cero.png", true));
        baraja.add(new PanelCarta("uno",Color.RED,"rojo","/cartas/uno.png", true));
        baraja.add(new PanelCarta("uno",Color.RED,"rojo","/cartas/uno.png", true));
        baraja.add(new PanelCarta("dos",Color.RED,"rojo","/cartas/dos.png", true));
        baraja.add(new PanelCarta("dos",Color.RED,"rojo","/cartas/dos.png", true));
        baraja.add(new PanelCarta("tres",Color.RED,"rojo","/cartas/tres.png", true));
        baraja.add(new PanelCarta("tres",Color.RED,"rojo","/cartas/tres.png", true));
        baraja.add(new PanelCarta("cuatro",Color.RED,"rojo","/cartas/cuatro.png", true));
        baraja.add(new PanelCarta("cuatro",Color.RED,"rojo","/cartas/cuatro.png", true));
        baraja.add(new PanelCarta("cinco",Color.RED,"rojo","/cartas/cinco.png", true));
        baraja.add(new PanelCarta("cinco",Color.RED,"rojo","/cartas/cinco.png", true));
        baraja.add(new PanelCarta("seis",Color.RED,"rojo","/cartas/seis.png", true));
        baraja.add(new PanelCarta("seis",Color.RED,"rojo","/cartas/seis.png", true));
        baraja.add(new PanelCarta("siete",Color.RED,"rojo","/cartas/siete.png", true));
        baraja.add(new PanelCarta("siete",Color.RED,"rojo","/cartas/siete.png", true));
        baraja.add(new PanelCarta("ocho",Color.RED,"rojo","/cartas/ocho.png", true));
        baraja.add(new PanelCarta("ocho",Color.RED,"rojo","/cartas/ocho.png", true));
        baraja.add(new PanelCarta("nueve",Color.RED,"rojo","/cartas/nueve.png", true));
        baraja.add(new PanelCarta("nueve",Color.RED,"rojo","/cartas/nueve.png", true));
        baraja.add(new PanelCarta("mas_dos",Color.RED,"rojo","/cartas/mas_dos.png", true));
        baraja.add(new PanelCarta("mas_dos",Color.RED,"rojo","/cartas/mas_dos.png", true));
        baraja.add(new PanelCarta("prohibido",Color.RED,"rojo","/cartas/prohibido.png", true));
        baraja.add(new PanelCarta("prohibido",Color.RED,"rojo","/cartas/prohibido.png", true));
        baraja.add(new PanelCarta("reversa",Color.RED,"rojo","/cartas/reversa.png", true));
        baraja.add(new PanelCarta("reversa",Color.RED,"rojo","/cartas/reversa.png", true));
        baraja.add(new PanelCarta("cero",Color.GREEN,"verde","/cartas/cero.png", true));
        baraja.add(new PanelCarta("cero",Color.GREEN,"verde","/cartas/cero.png", true));
        baraja.add(new PanelCarta("uno",Color.GREEN,"verde","/cartas/uno.png", true));
        baraja.add(new PanelCarta("uno",Color.GREEN,"verde","/cartas/uno.png", true));
        baraja.add(new PanelCarta("dos",Color.GREEN,"verde","/cartas/dos.png", true));
        baraja.add(new PanelCarta("dos",Color.GREEN,"verde","/cartas/dos.png", true));
        baraja.add(new PanelCarta("tres",Color.GREEN,"verde","/cartas/tres.png", true));
        baraja.add(new PanelCarta("tres",Color.GREEN,"verde","/cartas/tres.png", true));
        baraja.add(new PanelCarta("cuatro",Color.GREEN,"verde","/cartas/cuatro.png", true));
        baraja.add(new PanelCarta("cuatro",Color.GREEN,"verde","/cartas/cuatro.png", true));
        baraja.add(new PanelCarta("cinco",Color.GREEN,"verde","/cartas/cinco.png", true));
        baraja.add(new PanelCarta("cinco",Color.GREEN,"verde","/cartas/cinco.png", true));
        baraja.add(new PanelCarta("seis",Color.GREEN,"verde","/cartas/seis.png", true));
        baraja.add(new PanelCarta("seis",Color.GREEN,"verde","/cartas/seis.png", true));
        baraja.add(new PanelCarta("siete",Color.GREEN,"verde","/cartas/siete.png", true));
        baraja.add(new PanelCarta("siete",Color.GREEN,"verde","/cartas/siete.png", true));
        baraja.add(new PanelCarta("ocho",Color.GREEN,"verde","/cartas/ocho.png", true));
        baraja.add(new PanelCarta("ocho",Color.GREEN,"verde","/cartas/ocho.png", true));
        baraja.add(new PanelCarta("nueve",Color.GREEN,"verde","/cartas/nueve.png", true));
        baraja.add(new PanelCarta("nueve",Color.GREEN,"verde","/cartas/nueve.png", true));
        baraja.add(new PanelCarta("mas_dos",Color.GREEN,"verde","/cartas/mas_dos.png", true));
        baraja.add(new PanelCarta("mas_dos",Color.GREEN,"verde","/cartas/mas_dos.png", true));
        baraja.add(new PanelCarta("prohibido",Color.GREEN,"verde","/cartas/prohibido.png", true));
        baraja.add(new PanelCarta("prohibido",Color.GREEN,"verde","/cartas/prohibido.png", true));
        baraja.add(new PanelCarta("reversa",Color.GREEN,"verde","/cartas/reversa.png", true));
        baraja.add(new PanelCarta("reversa",Color.GREEN,"verde","/cartas/reversa.png", true));
        baraja.add(new PanelCarta("cero",Color.YELLOW,"amarillo","/cartas/cero.png", true));
        baraja.add(new PanelCarta("cero",Color.YELLOW,"amarillo","/cartas/cero.png", true));
        baraja.add(new PanelCarta("uno",Color.YELLOW,"amarillo","/cartas/uno.png", true));
        baraja.add(new PanelCarta("uno",Color.YELLOW,"amarillo","/cartas/uno.png", true));
        baraja.add(new PanelCarta("dos",Color.YELLOW,"amarillo","/cartas/dos.png", true));
        baraja.add(new PanelCarta("dos",Color.YELLOW,"amarillo","/cartas/dos.png", true));
        baraja.add(new PanelCarta("tres",Color.YELLOW,"amarillo","/cartas/tres.png", true));
        baraja.add(new PanelCarta("tres",Color.YELLOW,"amarillo","/cartas/tres.png", true));
        baraja.add(new PanelCarta("cuatro",Color.YELLOW,"amarillo","/cartas/cuatro.png", true));
        baraja.add(new PanelCarta("cuatro",Color.YELLOW,"amarillo","/cartas/cuatro.png", true));
        baraja.add(new PanelCarta("cinco",Color.YELLOW,"amarillo","/cartas/cinco.png", true));
        baraja.add(new PanelCarta("cinco",Color.YELLOW,"amarillo","/cartas/cinco.png", true));
        baraja.add(new PanelCarta("seis",Color.YELLOW,"amarillo","/cartas/seis.png", true));
        baraja.add(new PanelCarta("seis",Color.YELLOW,"amarillo","/cartas/seis.png", true));
        baraja.add(new PanelCarta("siete",Color.YELLOW,"amarillo","/cartas/siete.png", true));
        baraja.add(new PanelCarta("siete",Color.YELLOW,"amarillo","/cartas/siete.png", true));
        baraja.add(new PanelCarta("ocho",Color.YELLOW,"amarillo","/cartas/ocho.png", true));
        baraja.add(new PanelCarta("ocho",Color.YELLOW,"amarillo","/cartas/ocho.png", true));
        baraja.add(new PanelCarta("nueve",Color.YELLOW,"amarillo","/cartas/nueve.png", true));
        baraja.add(new PanelCarta("nueve",Color.YELLOW,"amarillo","/cartas/nueve.png", true));
        baraja.add(new PanelCarta("mas_dos",Color.YELLOW,"amarillo","/cartas/mas_dos.png", true));
        baraja.add(new PanelCarta("mas_dos",Color.YELLOW,"amarillo","/cartas/mas_dos.png", true));
        baraja.add(new PanelCarta("prohibido",Color.YELLOW,"amarillo","/cartas/prohibido.png", true));
        baraja.add(new PanelCarta("prohibido",Color.YELLOW,"amarillo","/cartas/prohibido.png", true));
        baraja.add(new PanelCarta("reversa",Color.YELLOW,"amarillo","/cartas/reversa.png", true));
        baraja.add(new PanelCarta("reversa",Color.YELLOW,"amarillo","/cartas/reversa.png", true));
        baraja.add(new PanelCarta("mas_cuatro",Color.BLACK,"negro","/cartas/mas_cuatro.png", true));
        baraja.add(new PanelCarta("mas_cuatro",Color.BLACK,"negro","/cartas/mas_cuatro.png", true));
        baraja.add(new PanelCarta("mas_cuatro",Color.BLACK,"negro","/cartas/mas_cuatro.png", true));
        baraja.add(new PanelCarta("mas_cuatro",Color.BLACK,"negro","/cartas/mas_cuatro.png", true));
        baraja.add(new PanelCarta("cambio_color",Color.BLACK,"negro","/cartas/cambio_color.png", true));
        baraja.add(new PanelCarta("cambio_color",Color.BLACK,"negro","/cartas/cambio_color.png", true));
        baraja.add(new PanelCarta("cambio_color",Color.BLACK,"negro","/cartas/cambio_color.png", true));
        baraja.add(new PanelCarta("cambio_color",Color.BLACK,"negro","/cartas/cambio_color.png", true));    
    }
}
