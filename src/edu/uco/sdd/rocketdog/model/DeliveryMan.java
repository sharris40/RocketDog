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
        getSprite().setLayoutX(x);
        getSprite().setLayoutY(y);
        getHitbox().setLayoutX(x);
        getHitbox().setLayoutY(x);
 
        this.setMultiHibox(true);
        hitboxes = new ArrayList();
        Hitbox hitbox = new Hitbox(this.getSprite().getTranslateX(), this.getSprite().getTranslateX());
        hitbox.setWidth(350);
        hitbox.setHeight(100);
        hitbox.setOffsetX(10);
        hitbox.setOffsetY(250);
        hitboxes.add(hitbox);
        Hitbox nhitbox = new Hitbox(this.getSprite().getTranslateX(), this.getSprite().getTranslateX());
        nhitbox.setWidth(100);
        nhitbox.setHeight(250);
        nhitbox.setOffsetX(250);
        nhitbox.setOffsetY(10);
        hitboxes.add(nhitbox);
        setHitboxes(hitboxes);
        Hitbox nnhitbox = new Hitbox(this.getSprite().getTranslateX(), this.getSprite().getTranslateX());
        nnhitbox.setWidth(125);
        nnhitbox.setHeight(125);
        nnhitbox.setOffsetX(125);
        nnhitbox.setOffsetY(125);
        hitboxes.add(nnhitbox);
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
        getSprite().setLayoutX(getPosition().getX());
        getSprite().setLayoutY(getPosition().getY());

        //getHitbox().setLayoutX(getPosition().getX());
        //getHitbox().setLayoutY(getPosition().getY());

        getSprite().setViewport(animating.getCurrentView());

        hitboxes.forEach((hitbox) -> {
            hitbox.setLayoutX(this.getSprite().getLayoutX());
            hitbox.setLayoutY(this.getSprite().getLayoutY());
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