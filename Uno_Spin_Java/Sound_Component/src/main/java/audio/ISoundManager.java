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
    public void loadEffect(String name, String path, int poolSize);
    public boolean validateWav(String path);
    public void playEffect(String name);
    public void loadMusic(String path);
    public void playMusicLoop();
    public void stopMusic();

}
