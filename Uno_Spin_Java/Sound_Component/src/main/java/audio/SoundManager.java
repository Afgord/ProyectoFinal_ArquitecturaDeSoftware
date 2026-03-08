/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager implements ISoundManager {
    private final Map<String, SoundPool> soundEffects = new HashMap<>();
    private Clip backgroundMusic;

    @Override
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

    @Override
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

            System.out.println("Efecto cargado: " + name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playEffect(String name) {
        SoundPool pool = soundEffects.get(name);
        if (pool != null) {
            pool.play();
        }
    }
    @Override
    public void loadMusic(String path) {

        if (!validateWav(path)) return;

        try {
            URL url = getClass().getResource(path);
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);

            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(ais);

            System.out.println("Música cargada.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playMusicLoop() {
        if (backgroundMusic == null) return;

        Thread musicThread = new Thread(() -> {
            backgroundMusic.setFramePosition(0);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        });

        musicThread.setDaemon(true); 
        musicThread.start();
    }

    @Override
    public void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }
}