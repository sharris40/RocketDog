package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.model.Animations.IAnimateStrategy;
import edu.uco.sdd.rocketdog.model.Animations.SpitzDeadAnimateStrategy;
import edu.uco.sdd.rocketdog.model.Animations.SpitzIdleAnimateStrategy;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RocketDog extends TangibleEntity implements IAnimateStrategy, Attackable {

    private IAnimateStrategy animating;
    private final Text healthText;
    private static final double MAX_HEALTH = 500;
    private int powerAttribute;
    private int agilityAttribute;
    private int defenseAttribute;
    private int luckAttribute;
    private int currentScore;
    public int count1 = 0, count2 = 0;
    private boolean moving = false;

    private double horzSpeed, vertSpeed;

    public RocketDog() {
        super();
        currentScore = 0;
        setMaximumHealth(MAX_HEALTH);
        currentHealth = MAX_HEALTH;
        powerAttribute = 0;
        agilityAttribute = 1;
        defenseAttribute = 1;
        luckAttribute = 1;
        animating = new SpitzIdleAnimateStrategy();
        setSprite(new ImageView(animating.getImage()));
        getSprite().setViewport(animating.getCurrentView());
        this.currentHealth = 20;
        this.healthText = new Text(0, 20, Double.toString(super.getCurrentHealth()));
        this.healthText.setFont(new Font(20));
        this.healthText.setStroke(Color.GREEN);

        getHitbox().setOffsetX(40);//set offset for more appropriate and adjustable hit box
        getHitbox().setOffsetY(20);
    }

    @Override
    public void update() {
        Point2D currentVelocity = null;
        if (isMovementRestricted()) {
            currentVelocity = stuckVelocity;
        } else {
            currentVelocity = getVelocity();
        }

        if (moving || isMovementRestricted()) {
            setPosition(new Point2D(getPosition().getX() + currentVelocity.getX(), getPosition().getY() + currentVelocity.getY()));
        }

        if (!moving && getHorzSpeed() > 0) {
            setHorzSpeed(getHorzSpeed() - .5);
            setPosition(new Point2D(getPosition().getX() + getHorzSpeed(), getPosition().getY()));
            //if(!moving && getRightSpeed() > 0){
            //setRightSpeed(getRightSpeed()-.3);
            //setPosition(new Point2D(getPosition().getX() + getRightSpeed(), getPosition().getY()));
        }

        if (!moving && getHorzSpeed() < 0) {
            setHorzSpeed(getHorzSpeed() + .5);
            setPosition(new Point2D(getPosition().getX() + getHorzSpeed(), getPosition().getY()));
            //if(!moving && getLeftSpeed() < 0){
            //setLeftSpeed(getLeftSpeed()+.3);
            //setPosition(new Point2D(getPosition().getX() + getLeftSpeed(), getPosition().getY()));
        }

        if (!moving && getVertSpeed() < 0) {
            setVertSpeed(getVertSpeed() + .5);
            setPosition(new Point2D(getPosition().getX(), getPosition().getY() + getVertSpeed()));
            //if(!moving && getUpSpeed() < 0){
            //setUpSpeed(getUpSpeed() +.5);
            //setPosition(new Point2D(getPosition().getX(), getPosition().getY() + getUpSpeed()));
        }
        if (!moving && getVertSpeed() > 0) {
            setVertSpeed(getVertSpeed() - .5);
            setPosition(new Point2D(getPosition().getX(), getPosition().getY() + getVertSpeed()));
            //if(!moving && getDownSpeed() > 0){
            //setDownSpeed(getDownSpeed() -.5);
            //setPosition(new Point2D(getPosition().getX(), getPosition().getY() + getDownSpeed()));
        }


        if(this.getCurrentHealth() <= 0 && !this.isDead())
        {
            this.setDead(true);
            setAnimation(new SpitzDeadAnimateStrategy());
        }

        /**
         * Moving the character is handled by the TangibleEntity class
         */
//        getSprite().setLayoutX(getPosition().getX());
//        getSprite().setTranslateY(getPosition().getY());
//
//        getHitbox().setLayoutX(getPosition().getX());
//        getHitbox().setTranslateY(getPosition().getY());
        getHitbox().setTranslateX(getSprite().getTranslateX());
        getHitbox().setTranslateY(getSprite().getTranslateY());
        getHitbox().setWidth(80);
        getHitbox().setHeight(100);
        getHitbox().resize(this);

        getSprite().setViewport(animating.getCurrentView());
        handle(); // Animations
    }

    public void setLuckAttribute(int newLuckAttribute) {
        this.luckAttribute = newLuckAttribute;
    }

    public int getLuckAttribute() {
        return this.luckAttribute;
    }

    public void setDefenseAttribute(int newDefenseAttribute) {
        defenseAttribute = newDefenseAttribute;
    }

    public int getDefenseAttribute() {
        return defenseAttribute;
    }

    public void setAnimation(IAnimateStrategy newAnimation) {
        animating = newAnimation;
        getSprite().setImage(animating.getImage());
        setPosition(new Point2D(getPosition().getX(), getPosition().getY()));
        //getSprite().setTranslateX(getPosition().getX());
        //getSprite().setTranslateY(getPosition().getY());
    }

    public void setPowerAttribute(int newPowerAttribute) {
        this.powerAttribute = newPowerAttribute;
    }

    public void setAgilityAttribute(int newAgilityAttribute) {
        this.agilityAttribute = newAgilityAttribute;
    }

    public int getPowerAttribute() {
        return powerAttribute;
    }

    public int getAgilityAttribute() {
        return agilityAttribute;
    }

    public void setScore(int newScore) {
        currentScore = newScore;
    }

    public int getScore() {
        return currentScore;
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
    public String toString() {
        return new String(
                "RocketDog[x:" + this.getPosition().getX()
                + " y: " + this.getPosition().getY()
                + "]"
        );
    }

    @Override
    public void damage(double attackStrength) {
        if (this.currentHealth > 0) {
            this.currentHealth -= attackStrength;
            this.healthText.setText(Double.toString(currentHealth));
            if (this.currentHealth <= 0) {
                this.setDead(true);
                this.setAnimation(new SpitzDeadAnimateStrategy());
            }
        }
    }

    public Text getHealthText() {
        return this.healthText;
    }

    public void setMoving(boolean x) {
        moving = x;
    }

    public boolean getMoving() {
        return moving;
    }

    public double getHorzSpeed() {
        return horzSpeed;
    }

    public double getVertSpeed() {
        return vertSpeed;
    }

    public void setHorzSpeed(double v) {
        horzSpeed = v;
    }

    public void setVertSpeed(double v) {
        vertSpeed = v;
    }
}
