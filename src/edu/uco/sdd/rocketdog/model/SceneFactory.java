package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.exceptions.LevelNotFound;
import edu.uco.sdd.rocketdog.controller.ImageViewLoader;
import static edu.uco.sdd.rocketdog.controller.RocketDogGame.GAME_SCREEN_HEIGHT;
import static edu.uco.sdd.rocketdog.controller.RocketDogGame.GAME_SCREEN_WIDTH;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class SceneFactory {

    protected String level;
    ImageViewLoader ldr;
    public SoundManager soundManager;

    public SceneFactory(String level) {
        this.level = level;
        ldr = ImageViewLoader.getInstance();
        loadMusic();
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Scene getLevel() {
        switch (level) {
            case "Splash":
                return new SplashLevel(new BorderPane(),soundManager);
            case "One":
                return new LevelOne(new Group(), ldr.loadImage("/Level 2.png"), GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT,soundManager);
            case "Two":
                return new LevelTwo(new Group(), LevelTwo.LEVEL_WIDTH, LevelTwo.LEVEL_HEIGHT, soundManager);
            default: // Google Style Guide: default for switch is mandatory
                throw new LevelNotFound(level + " is not valid for getLevel in LevelFactory.java");
        }
    }
    private void loadMusic(){
        soundManager= new SoundManager();
        soundManager.setMp_bg(soundManager.createMediaPlayer("bgmusic.mp3"));
        soundManager.playMediaPlayer(soundManager.getMp_bg(),0.1);
        soundManager.setMp_am(soundManager.createMediaPlayer("forest.mp3"));
        soundManager.playMediaPlayer(soundManager.getMp_am(),0.2);
    }
}