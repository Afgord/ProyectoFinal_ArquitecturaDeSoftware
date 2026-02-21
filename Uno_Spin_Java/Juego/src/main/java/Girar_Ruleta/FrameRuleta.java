/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Girar_Ruleta;

import javax.swing.JFrame;

/**
 *
 * @author lagar
 */
public class FrameRuleta extends JFrame{
    private PanelRuleta panelRuleta;

    public FrameRuleta() {
        setTitle("UNO SPIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400,400);
        panelRuleta = new PanelRuleta();
        add(panelRuleta);
        setLocationRelativeTo(null);
        mostrar();
    }
    
    private void mostrar(){
        setVisible(true);
    } 
}
