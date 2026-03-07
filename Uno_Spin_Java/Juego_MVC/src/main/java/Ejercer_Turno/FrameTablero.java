/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class FrameTablero extends JFrame {
    private PanelTablero panelContenedor;
    private Tablero tableroEntidad;

    public FrameTablero(Jugador j1, Jugador j2, Jugador j3, Jugador j4, 
                        int rangoInicio, int rangoFinal, boolean masDos, boolean prohibido, 
                        boolean reversa, boolean masCuatro, boolean cambioColor, 
                        Color cAzul, Color cRojo, Color cAmarillo, Color cVerde, Color cNegro, 
                        int numCartas) {
        
        setTitle("UNO SPIN - MVC Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1200, 750);
        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(j1);
        listaJugadores.add(j2);
        listaJugadores.add(j3);
        listaJugadores.add(j4);
        tableroEntidad = new Tablero(listaJugadores, rangoInicio, rangoFinal, 
                                     masDos, prohibido, reversa, masCuatro, cambioColor, 
                                     cAzul, cRojo, cAmarillo, cVerde, cNegro);
        panelContenedor = new PanelTablero(tableroEntidad, j1, j2, j3, j4, numCartas);
        add(panelContenedor);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
