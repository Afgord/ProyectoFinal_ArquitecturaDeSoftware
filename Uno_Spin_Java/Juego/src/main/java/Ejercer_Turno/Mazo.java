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

    public Mazo(int rangoInicio, int rangoFinal,boolean masDos, boolean prohibido, boolean reversa, boolean masCuatro, boolean cambioColor, Color cambioAzul, Color cambioRojo, Color cambioAmarillo, Color cambioVerde, Color cambioNegro) {
        baraja = new ArrayList<>();
        generarMazoCompleto(rangoInicio, rangoFinal,masDos,prohibido,reversa,masCuatro,cambioColor,baraja,cambioAzul, cambioRojo, cambioAmarillo,cambioVerde, cambioNegro);
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

    public List<PanelCarta> getBaraja() {
        return baraja;
    }
  
    private void generarMazoCompleto(int rangoInicio,int rangoFinal,boolean masDos, boolean prohibido, boolean reversa, boolean masCuatro, boolean cambioColor,List<PanelCarta> baraja, Color cambioAzul, Color cambioRojo, Color cambioAmarillo, Color cambioVerde, Color cambioNegro){
        if(0 >= rangoInicio && 0 <= rangoFinal){
            baraja.add(new PanelCarta("0",cambioAzul,"azul","/cartas/cero.png", true));
            baraja.add(new PanelCarta("0",cambioAzul,"azul","/cartas/cero.png", true));
            baraja.add(new PanelCarta("0",cambioRojo,"rojo","/cartas/cero.png", true));
            baraja.add(new PanelCarta("0",cambioRojo,"rojo","/cartas/cero.png", true));
            baraja.add(new PanelCarta("0",cambioAmarillo,"amarillo","/cartas/cero.png", true));
            baraja.add(new PanelCarta("0",cambioAmarillo,"amarillo","/cartas/cero.png", true));
            baraja.add(new PanelCarta("0",cambioVerde,"verde","/cartas/cero.png", true));
            baraja.add(new PanelCarta("0",cambioVerde,"verde","/cartas/cero.png", true));
        }
        
        if(1 >= rangoInicio && 1 <= rangoFinal){
            baraja.add(new PanelCarta("1",cambioVerde,"verde","/cartas/uno.png", true));
            baraja.add(new PanelCarta("1",cambioVerde,"verde","/cartas/uno.png", true));
            baraja.add(new PanelCarta("1",cambioAzul,"azul","/cartas/uno.png", true));
            baraja.add(new PanelCarta("1",cambioAzul,"azul","/cartas/uno.png", true));
            baraja.add(new PanelCarta("1",cambioRojo,"rojo","/cartas/uno.png", true));
            baraja.add(new PanelCarta("1",cambioRojo,"rojo","/cartas/uno.png", true));
            baraja.add(new PanelCarta("1",cambioAmarillo,"amarillo","/cartas/uno.png", true));
            baraja.add(new PanelCarta("1",cambioAmarillo,"amarillo","/cartas/uno.png", true));
        }
        
        if(2 >= rangoInicio && 2 <= rangoFinal){
            baraja.add(new PanelCarta("2",cambioAzul,"azul","/cartas/dos.png", true));
            baraja.add(new PanelCarta("2",cambioAzul,"azul","/cartas/dos.png", true));
            baraja.add(new PanelCarta("2",cambioRojo,"rojo","/cartas/dos.png", true));
            baraja.add(new PanelCarta("2",cambioRojo,"rojo","/cartas/dos.png", true));
            baraja.add(new PanelCarta("2",cambioVerde,"verde","/cartas/dos.png", true));
            baraja.add(new PanelCarta("2",cambioVerde,"verde","/cartas/dos.png", true));
            baraja.add(new PanelCarta("2",cambioAmarillo,"amarillo","/cartas/dos.png", true));
            baraja.add(new PanelCarta("2",cambioAmarillo,"amarillo","/cartas/dos.png", true));
        }
            
        if(3 >= rangoInicio && 3 <= rangoFinal){
            baraja.add(new PanelCarta("3",cambioAzul,"azul","/cartas/tres.png", true));
            baraja.add(new PanelCarta("3",cambioAzul,"azul","/cartas/tres.png", true));
            baraja.add(new PanelCarta("3",cambioRojo,"rojo","/cartas/tres.png", true));
            baraja.add(new PanelCarta("3",cambioRojo,"rojo","/cartas/tres.png", true));
            baraja.add(new PanelCarta("3",cambioVerde,"verde","/cartas/tres.png", true));
            baraja.add(new PanelCarta("3",cambioVerde,"verde","/cartas/tres.png", true));
            baraja.add(new PanelCarta("3",cambioAmarillo,"amarillo","/cartas/tres.png", true));
            baraja.add(new PanelCarta("3",cambioAmarillo,"amarillo","/cartas/tres.png", true));
        }
        
        if(4 >= rangoInicio && 4 <= rangoFinal){
            baraja.add(new PanelCarta("4",cambioAzul,"azul","/cartas/cuatro.png", true));
            baraja.add(new PanelCarta("4",cambioAzul,"azul","/cartas/cuatro.png", true));
            baraja.add(new PanelCarta("4",cambioRojo,"rojo","/cartas/cuatro.png", true));
            baraja.add(new PanelCarta("4",cambioRojo,"rojo","/cartas/cuatro.png", true));
            baraja.add(new PanelCarta("4",cambioVerde,"verde","/cartas/cuatro.png", true));
            baraja.add(new PanelCarta("4",cambioVerde,"verde","/cartas/cuatro.png", true));
            baraja.add(new PanelCarta("4",cambioAmarillo,"amarillo","/cartas/cuatro.png", true));
            baraja.add(new PanelCarta("4",cambioAmarillo,"amarillo","/cartas/cuatro.png", true));
        }
        
        if(5 >= rangoInicio && 5 <= rangoFinal){
            baraja.add(new PanelCarta("5",cambioAzul,"azul","/cartas/cinco.png", true));
            baraja.add(new PanelCarta("5",cambioAzul,"azul","/cartas/cinco.png", true));
            baraja.add(new PanelCarta("5",cambioRojo,"rojo","/cartas/cinco.png", true));
            baraja.add(new PanelCarta("5",cambioRojo,"rojo","/cartas/cinco.png", true));
            baraja.add(new PanelCarta("5",cambioVerde,"verde","/cartas/cinco.png", true));
            baraja.add(new PanelCarta("5",cambioVerde,"verde","/cartas/cinco.png", true));
            baraja.add(new PanelCarta("5",cambioAmarillo,"amarillo","/cartas/cinco.png", true));
            baraja.add(new PanelCarta("5",cambioAmarillo,"amarillo","/cartas/cinco.png", true));
        }
        
        if(6 >= rangoInicio && 6 <= rangoFinal){
            baraja.add(new PanelCarta("6",cambioAzul,"azul","/cartas/seis.png", true));
            baraja.add(new PanelCarta("6",cambioAzul,"azul","/cartas/seis.png", true));
            baraja.add(new PanelCarta("6",cambioRojo,"rojo","/cartas/seis.png", true));
            baraja.add(new PanelCarta("6",cambioRojo,"rojo","/cartas/seis.png", true));
            baraja.add(new PanelCarta("6",cambioVerde,"verde","/cartas/seis.png", true));
            baraja.add(new PanelCarta("6",cambioVerde,"verde","/cartas/seis.png", true));
            baraja.add(new PanelCarta("6",cambioAmarillo,"amarillo","/cartas/seis.png", true));
            baraja.add(new PanelCarta("6",cambioAmarillo,"amarillo","/cartas/seis.png", true));
        }
        
        if(7 >= rangoInicio && 7 <= rangoFinal){
            baraja.add(new PanelCarta("7",cambioAzul,"azul","/cartas/siete.png", true));
            baraja.add(new PanelCarta("7",cambioAzul,"azul","/cartas/siete.png", true));
            baraja.add(new PanelCarta("7",cambioRojo,"rojo","/cartas/siete.png", true));
            baraja.add(new PanelCarta("7",cambioRojo,"rojo","/cartas/siete.png", true));
            baraja.add(new PanelCarta("7",cambioVerde,"verde","/cartas/siete.png", true));
            baraja.add(new PanelCarta("7",cambioVerde,"verde","/cartas/siete.png", true));
            baraja.add(new PanelCarta("7",cambioAmarillo,"amarillo","/cartas/siete.png", true));
            baraja.add(new PanelCarta("7",cambioAmarillo,"amarillo","/cartas/siete.png", true));
        }
        
        if(8 >= rangoInicio && 8 <= rangoFinal){
            baraja.add(new PanelCarta("8",cambioAzul,"azul","/cartas/ocho.png", true));
            baraja.add(new PanelCarta("8",cambioAzul,"azul","/cartas/ocho.png", true));
            baraja.add(new PanelCarta("8",cambioRojo,"rojo","/cartas/ocho.png", true));
            baraja.add(new PanelCarta("8",cambioRojo,"rojo","/cartas/ocho.png", true));
            baraja.add(new PanelCarta("8",cambioVerde,"verde","/cartas/ocho.png", true));
            baraja.add(new PanelCarta("8",cambioVerde,"verde","/cartas/ocho.png", true));
            baraja.add(new PanelCarta("8",cambioAmarillo,"amarillo","/cartas/ocho.png", true));
            baraja.add(new PanelCarta("8",cambioAmarillo,"amarillo","/cartas/ocho.png", true));
        }
        
        if(9 >= rangoInicio && 9 <= rangoFinal){
            baraja.add(new PanelCarta("9",cambioAzul,"azul","/cartas/nueve.png", true));
            baraja.add(new PanelCarta("9",cambioAzul,"azul","/cartas/nueve.png", true));
            baraja.add(new PanelCarta("9",cambioRojo,"rojo","/cartas/nueve.png", true));
            baraja.add(new PanelCarta("9",cambioRojo,"rojo","/cartas/nueve.png", true));
            baraja.add(new PanelCarta("9",cambioVerde,"verde","/cartas/nueve.png", true));
            baraja.add(new PanelCarta("9",cambioVerde,"verde","/cartas/nueve.png", true));
            baraja.add(new PanelCarta("9",cambioAmarillo,"amarillo","/cartas/nueve.png", true));
            baraja.add(new PanelCarta("9",cambioAmarillo,"amarillo","/cartas/nueve.png", true));
        }
        
        if(masDos == true){
            baraja.add(new PanelCarta("mas_dos",cambioAzul,"azul","/cartas/mas_dos.png", true));
            baraja.add(new PanelCarta("mas_dos",cambioAzul,"azul","/cartas/mas_dos.png", true));
            baraja.add(new PanelCarta("mas_dos",cambioRojo,"rojo","/cartas/mas_dos.png", true));
            baraja.add(new PanelCarta("mas_dos",cambioRojo,"rojo","/cartas/mas_dos.png", true));
            baraja.add(new PanelCarta("mas_dos",cambioVerde,"verde","/cartas/mas_dos.png", true));
            baraja.add(new PanelCarta("mas_dos",cambioVerde,"verde","/cartas/mas_dos.png", true));
            baraja.add(new PanelCarta("mas_dos",cambioAmarillo,"amarillo","/cartas/mas_dos.png", true));
            baraja.add(new PanelCarta("mas_dos",cambioAmarillo,"amarillo","/cartas/mas_dos.png", true));
        }
        
        if(prohibido == true){
            baraja.add(new PanelCarta("prohibido",cambioAzul,"azul","/cartas/prohibido.png", true));
            baraja.add(new PanelCarta("prohibido",cambioAzul,"azul","/cartas/prohibido.png", true));
            baraja.add(new PanelCarta("prohibido",cambioRojo,"rojo","/cartas/prohibido.png", true));
            baraja.add(new PanelCarta("prohibido",cambioRojo,"rojo","/cartas/prohibido.png", true));
            baraja.add(new PanelCarta("prohibido",cambioVerde,"verde","/cartas/prohibido.png", true));
            baraja.add(new PanelCarta("prohibido",cambioVerde,"verde","/cartas/prohibido.png", true));
            baraja.add(new PanelCarta("prohibido",cambioAmarillo,"amarillo","/cartas/prohibido.png", true));
            baraja.add(new PanelCarta("prohibido",cambioAmarillo,"amarillo","/cartas/prohibido.png", true));
        }
        
        if(reversa == true){
            baraja.add(new PanelCarta("reversa",cambioAzul,"azul","/cartas/reversa.png", true));
            baraja.add(new PanelCarta("reversa",cambioAzul,"azul","/cartas/reversa.png", true));
            baraja.add(new PanelCarta("reversa",cambioRojo,"rojo","/cartas/reversa.png", true));
            baraja.add(new PanelCarta("reversa",cambioRojo,"rojo","/cartas/reversa.png", true));
            baraja.add(new PanelCarta("reversa",cambioAmarillo,"verde","/cartas/reversa.png", true));
            baraja.add(new PanelCarta("reversa",cambioAmarillo,"verde","/cartas/reversa.png", true));
            baraja.add(new PanelCarta("reversa",cambioAmarillo,"amarillo","/cartas/reversa.png", true));
            baraja.add(new PanelCarta("reversa",cambioAmarillo,"amarillo","/cartas/reversa.png", true));
        }
        
        if(masCuatro == true){
            baraja.add(new PanelCarta("mas_cuatro",cambioNegro,"negro","/cartas/mas_cuatro.png", true));
            baraja.add(new PanelCarta("mas_cuatro",cambioNegro,"negro","/cartas/mas_cuatro.png", true));
            baraja.add(new PanelCarta("mas_cuatro",cambioNegro,"negro","/cartas/mas_cuatro.png", true));
            baraja.add(new PanelCarta("mas_cuatro",cambioNegro,"negro","/cartas/mas_cuatro.png", true));
        }
            
        if(cambioColor == true){
            baraja.add(new PanelCarta("cambio_color",cambioNegro,"negro","/cartas/cambio_color.png", true));
            baraja.add(new PanelCarta("cambio_color",cambioNegro,"negro","/cartas/cambio_color.png", true));
            baraja.add(new PanelCarta("cambio_color",cambioNegro,"negro","/cartas/cambio_color.png", true));
            baraja.add(new PanelCarta("cambio_color",cambioNegro,"negro","/cartas/cambio_color.png", true));   
        }
    }
}
