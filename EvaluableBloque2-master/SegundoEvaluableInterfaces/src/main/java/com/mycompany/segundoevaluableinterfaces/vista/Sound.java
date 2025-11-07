/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.segundoevaluableinterfaces.vista;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author pablo
 */
public class Sound {
    
    //Clip clip; 
    URL soundURL[] = new URL[100]; 
    private final Map<Integer, Clip> clips = new HashMap<>();
    public float volumeMusic; 
    public float volumeEffect; 
    
    public Sound(){ 
        
        soundURL[1] = getClass().getResource("/sonido/PlanB.wav"); 
        soundURL[2] = getClass().getResource("/sonido/Coger.wav"); 
        soundURL[3] = getClass().getResource("/sonido/EntrarMenu.wav"); 
        soundURL[4] = getClass().getResource("/sonido/Hablando.wav"); 
        soundURL[5] = getClass().getResource("/sonido/pokemon_battle.wav"); // Batalla pokemon 
        soundURL[6] = getClass().getResource("/sonido/BattleMewTwo.wav");
        soundURL[7] = getClass().getResource("/sonido/MainTheme.wav");
        soundURL[8] = getClass().getResource("/sonido/Ataque_Normal.wav");
        soundURL[9] = getClass().getResource("/sonido/Ataque_Critico.wav");
        soundURL[10] = getClass().getResource("/sonido/Pisar_Hierba.wav");
        soundURL[11] = getClass().getResource("/sonido/Curados.wav");
        soundURL[12] = getClass().getResource("/sonido/Salto.wav");
        soundURL[13] = getClass().getResource("/sonido/Primer_Pokemon.wav");
        soundURL[14] = getClass().getResource("/sonido/Andar_Por_Hierba.wav");
        
        this.volumeMusic = -20.0f; 
        this.volumeEffect = -20.0f; 
    }
    
    
    public void play(int i, boolean loop, String accion){
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i])) {
            if(accion.equals("music")){
                Clip newClip = AudioSystem.getClip();
                newClip.open(ais);

                FloatControl audioVolume = (FloatControl) newClip.getControl(FloatControl.Type.MASTER_GAIN);
                audioVolume.setValue(volumeMusic);

                if (loop) {
                    newClip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    newClip.start();
                }

                clips.put(i, newClip); 
            }else if(accion.equals("effect")){
                Clip newClip = AudioSystem.getClip();
                newClip.open(ais);

                FloatControl audioVolume = (FloatControl) newClip.getControl(FloatControl.Type.MASTER_GAIN);
                audioVolume.setValue(volumeEffect);

                if (loop) {
                    newClip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    newClip.start();
                }

                clips.put(i, newClip); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    
    public void play(){
        if(clip != null ){
            clip.start();
        }
    }
    
    public void loop(){
        if(clip != null){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    */
    public void stop(int i) {
        Clip c = clips.get(i);
        if (c != null && c.isRunning()) {
            c.stop();
            c.close();
        }
    }

    
    
}
