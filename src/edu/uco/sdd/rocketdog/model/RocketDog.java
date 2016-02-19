package edu.uco.sdd.rocketdog.model;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RocketDog extends TangibleEntity implements IAnimateStrategy, Attackable{

    private IAnimateStrategy animating;
    private double health;

    public RocketDog() {
        super();
        animating = new SpitzIdleAnimateStrategy();
        setSprite(new ImageView(animating.getImage()));
        getSprite().setViewport(animating.getCurrentView());
        this.health = 20.;
    }

    public void update() {
        setPosition(new Point2D(getPosition().getX() + getVelocity().getX(), getPosition().getY() + getVelocity().getY()));
        /**
         * Moving the character is handled by the TangibleEntity class
         */
        getSprite().setTranslateX(getPosition().getX());
        getSprite().setTranslateY(getPosition().getY());

        getHitbox().setTranslateX(getPosition().getX());
        getHitbox().setTranslateY(getPosition().getY());
        
        getSprite().setViewport(animating.getCurrentView());
        handle(); // Animations
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

    @Override
    public void damage(double attackStrength) {
      this.health -= attackStrength;
      if (this.health <= 0) {
        this.setAnimation(new SpitzDeadAnimateStrategy());
      }
    }

}
