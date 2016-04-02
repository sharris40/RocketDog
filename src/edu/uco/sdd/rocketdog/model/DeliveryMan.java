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
import javafx.geometry.Rectangle2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Doobifier
 */
public class DeliveryMan extends Enemy implements Attacker, IAnimateStrategy {
    private IAnimateStrategy animating;
    private EntityClass entityClass;



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
        setPosition(new Point2D(getPosition().getX(),getPosition().getY()));
        if (getLevel().getRocketDog().getPosition().getX() > this.getPosition().getX()){
            getSprite().setScaleX(-1);
        } else {
            getSprite().setScaleX(1);
        }
        getSprite().setLayoutX(getPosition().getX());
        getSprite().setLayoutY(getPosition().getY());

        getHitbox().setLayoutX(getPosition().getX());
        getHitbox().setLayoutY(getPosition().getY());

        getSprite().setViewport(animating.getCurrentView());


        handle(); // Animations
    }

    @Override
    public void attack(TangibleEntity te){

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
