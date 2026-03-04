/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Girar_Ruleta;

import javax.swing.Timer;

/**
 *
 * @author lagar
 */
public class EjectuadorRuleta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FrameRuleta f = new FrameRuleta();
        new Timer(3000, e -> f.getPanelRuleta().detenerRuleta()).start();
    }
    
}
