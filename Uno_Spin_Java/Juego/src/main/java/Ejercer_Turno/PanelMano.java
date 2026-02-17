/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;


import java.awt.Color;
import java.awt.Dimension;

import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author lagar
 */
public class PanelMano extends JPanel{
    private List<PanelCarta> cartas;
    
    public PanelMano(List<PanelCarta> cartas) {
        this.cartas = cartas;
        setBackground(Color.RED);
        setPreferredSize(new Dimension(800,120));
        setLayout(null);
        this.cartas.get(0).setBounds(0,0,100,120);
        add(cartas.get(0));
    }
    
    
}
