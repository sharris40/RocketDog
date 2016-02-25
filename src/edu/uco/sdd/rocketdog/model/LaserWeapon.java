/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Kody
 */

/* This class will later be edited to be a builder
   This will allow for several laser bullets on the screen at a time
   However we can restrict the total number on the screen at a time. 
*/
public class LaserWeapon extends TangibleEntity implements IAnimateStrategy{
   
    private IAnimateStrategy animating;
    
    
    public LaserWeapon(){
        super();
        animating = new SpitzLaserWeaponAnimateStrategy();
        setSprite(new ImageView(animating.getImage()));
        getSprite().setViewport(animating.getCurrentView());
        setVisableOff();
    }
    
    public void setAnimation(IAnimateStrategy newAnimation) {
        animating = newAnimation;
        getSprite().setImage(animating.getImage());
        getSprite().setTranslateX(getPosition().getX());
        getSprite().setTranslateY(getPosition().getY());
    }
    
    public void update(){
        setPosition(new Point2D(getPosition().getX() + getVelocity().getX(), 
                                getPosition().getY() + getVelocity().getY()));
        
        getSprite().setTranslateX(getPosition().getX());
        getSprite().setTranslateY(getPosition().getY());

        getHitbox().setTranslateX(getPosition().getX());
        getHitbox().setTranslateY(getPosition().getY());
        
        getSprite().setViewport(animating.getCurrentView());
        handle(); // Animations
    }
    
    
    @Override
    public void handle() {
        animating.handle();
    }

    @Override
    public Rectangle2D getCurrentView() {
        return animating.getCurrentView();
    }

    @Override
    public Image getImage() {
        return animating.getImage();
    }
    
    
    public void setVisableOn(){
        getSprite().setVisible(true);
    }
    
    public void setVisableOff(){
        getSprite().setVisible(false);
    }
    
   
}