/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * @author Sophia
 */
public final class SoundManager {

    String soundDirectory = "src/Sounds/";
    ExecutorService soundPool = Executors.newFixedThreadPool(2);
    ArrayList<Sound> soundEffects;
    MusicInformation myMusicInformation;
    ArrayList<MusicInformation> myMusicPlayers;
    boolean musicEnabled;
    BgMusicPlayer bgMusicPlayer;
    AmMusicPlayer amMusicPlayer;
    String bgsource;
    String amsource;
    int levelNumber = 0;

    public SoundManager() {
        loadSounds();
        musicEnabled = true;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public SoundManager(int n) {
        soundPool = Executors.newFixedThreadPool(n);
    }

    public MusicInformation musicForLevel(int levelnumber) {

        if (myMusicPlayers.size() < levelnumber) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Music for this level IS non-Existant");
            alert.setContentText("Music from the last available music track will be loaded instead!");
            alert.showAndWait();
            bgsource = myMusicPlayers.get(myMusicPlayers.size()).getBg();
            amsource = myMusicPlayers.get(myMusicPlayers.size()).getAm();
            
        } else {
            bgsource = myMusicPlayers.get(levelnumber).getBg();
            amsource = myMusicPlayers.get(levelnumber).getAm();
        }
        myMusicInformation = new MusicInformation(bgsource, amsource);
        return myMusicInformation;
    }

    public void startGameMusicForLevel(int levelNumber) {
        double v1 = 0, v2 = 0;
        myMusicInformation = musicForLevel(levelNumber);

        if (levelNumber == 0) {
            bgMusicPlayer = new BgMusicPlayer(myMusicInformation.getBg());
            amMusicPlayer = new AmMusicPlayer(myMusicInformation.getAm());
            bgMusicPlayer.setVolumePlayer(0.2);
            amMusicPlayer.setVolumePlayer(0.2);
        }
        if (levelNumber > 0) {
            try{
            System.out.println("level greater than 0|");}
            catch(Exception e){
            e.printStackTrace();}
            v1 = bgMusicPlayer.getCurrentVolume();
            v2 = amMusicPlayer.getCurrentVolume();
            bgMusicPlayer.stopPlayer();
            bgMusicPlayer.getMp().dispose();
            amMusicPlayer.stopPlayer();
            amMusicPlayer.getMp().dispose();       
            bgMusicPlayer = new BgMusicPlayer(myMusicInformation.getBg(), v1);
            amMusicPlayer = new AmMusicPlayer(myMusicInformation.getAm(), v2);
        }
        if (!isMusicEnabled()) {
            bgMusicPlayer.mutePlayer();
            amMusicPlayer.mutePlayer();
        }
    }

    public BgMusicPlayer getBgMusicPlayer() {
        return bgMusicPlayer;
    }

    public AmMusicPlayer getAmMusicPlayer() {
        return amMusicPlayer;
    }

    public void loadSounds() {
        soundEffects = new ArrayList<>();
        // this will onlybe used for background and ambient musics
        myMusicPlayers = new ArrayList<>();
        myMusicPlayers.add(new MusicInformation("bgmusic.mp3", "forest.mp3"));
        myMusicPlayers.add(new MusicInformation("intense.mp3", "forest.mp3"));
        myMusicPlayers.add(new MusicInformation("horror.mp3", "forest.mp3"));
        myMusicPlayers.add(new MusicInformation("bgmusic.mp3", "forest.mp3"));
        myMusicPlayers.add(new MusicInformation("intense.mp3", "forest.mp3"));
        myMusicPlayers.add(new MusicInformation("horror.mp3", "forest.mp3"));
        addSound("got_item", "got_item.mp3");
        addSound("shoot", "laser_shot.mp3");
        addSound("forest", "forest.mp3");
        addSound("horror", "horror.mp3");
        addSound("intense", "intense.mp3");
        addSound("bg", "bgmusic.mp3");
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
    }

    //-------------------AUDIO CLIP---------------------------//
    public void shutdown() {
        soundPool.shutdown();
    }

    public void addSound(String clipName, String url) {
        if (!soundEffects.contains(clipName)) {
            //add this clip to the map
            try {
                Sound newsound = new Sound();
                if (clipName != null) {
                    newsound.setName(clipName);
                    newsound.setAudioClip(new AudioClip(new File(soundDirectory + url).toURI().toString()));
                    soundEffects.add(newsound);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int searchMethodSoundEffects(String name) {
        for (Sound sound : soundEffects) {
            if (sound.getName().equalsIgnoreCase(name)) {

                System.out.println("INDEX IS " + soundEffects.indexOf(sound) + sound.getName());
                return soundEffects.indexOf(sound);
            } else {
                System.out.println("inside for loop not found");
            }
        }
        return -1;
    }

    public void setAudioClipVolume(String name, double value) {
        retrieveAudioClip(name).volumeProperty().set(value);
    }

    public AudioClip retrieveAudioClip(String name) {
        AudioClip audioClip = null;
        for (Sound sound : soundEffects) {
            if (sound.getName().equalsIgnoreCase(name)) {
                int i = soundEffects.indexOf(sound);
                audioClip = soundEffects.get(i).getAudioClip();
            }
        }
        return audioClip;
    }

    public void playAudioClip(String name) {
        retrieveAudioClip(name).play();
    }

    public void stopAudioClip(String name) {
        retrieveAudioClip(name).stop();
    }

    public void loopAudioClip(String name) {
        retrieveAudioClip(name).play();
    }

    public void deleteSoundClip(String name) {
        soundEffects.remove(searchMethodSoundEffects(name));
        for (Sound sound : soundEffects) {
            System.out.println(sound.getName());
        }
    }

    public void clearSoundEffectList() {
        soundEffects.clear();
    }

    public ArrayList<Sound> getAllSounds() {
        return soundEffects;
    }
}
