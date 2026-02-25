/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package audio;


/**
 *
 * @author lagar
 */
public class TestSound {
    public static void main(String[] args) {
        SoundManager sound = new SoundManager();
       
        sound.loadMusic("/sound/s.wav");
        sound.playMusicLoop();
        try {
            Thread.sleep(200000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
