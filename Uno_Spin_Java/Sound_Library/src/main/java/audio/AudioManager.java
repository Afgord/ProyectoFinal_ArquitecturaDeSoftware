/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author Luis Rafael
 */
public class AudioManager {

    private final Map<String, SoundPool> soundEffects = new HashMap<>();
    private Clip backgroundMusic;

    public AudioManager() {
        init();
    }

    private void init() {

        loadMusic("/sound/music/fnafw_bosses.wav");

        loadEffect("tirar", "/sound/effect/tirar.wav", 5);
        loadEffect("jalar", "/sound/effect/jalar.wav", 5);
        loadEffect("uno", "/sound/effect/uno.wav", 5);
        loadEffect("alerta", "/sound/effect/alerta.wav", 5);
    }

    public boolean validateWav(String path) {

        if (path == null || !path.toLowerCase().endsWith(".wav")) {
            System.err.println("No es un archivo WAV: " + path);
            return false;
        }

        try {

            URL url = getClass().getResource(path);

            if (url == null) {
                System.err.println("No se encontró el archivo: " + path);
                return false;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            ais.close();

            return true;

        } catch (Exception e) {

            System.err.println("WAV inválido o corrupto: " + path);
            return false;
        }
    }

    public void loadEffect(String name, String path, int poolSize) {

        if (!validateWav(path)) return;

        try {

            URL url = getClass().getResource(path);
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);

            AudioFormat format = ais.getFormat();
            byte[] audioData = ais.readAllBytes();
            ais.close();

            Clip[] clips = new Clip[poolSize];

            for (int i = 0; i < poolSize; i++) {

                Clip clip = AudioSystem.getClip();
                clip.open(format, audioData, 0, audioData.length);
                clips[i] = clip;
            }

            soundEffects.put(name, new SoundPool(clips));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playEffect(String name) {

        SoundPool pool = soundEffects.get(name);

        if (pool != null) {
            pool.play();
        }
    }

    public void loadMusic(String path) {

        if (!validateWav(path)) return;

        try {

            URL url = getClass().getResource(path);
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);

            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(ais);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusicLoop() {

        if (backgroundMusic == null) return;

        Thread musicThread = new Thread(() -> {

            backgroundMusic.setFramePosition(0);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);

        });

        musicThread.setDaemon(true);
        musicThread.start();
    }

    public void stopMusic() {

        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }
}