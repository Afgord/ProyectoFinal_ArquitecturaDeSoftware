/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package audio;
/**
 * 
 * @author lagar
 */
public class AudioModel {

    private ISoundManager soundManager;
    private AudioController controller;

    public AudioModel() {
        soundManager = new SoundManager();
        controller = new AudioController(this);
        init();
    }

    private void init() {
        soundManager.loadMusic("/sound/s.wav");
        soundManager.loadEffect("tirar", "/sound/tirar.wav", 5);
        soundManager.loadEffect("jalar", "/sound/jalar.wav", 5);
        soundManager.loadEffect("uno", "/sound/uno.wav", 5);
        soundManager.loadEffect("alerta", "/sound/alerta.wav", 5);
    }

    ISoundManager getSoundManager() {
        return soundManager;
    }

    public void playMusic() {
        controller.playMusic();
    }

    public void stopMusic() {
        controller.stopMusic();
    }

    public void playEffect(String name) {
        controller.playEffect(name);
    }
}
