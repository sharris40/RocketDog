package edu.uco.sdd.rocketdog.commands;

import edu.uco.sdd.rocketdog.model.Bullet;
import edu.uco.sdd.rocketdog.model.LargeLaserAttack;
import edu.uco.sdd.rocketdog.model.LaserAttack;
import edu.uco.sdd.rocketdog.model.Level;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

class ShootRight extends AbstractCommand {

    //private final Group bulletGroup;
    private Level currentLevel;
    private KeyCode k;
    //private final double x;
    //private final double y;
    

    public ShootRight(Level currentLevel, KeyCode k) {
        //this.bulletGroup = bulletGroup; //Group bulletGroup
        this.currentLevel = currentLevel;
        this.k = k;
        //this.x = x;
        //this.y = y;
    }

    @Override
    public void execute() {
        //Bullet bullet = new Bullet(x,y);
        //bulletGroup.getChildren().add(bullet);
        if(k == k.J){
            int i = currentLevel.checkFiredLaser();
            if(i == -1){}
            else{
                currentLevel.getLaserWeapon(i).getHitbox().setStroke(Color.GREEN);
                currentLevel.getLaserWeapon(i).setVisableOn();
                if (currentLevel.getRocketDog().getSprite().getScaleX() == -1) {
                    currentLevel.getLaserWeapon(i).setVelocity(new Point2D(-8, currentLevel.getRocketDog().getVelocity().getY()));
                    currentLevel.getLaserWeapon(i).setPosition(new Point2D(currentLevel.getRocketDog().getPosition().getX() -40,
                        currentLevel.getRocketDog().getPosition().getY() + 65));
                } else {
                    currentLevel.getLaserWeapon(i).setVelocity(new Point2D(8, currentLevel.getRocketDog().getVelocity().getY()));
                    currentLevel.getLaserWeapon(i).setPosition(new Point2D(currentLevel.getRocketDog().getPosition().getX() + 120,
                        currentLevel.getRocketDog().getPosition().getY() + 65));
                }
            }
        }

        else if(k == k.K){
            int charge = currentLevel.largeLaserCharge();
                currentLevel.update(charge);
                if(charge == 3){
                    int j = currentLevel.checkFiredLargerLaser();
                    //if (j == -1) {}
                    currentLevel.getLargeLaserWeapon(j).getHitbox().setStroke(Color.GREEN);
                    currentLevel.getLargeLaserWeapon(j).setVisableOn();
                    if (currentLevel.getRocketDog().getSprite().getScaleX() == -1) {
                        currentLevel.getLargeLaserWeapon(j).getSprite().setScaleX(-1);
                        currentLevel.getLargeLaserWeapon(j).setVelocity(new Point2D(-8, currentLevel.getRocketDog().getVelocity().getY()));
                        currentLevel.getLargeLaserWeapon(j).setPosition(new Point2D(currentLevel.getRocketDog().getPosition().getX() - 170,
                            currentLevel.getRocketDog().getPosition().getY() + 15));
                    } else {
                        currentLevel.getLargeLaserWeapon(j).setPosition(new Point2D(currentLevel.getRocketDog().getPosition().getX() + 100,
                            currentLevel.getRocketDog().getPosition().getY() + 15));
                        currentLevel.getLargeLaserWeapon(j).getSprite().setScaleX(1);
                        currentLevel.getLargeLaserWeapon(j).setVelocity(new Point2D(8, currentLevel.getRocketDog().getVelocity().getY()));
                    }
                }
            }
        }
    }

