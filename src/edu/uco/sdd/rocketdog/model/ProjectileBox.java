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

import edu.uco.sdd.rocketdog.model.Animations.ExplosionAnimateStrategy;
import edu.uco.sdd.rocketdog.model.Animations.IAnimateStrategy;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Richard Dobie
 */
public class ProjectileBox extends TangibleEntity implements IAnimateStrategy, Attacker {
    private IAnimateStrategy animating;
    private int duration = 0;
    private TangibleEntity te;
    private boolean active;
    private double newDisplacementX;
    private double newDisplacementY;
    private double boxVelocityX;
    private double boxVelocityY;
    private Explosion explosion;
    private Group group;
    private static final double GRAVITY_ACCELERATION = 1.6;
    private static final int COMPENSATE_FOR_FPS = 60;
    private static final int DAMAGE = 500;

    public ProjectileBox(TangibleEntity t, IAnimateStrategy animate, Group group){
        super();
        this.te = t;
        this.animating = animate;
        this.active = true;
        setSprite(new ImageView(animating.getImage()));
        getSprite().setViewport(animating.getCurrentView());
        setDead(false);
        getHitbox().setWidth(150);
        getHitbox().setHeight(150);
        getHitbox().setLayoutX(-100);
        getHitbox().setLayoutY(1000);

        this.group = group;
        explosion = new Explosion(new ExplosionAnimateStrategy());
        group.getChildren().add(explosion.getSprite());
        explosion.getSprite().setLayoutY(-300);
        explosion.setDead(true);
    }

    public void update(){
        if(!this.isDead()){
            duration++;

            getSprite().setLayoutX(getSprite().getLayoutX() - (newDisplacementX));
            getSprite().setLayoutY(getSprite().getLayoutY() - (newDisplacementY));
            setNextYDisplacement(); // set next velocity
            getHitbox().setLayoutX(getSprite().getLayoutX());
            getHitbox().setLayoutY(getSprite().getLayoutY());
            getSprite().setViewport(animating.getCurrentView());
            handle(); // Animations
        } else {
            boxVelocityX = 0;
            boxVelocityY = 0;
            newDisplacementX = 0;
            newDisplacementY = 0;
            duration = 0;
        }


        if (explosion.isDead()){
           removeExplosion();
        } else {
            explosion.update();
        }

    }

    public void setAnimation(IAnimateStrategy newAnimation) {
        animating = newAnimation;
        getSprite().setImage(animating.getImage());
        //getSprite().setTranslateX(getPosition().getX());
        //getSprite().setTranslateY(getPosition().getY());
    }

    @Override
    public void processCollision(TangibleEntity te){
        super.processCollision(te);

        if (this.isColliding()){
            attack(te);
            addExplosion();
            this.setDead(true);
        }
    }

    public void setNextYDisplacement(){
        //boxVelocityY = Math.sqrt(Math.abs(boxVelocityY + (2*GRAVITY_ACCELERATION*1)));
        newDisplacementY = ((boxVelocityY * duration) - (.5 * GRAVITY_ACCELERATION * (duration * duration))) / COMPENSATE_FOR_FPS;
        newDisplacementX = boxVelocityX / 4.5; //COMPENSATE_FOR_FPS;
    }


    public void attack(TangibleEntity tangibleEntity){
        tangibleEntity.setCurrentHealth(tangibleEntity.getCurrentHealth() - DAMAGE);
    }

    public void addExplosion(){
        explosion.setDead(false);
        explosion.getSprite().setLayoutX(this.getSprite().getLayoutX()-100);
        explosion.getSprite().setLayoutY(this.getSprite().getLayoutY()-100);
    }

    public void removeExplosion(){
        explosion.getSprite().setLayoutY(-300);
    }

    public void updateExplosion(){
        explosion.update();
        if (explosion.isDead()){
           removeExplosion();
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

    public double getBoxVelocityX() {
        return boxVelocityX;
    }

    public void setBoxVelocityX(double velocityX) {
        this.boxVelocityX = velocityX;
    }

    public double getBoxVelocityY() {
        return boxVelocityY;
    }

    public void setBoxVelocityY(double velocityY) {
        this.boxVelocityY = velocityY;
    }

    public double getGRAVITY_ACCELERATION() {
        return GRAVITY_ACCELERATION;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
