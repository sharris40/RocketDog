package edu.uco.sdd.rocketdog.controller;

import edu.uco.sdd.rocketdog.model.Level;
import edu.uco.sdd.rocketdog.commands.RocketDogController;
import javafx.scene.input.KeyEvent;

public interface KeyMapping {
    //public void handleKeyPressed(Level currentLevel, KeyEvent keyEvent, double speed);
    public void handleKeyPressed(RocketDogController rdController, Level currentLevel, KeyEvent keyEvent, double speed);
    public void handleKeyReleased(RocketDogController rdController, Level currentLevel, KeyEvent keyEvent, double speed);
}
