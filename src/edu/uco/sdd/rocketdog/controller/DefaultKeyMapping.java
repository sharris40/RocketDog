package edu.uco.sdd.rocketdog.controller;

import edu.uco.sdd.rocketdog.model.Animations.SpitzChargeGunAnimateStrategy;
import edu.uco.sdd.rocketdog.model.Animations.SpitzDeadAnimateStrategy;
import edu.uco.sdd.rocketdog.model.Animations.SpitzIdleAnimateStrategy;
import edu.uco.sdd.rocketdog.model.FullHealthState;
import edu.uco.sdd.rocketdog.model.Level;
import edu.uco.sdd.rocketdog.model.SplashLevel;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class DefaultKeyMapping implements KeyMapping {
    
    @Override
    public void handleKeyPressed(Level currentLevel, KeyEvent keyEvent, double speed) {   
        switch (keyEvent.getCode()) {
            case LEFT:
                currentLevel.getRocketDog().getSprite().setScaleX(-1); // Flip image so facing left
                //currentLevel.getRocketDog().setLeftSpeed(-speed);   //ORIG
                currentLevel.getRocketDog().setHorzSpeed(-speed);
                currentLevel.getRocketDog().setVelocity(new Point2D(-speed, currentLevel.getRocketDog().getVelocity().getY()));
                currentLevel.getRocketDog().setMoving(true); 
                break;
            case RIGHT:
                currentLevel.getRocketDog().getSprite().setScaleX(1); // Flip image so facing right
                //currentLevel.getRocketDog().setRightSpeed(speed);  //ORIG
                currentLevel.getRocketDog().setHorzSpeed(speed);
                currentLevel.getRocketDog().setVelocity(new Point2D(speed, currentLevel.getRocketDog().getVelocity().getY()));
                currentLevel.getRocketDog().setMoving(true);
                break;
            case UP:
                currentLevel.getRocketDog().setVertSpeed(-speed);
                //currentLevel.getRocketDog().setUpSpeed(-speed);
                currentLevel.getRocketDog().setVelocity(new Point2D(currentLevel.getRocketDog().getVelocity().getX(), -speed)); 
                currentLevel.getRocketDog().setMoving(true);
                break;
            case DOWN:
                currentLevel.getRocketDog().setVertSpeed(speed);
                //currentLevel.getRocketDog().setDownSpeed(speed);
                currentLevel.getRocketDog().setVelocity(new Point2D(currentLevel.getRocketDog().getVelocity().getX(), speed));
                currentLevel.getRocketDog().setMoving(true);
                break;      
            case J:
                int i = currentLevel.checkFiredLaser();
                if (i == -1) {
                    break;
                }
                currentLevel.getLaserWeapon(i).getHitbox().setStroke(Color.GREEN);
                currentLevel.getLaserWeapon(i).setVisableOn();
                currentLevel.getLaserWeapon(i).setPosition(new Point2D(currentLevel.getRocketDog().getPosition().getX() + 100,
                        currentLevel.getRocketDog().getPosition().getY() + 65));
                if (currentLevel.getRocketDog().getSprite().getScaleX() == -1) {
                    currentLevel.getLaserWeapon(i).setVelocity(new Point2D(-speed, currentLevel.getRocketDog().getVelocity().getY()));
                } else {
                    currentLevel.getLaserWeapon(i).setVelocity(new Point2D(speed, currentLevel.getRocketDog().getVelocity().getY()));
                }
                break;
            case K:
                int charge = currentLevel.largeLaserCharge();
                if(charge == 3){
                    int j = currentLevel.checkFiredLargerLaser();
                    if (j == -1) {
                        break;
                    }
                    currentLevel.getLargeLaserWeapon(j).getHitbox().setStroke(Color.GREEN);
                    currentLevel.getLargeLaserWeapon(j).setVisableOn();
                    currentLevel.getLargeLaserWeapon(j).setPosition(new Point2D(currentLevel.getRocketDog().getPosition().getX() + 100,
                            currentLevel.getRocketDog().getPosition().getY() + 15));
                    if (currentLevel.getRocketDog().getSprite().getScaleX() == -1) {
                        currentLevel.getLargeLaserWeapon(j).getSprite().setScaleX(-1);
                        currentLevel.getLargeLaserWeapon(j).setVelocity(new Point2D(-speed, currentLevel.getRocketDog().getVelocity().getY()));
                    } else {
                        currentLevel.getLargeLaserWeapon(j).getSprite().setScaleX(1);
                        currentLevel.getLargeLaserWeapon(j).setVelocity(new Point2D(speed, currentLevel.getRocketDog().getVelocity().getY()));
                    }
                }
                break;
            case F1:
                currentLevel.getRocketDog().setState(new FullHealthState(1000));
                currentLevel.getRocketDog().setDead(false);
                currentLevel.getRocketDog().setAnimation(new SpitzIdleAnimateStrategy());
                break;
            case F2:
                currentLevel.getRocketDog().setAnimation(new SpitzDeadAnimateStrategy());
                break;
            case H:
                currentLevel.setVisibleHitBoxes(!currentLevel.getVisibleHitBoxes());
                break;
            case P:
                currentLevel.setDone(true);
            case O:
                //game.displayOptionsScreen();
                break;
        }
    }
   
    @Override
    public void handleKeyReleased(Level currentLevel, KeyEvent keyEvent, double speed) {
        switch (keyEvent.getCode()) {
            case LEFT:
                currentLevel.getRocketDog().setMoving(false);
                currentLevel.getRocketDog().setVelocity(new Point2D(0, currentLevel.getRocketDog().getVelocity().getY()));
                break;
            case RIGHT:
                currentLevel.getRocketDog().setMoving(false);
                currentLevel.getRocketDog().setVelocity(new Point2D(0, currentLevel.getRocketDog().getVelocity().getY()));
                break;
            case UP:
                currentLevel.getRocketDog().setVelocity(new Point2D(currentLevel.getRocketDog().getVelocity().getX(), 0));
                currentLevel.getRocketDog().setMoving(false);              
                break;
            case DOWN:
                currentLevel.getRocketDog().setVelocity(new Point2D(currentLevel.getRocketDog().getVelocity().getX(), 0));
                currentLevel.getRocketDog().setMoving(false);             
                break;
        }
    }
}
