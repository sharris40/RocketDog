/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.controller.MeleeAttackController;
import edu.uco.sdd.rocketdog.controller.PatrolController;
import edu.uco.sdd.rocketdog.controller.ProjectileAttackController;
import edu.uco.sdd.rocketdog.model.Animations.IAnimateStrategy;
import edu.uco.sdd.rocketdog.model.Animations.DManWalkAnimateStrategy;
import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author Doobifier
 */
public class DeliveryMan extends Enemy implements Attacker, IAnimateStrategy {
    private IAnimateStrategy animating;
    private EntityClass entityClass;
    private ArrayList<Hitbox> hitboxes;



    public DeliveryMan(double x, double y){
        super();

        animating = new DManWalkAnimateStrategy();
        setPosition(new Point2D(x,y));
        setSprite(new ImageView(animating.getImage()));
        getSprite().setViewport(animating.getCurrentView());

        this.setMultiHibox(true);
        hitboxes = new ArrayList();
        Hitbox hitbox = new Hitbox(this.getPosition().getX(), this.getPosition().getY());
        hitbox.setWidth(400);
        hitbox.setHeight(100);
        hitbox.setOffsetX(10);
        hitbox.setOffsetY(250);
        hitboxes.add(hitbox);
        Hitbox nhitbox = new Hitbox(this.getPosition().getX(), this.getPosition().getY());
        nhitbox.setWidth(100);
        nhitbox.setHeight(300);
        nhitbox.setOffsetX(250);
        nhitbox.setOffsetY(10);
        hitboxes.add(nhitbox);
        setHitboxes(hitboxes);

        entityClass = new EntityClass("Enemy");
        PatrolController controller = new PatrolController(this);
        this.addController(controller);
        controller.setRange(150.);
        controller.setStart(getPosition().getX() - 50.);
        controller.setEnd(getPosition().getX() + 50.);
        this.addEntityClass(entityClass, 1);
        this.setMeleeAttack(new MeleeAttackController(this));
        this.setProjectileAttack(new ProjectileAttackController(this));

    }


    @Override
    public void update(){
        /*setPosition(new Point2D(getPosition().getX(),getPosition().getY()));
        if (getLevel().getRocketDog().getPosition().getX() > this.getPosition().getX() + 200){
            getSprite().setScaleX(-1);
            hitboxes.get(1).setOffsetX(10);
        } else {
            getSprite().setScaleX(1);
            hitboxes.get(1).setOffsetX(250);
        }
        
        getSprite().setTranslateX(getPosition().getX());
        getSprite().setTranslateY(getPosition().getY());

        hitboxes.forEach((hitbox) -> {
            hitbox.setTranslateX(getPosition().getX());
            hitbox.setTranslateY(getPosition().getY());
            hitbox.resize(this);
            super.update();
        });

        getSprite().setViewport(animating.getCurrentView());

        handle(); // Animations*/
        setPosition(new Point2D(getPosition().getX(),getPosition().getY()));
        if (getLevel().getRocketDog().getPosition().getX() > this.getPosition().getX()){
            getSprite().setScaleX(-1);
        } else {
            getSprite().setScaleX(1);
        }
        getSprite().setTranslateX(getPosition().getX());
        getSprite().setTranslateY(getPosition().getY());

        getHitbox().setTranslateX(getPosition().getX());
        getHitbox().setTranslateY(getPosition().getY());

        getSprite().setViewport(animating.getCurrentView());

        hitboxes.forEach((hitbox) -> {
            hitbox.setTranslateX(getSprite().getTranslateX());
            hitbox.setTranslateY(getSprite().getTranslateY());
            hitbox.resize(this);
        });
        
        handle(); // Animations
    }

    @Override
    public void attack(TangibleEntity te){

    }

    @Override
    public void processCollision(TangibleEntity te){
        hitboxes.stream().forEach((hitbox) -> {
            if (levelIntersect(hitbox,te.getHitbox())) {
            te.getHitbox().setStroke(Color.RED);
            hitbox.setStroke(Color.RED);
            setColliding(true);
        } else {
            hitbox.setStroke(Color.GREEN);
            te.getHitbox().setStroke(Color.GREEN);
            setColliding(false);
        }
        });

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
}
