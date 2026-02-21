/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author lagar
 */
public class FrameTablero extends JFrame{
    private JPanel panelContenedor;
    
    public FrameTablero(Jugador jugador1,Jugador jugador2,Jugador jugador3,Jugador jugador4,int rangoInicio, int rangoFinal,boolean masDos, boolean prohibido, boolean reversa, boolean masCuatro, boolean cambioColor,Color cambioAzul, Color cambioRojo, Color cambioAmarillo, Color cambioVerde, Color cambioNegro, int numCartas) {
        setTitle("UNO SPIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1200,750);
        panelContenedor = new PanelTablero(jugador1, jugador2, jugador3, jugador4,rangoInicio, rangoFinal,masDos,prohibido,reversa,masCuatro,cambioColor,cambioAzul, cambioRojo, cambioAmarillo,cambioVerde, cambioNegro, numCartas);
        add(panelContenedor);
        setLocationRelativeTo(null);
        mostrar();
    }
    
    private void mostrar(){
        setVisible(true);
    }
}
