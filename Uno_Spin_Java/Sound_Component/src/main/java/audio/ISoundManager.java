/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package audio;
/**
 * 
 * @author lagar
 */
public interface ISoundManager {
    void loadEffect(String name, String path, int poolSize);

    boolean validateWav(String path);

    void playEffect(String name);

    void loadMusic(String path);

    void playMusicLoop();

    void stopMusic();
}