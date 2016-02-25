package edu.uco.sdd.rocketdog.model;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;

public class WASDKeyMapping implements KeyMapping {

    @Override
    public void handleKeyPressed(Level currentLevel, KeyEvent keyEvent, double speed) {
        switch (keyEvent.getCode()) {
            case A:
                currentLevel.getRocketDog().getSprite().setScaleX(-1);
                currentLevel.getRocketDog().setVelocity(new Point2D(-speed, currentLevel.getRocketDog().getVelocity().getY()));
                break;
            case D:
                currentLevel.getRocketDog().getSprite().setScaleX(1);
                currentLevel.getRocketDog().setVelocity(new Point2D(speed, currentLevel.getRocketDog().getVelocity().getY()));
                break;
            case W:
                currentLevel.getRocketDog().setVelocity(new Point2D(currentLevel.getRocketDog().getVelocity().getX(), -speed));
                break;
            case S:
                currentLevel.getRocketDog().setVelocity(new Point2D(currentLevel.getRocketDog().getVelocity().getX(), speed));
                break;
            case J:
                int i = currentLevel.checkFiredLaser();
                if(i == -1)
                    break;
                currentLevel.getLaserWeapon(i).setVisableOn();
                currentLevel.getLaserWeapon(i).setPosition(new Point2D(currentLevel.getRocketDog().getPosition().getX()+100, 
                                                                      currentLevel.getRocketDog().getPosition().getY()+65));
                currentLevel.getLaserWeapon(i).setVelocity(new Point2D(speed, currentLevel.getRocketDog().getVelocity().getY()));
                break;
            case K:
                int j = currentLevel.checkFiredLargerLaser();
                if(j == -1)
                    break;
                currentLevel.getLargeLaserWeapon(j).setVisableOn();
                currentLevel.getLargeLaserWeapon(j).setPosition(new Point2D(currentLevel.getRocketDog().getPosition().getX()+100, 
                                                                      currentLevel.getRocketDog().getPosition().getY()+15));
                currentLevel.getLargeLaserWeapon(j).setVelocity(new Point2D(speed, currentLevel.getRocketDog().getVelocity().getY()));
                break;
            case F1:
                currentLevel.getRocketDog().setAnimation(new SpitzChargeGunAnimateStrategy());
                break;
            case F2:
                currentLevel.getRocketDog().setAnimation(new SpitzDeadAnimateStrategy());
                break;
            case H:
                currentLevel.setVisibleHitBoxes(!currentLevel.getVisibleHitBoxes());
                break;
            case O:
                //game.displayOptionsScreen();
                break;
        }
    }

    @Override
    public void handleKeyReleased(Level currentLevel, KeyEvent keyEvent, double speed) {
        switch (keyEvent.getCode()) {
            case A:
            case D:
            case W:
            case S:
                currentLevel.getRocketDog().setVelocity(new Point2D(0, 0));
                break; //rd.y +=  10; 
        }
    }
}
