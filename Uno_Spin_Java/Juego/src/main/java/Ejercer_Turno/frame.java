/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author lagar
 */
public class frame extends JFrame{
    private JPanel panelContenedor;
    
    public frame() {
        setTitle("UNO SPIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1200,750);
        panelContenedor = new panelTablero();
        add(panelContenedor);
        setLocationRelativeTo(null);
        mostrar();
    }
    
    private void mostrar(){
        setVisible(true);
    }
}
