package edu.uco.sdd.rocketdog.controller;

import edu.uco.sdd.rocketdog.model.Animations.SpitzChargeGunAnimateStrategy;
import edu.uco.sdd.rocketdog.model.Animations.SpitzDeadAnimateStrategy;
import edu.uco.sdd.rocketdog.commands.RocketDogController;
import edu.uco.sdd.rocketdog.model.Level;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;

public class WASDKeyMapping implements KeyMapping {

    @Override
    public void handleKeyPressed(RocketDogController rdController, Level currentLevel, KeyEvent keyEvent, double speed) {
        switch (keyEvent.getCode()) {
            case A:
                //currentLevel.getRocketDog().getSprite().setScaleX(-1); // Flip image so facing left
                //currentLevel.getRocketDog().setLeftSpeed(-speed);
                //currentLevel.getRocketDog().setVelocity(new Point2D(-speed, currentLevel.getRocketDog().getVelocity().getY()));
                //currentLevel.getRocketDog().setMoving(true);
                break;
            case D:
               // currentLevel.getRocketDog().getSprite().setScaleX(1); // Flip image so facing right
                //currentLevel.getRocketDog().setRightSpeed(speed);
                //currentLevel.getRocketDog().setVelocity(new Point2D(speed, currentLevel.getRocketDog().getVelocity().getY()));
                //currentLevel.getRocketDog().setMoving(true);
                break;
            case W:
                //currentLevel.getRocketDog().setUpSpeed(-speed);
               // currentLevel.getRocketDog().setVelocity(new Point2D(currentLevel.getRocketDog().getVelocity().getX(), -speed));
               // currentLevel.getRocketDog().setMoving(true);
                break;
            case S:
                //currentLevel.getRocketDog().setDownSpeed(speed);
               // currentLevel.getRocketDog().setVelocity(new Point2D(currentLevel.getRocketDog().getVelocity().getX(), speed));
               // currentLevel.getRocketDog().setMoving(true);
                break;
            case J:
                /*int i = currentLevel.checkFiredLaser();
                if (i == -1) {
                    break;
                }
                currentLevel.getLaserWeapon(i).setVisableOn();
                currentLevel.getLaserWeapon(i).setPosition(new Point2D(currentLevel.getRocketDog().getPosition().getX() + 100,
                        currentLevel.getRocketDog().getPosition().getY() + 65));
                currentLevel.getLaserWeapon(i).setVelocity(new Point2D(speed, currentLevel.getRocketDog().getVelocity().getY()));*/
                break;
            case K:
                /*int j = currentLevel.checkFiredLargerLaser();
                if (j == -1) {
                    break;
                }
                currentLevel.getLargeLaserWeapon(j).setVisableOn();
                currentLevel.getLargeLaserWeapon(j).setPosition(new Point2D(currentLevel.getRocketDog().getPosition().getX() + 100,
                        currentLevel.getRocketDog().getPosition().getY() + 15));
                currentLevel.getLargeLaserWeapon(j).setVelocity(new Point2D(speed, currentLevel.getRocketDog().getVelocity().getY()));*/
                break;
            case F1:
                //currentLevel.getRocketDog().setCurrentHealth(1000);
                //currentLevel.getRocketDog().setAnimation(new SpitzChargeGunAnimateStrategy());
                break;
            case F2:
                //currentLevel.getRocketDog().setAnimation(new SpitzDeadAnimateStrategy());
                break;
            case H:
                //currentLevel.setVisibleHitBoxes(!currentLevel.getVisibleHitBoxes());
                break;
            case O:
                //game.displayOptionsScreen();
                break;
        }
    }
    
    @Override
    public void handleKeyReleased(RocketDogController rdController, Level currentLevel, KeyEvent keyEvent, double speed) {
        switch (keyEvent.getCode()) {
            case A:
                currentLevel.getRocketDog().setVelocity(new Point2D(0, currentLevel.getRocketDog().getVelocity().getY()));
                currentLevel.getRocketDog().setMoving(false);
                break;
            case D:
                currentLevel.getRocketDog().setVelocity(new Point2D(0, currentLevel.getRocketDog().getVelocity().getY()));
                currentLevel.getRocketDog().setMoving(false);
                break;
            case W:
                currentLevel.getRocketDog().setVelocity(new Point2D(currentLevel.getRocketDog().getVelocity().getX(), 0));
                currentLevel.getRocketDog().setMoving(false);
                break;
            case S:
                currentLevel.getRocketDog().setVelocity(new Point2D(currentLevel.getRocketDog().getVelocity().getX(), 0));
                currentLevel.getRocketDog().setMoving(false); 
                break;
        }
    }
}
