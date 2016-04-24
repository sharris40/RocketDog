/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author Sophia
 */
public class MusicPlayer {

    Media me;
    MediaPlayer mp;
    String soundDirectory = "src/Sounds/";
    double currentVolume;

    public MusicPlayer(String source) {
        loadMusicPlayer(source);
    }

    public MusicPlayer(String source, double v) {
        loadMusicPlayer(source);
        setVolumePlayer(v);

    }
    public void loadMusicPlayer(String source){
        createMedia(source);
        createPlayer();
        startPlayer();
        this.setVolumePlayer(currentVolume);
        setInfiniteLoop();
    }
    public void createMedia(String source) {
        me = new Media(new File(soundDirectory + source).toURI().toString());
    }

    public void createPlayer() {
        mp = new MediaPlayer(this.getMe());
    }

    public void startPlayer() {
        this.getMp().play();
    }

    public void stopPlayer() {
        this.getMp().stop();
    }

    public void mutePlayer() {
        currentVolume= this.getMp().getVolume();
        this.getMp().setMute(true);
    }

    public void unmutePlayer() {
        this.setVolumePlayer(currentVolume);
        this.getMp().setMute(false);
    }

    public void setVolumePlayer(double volume) {
        this.getMp().setVolume(volume);
        setCurrentVolume(volume);
    }

    public void setInfiniteLoop() {
        this.getMp().setOnEndOfMedia(new Runnable() {
            public void run() {
                mp.seek(Duration.ZERO);
            }
        });
        this.getMp().play();
    }

    public Media getMe() {
        return me;
    }

    public void setMe(Media me) {
        this.me = me;
    }

    public MediaPlayer getMp() {
        return mp;
    }

    public void setMp(MediaPlayer mp) {
        this.mp = mp;
    }

    public double getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(double currentVolume) {
        this.currentVolume = currentVolume;
    }

}
