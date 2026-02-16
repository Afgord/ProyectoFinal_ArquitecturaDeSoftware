/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno;

import javax.swing.JFrame;

/**
 *
 * @author lagar
 */
public class frame extends JFrame{

    public frame() {
        setTitle("UNO SPIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        PanelContenedor contenedor = new PanelContenedor();
        add(contenedor);

        pack();
        setLocationRelativeTo(null);
    }
    
}
