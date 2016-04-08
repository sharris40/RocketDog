/*
 * This class is the parent of a more specific ActiveAidItem class
 * to add an Active Aid Item create a new class enheriting this class
 * see ActiveShield.java for example
 *
 * Level.java contains an ArrayList to hold all of the ActiveAidItems
 * and ActiveAidItems should be implemented in that class
 * 
 * NOTE: an ActiveAidItem is what you get after you "pickup" an AidItem
 * so generally an instance of one of these is created after processColission() 
 * returns true for one of the AidItems on the screen.
 */
package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.model.Animations.IAnimateStrategy;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Richard Dobie
 */
public class ActiveAidItem extends TangibleEntity implements IAnimateStrategy, Attackable {
    private IAnimateStrategy animating;
    private int duration;
    private int offset = 0;
    private TangibleEntity te;
    private boolean active;
    
    public ActiveAidItem(TangibleEntity t, IAnimateStrategy animate, int duration){
        super();
        this.te = t;
        this.animating = animate;
        this.active = true;
        setSprite(new ImageView(animating.getImage()));
        getSprite().setViewport(animating.getCurrentView());
        this.duration = duration;
    }
    
    public void update(){
        setPosition(new Point2D(te.getPosition().getX() - this.offset,te.getPosition().getY()));
        getSprite().setTranslateX(te.getSprite().getTranslateX());
        getSprite().setTranslateY(te.getSprite().getTranslateY());
        getHitbox().setTranslateX(te.getSprite().getTranslateX());
        getHitbox().setTranslateY(te.getSprite().getTranslateY());
        getSprite().setViewport(animating.getCurrentView());
        handle(); // Animations
        
        if (duration > 0) {
            duration -= 1;
        } else if (duration == 0){
            this.active = false;//insert removal code
        } else {
            //insert other code
        }
        
        if (!this.isActive()) {
            this.setCurrentHealth(0);
        }
    }
    
    public void setAnimation(IAnimateStrategy newAnimation) {
        animating = newAnimation;
        getSprite().setImage(animating.getImage());
        getSprite().setTranslateX(getPosition().getX());
        getSprite().setTranslateY(getPosition().getY());
    }
    
    @Override
    public void processCollision(TangibleEntity te){
        super.processCollision(te);
    }
    
    
    public void damage(double attackStrength){
        if (this.currentHealth > 0) {
            this.currentHealth -= attackStrength;
            if (this.currentHealth <= 0) {
                this.setDead(true);
                this.setActive(false);
            }
        }
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

    public void setAnimating(IAnimateStrategy animating) {
        this.animating = animating;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
}
