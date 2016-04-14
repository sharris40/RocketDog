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
public class Explosion extends TangibleEntity implements IAnimateStrategy {
    private IAnimateStrategy animating;
    private int duration;
    
    public Explosion(IAnimateStrategy animate){
        super();
        this.animating = animate;
        setSprite(new ImageView(animating.getImage()));
        getSprite().setViewport(animating.getCurrentView());
        duration = 0;
    }
    
    public void update(){
        duration++;
        if (duration > 48) {
            this.setDead(true);
            duration = 0;
            this.getSprite().setTranslateY(-300);
        } else {
            getSprite().setViewport(animating.getCurrentView());
            handle();
        }
    }
    
    public void setAnimation(IAnimateStrategy newAnimation) {
        animating = newAnimation;
        getSprite().setImage(animating.getImage());
        //getSprite().setTranslateX(getPosition().getX());
        //getSprite().setTranslateY(getPosition().getY());
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
}
