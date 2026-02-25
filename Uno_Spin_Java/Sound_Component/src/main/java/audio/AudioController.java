/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package audio;
/**
 * 
 * @author lagar
 */
public class AudioController {
    private static SoundManager sound;
    public static void init() {
        sound = new SoundManager();
        sound.loadMusic("/sound/s.wav");
        sound.loadEffect("tirar", "/sound/tirar.wav", 5);
        sound.loadEffect("jalar", "/sound/jalar.wav", 5);
        sound.loadEffect("uno", "/sound/uno.wav", 5);
        sound.loadEffect("alerta", "/sound/alerta.wav", 5);
        //sound.playMusicLoop();
    }
    public static void playMusic() {
        if (sound != null) {
            sound.playMusicLoop();
        }
    }

    public static void stopMusic() {
        if (sound != null) {
            sound.stopMusic();
        }
    }
    
    public static void playEffect(String name) {
        if (sound != null) {
            sound.playEffect(name);
        }
    }
}

