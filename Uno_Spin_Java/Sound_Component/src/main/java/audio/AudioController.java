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

    private AudioModel model;

    public AudioController(AudioModel model) {
        this.model = model;
    }

    public void playMusic() {
        model.getSoundManager().playMusicLoop();
    }

    public void stopMusic() {
        model.getSoundManager().stopMusic();
    }

    public void playEffect(String name) {
        model.getSoundManager().playEffect(name);
    }
}
