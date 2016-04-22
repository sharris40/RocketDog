/*
 * This class is the parent of a more specific Obstruction class
 * to add an Obstruction create a new class enheriting this class
 * see ObstructionBrick.java for example
 *
 * Level.java contains an ArrayList to hold all of the Obstructions on any
 * level. The Logic that handles Obstructions is in level.java and mostly involves
 * updating, adding and removing instances of this class.
 *
 * LevelOne.java or any other unique level is where instances and coordinates 
 * of Obstructions should be implemented.
 * 
 * NOTE: an Obstruction is an object on screen that rocketDog can not pass
 * such as a wall.
 */
package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.model.Animations.IAnimateStrategy;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * @author Richard Dobie
 */
public class Obstruction extends TangibleEntity implements IAnimateStrategy {
    private IAnimateStrategy animating;
    private Level level;
    private RocketDog rd;
    private boolean visible;
    
    public Obstruction(Point2D position, IAnimateStrategy animate){
        super();
        this.setPosition(position);
        visible = true;

        animating = animate;
        setSprite(new ImageView(animating.getImage()));
        getSprite().setViewport(animating.getCurrentView());
    }

    //constructor for a invisible wall
    public Obstruction(Point2D position){
        this.setPosition(position);
        visible = false;
    }
    
    /* Obstructions may or may not move so we will leave the update
     * function like the other Tangible Entities for now
     */
    public void update(){
        //if (level != null) this.rd = level.getRocketDog();
        setPosition(new Point2D(getPosition().getX(), getPosition().getY()));
        if (visible){
            getSprite().setTranslateX(getPosition().getX());
            getSprite().setTranslateY(getPosition().getY());
        }
        getHitbox().setTranslateX(getPosition().getX());
        getHitbox().setTranslateY(getPosition().getY());
        
        if (visible){
            getSprite().setViewport(animating.getCurrentView());
            handle(); // Animations
        }
 
    }
    
    @Override
    public void processCollision(TangibleEntity te){
        super.processCollision(te);
        double rdMaxY = te.getHitbox().localToScene(te.getHitbox().getBoundsInParent()).getMaxY();
        double rdMinY = te.getHitbox().localToScene(te.getHitbox().getBoundsInParent()).getMinY();
        double boxMaxY = this.getHitbox().localToScene(this.getHitbox().getBoundsInParent()).getMaxY();
        double boxMinY = this.getHitbox().localToScene(this.getHitbox().getBoundsInParent()).getMinY();
        
        if (this.isColliding()) {
            this.rd = (RocketDog)te;
            
            this.rd.setHorzSpeed(-rd.getHorzSpeed());
            this.rd.setVertSpeed(-rd.getVertSpeed());
            te.setVelocity(new Point2D(-te.getVelocity().getX(), -te.getVelocity().getY()));
            te.update();
            te.setVelocity(new Point2D(0, 0));
            this.rd.setHorzSpeed(0);
            this.rd.setVertSpeed(0);
            te.setColliding(false);
        } else {
            te.setColliding(false);
        }
    }
    
    public void setAnimation(IAnimateStrategy newAnimation) {
        animating = newAnimation;
        getSprite().setImage(animating.getImage());
        getSprite().setTranslateX(getPosition().getX());
        getSprite().setTranslateY(getPosition().getY());
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

    public boolean isVisible() {
        return visible;
    }

}
