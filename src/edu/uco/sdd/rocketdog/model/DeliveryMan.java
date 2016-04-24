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
import edu.uco.sdd.rocketdog.model.Animations.FragileRotatingBoxAnimateStrategy;
import edu.uco.sdd.rocketdog.model.Animations.FlippingBox1AnimateStrategy;
import edu.uco.sdd.rocketdog.model.Animations.FlippingBox2AnimateStrategy;
import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Doobifier
 */
public class DeliveryMan extends Enemy implements Attacker, IAnimateStrategy {
    private static final int FIRE_RATE = 45;
    private static final double BOX_THROW_RANGE = 1000;
    private int count = 0;
    private int boxCount = 0;
    private int n = 0;
    private IAnimateStrategy animating;
    private EntityClass entityClass;
    private Group group;
    private ArrayList<Hitbox> hitboxes;
    private ArrayList<ProjectileBox> projectiles;
    private ProjectileBox projectileBox;
    private Text debugText;
    private double travelTime;
    private Boolean initialized = false;
    private RocketDog rocketDog;
    private Explosion explosions;
    private int invulCount = 0;

    Random random;



    public DeliveryMan(double x, double y, Group group){
        super();

        animating = new DManWalkAnimateStrategy();
        setPosition(new Point2D(x,y));
        setSprite(new ImageView(animating.getImage()));
        getSprite().setViewport(animating.getCurrentView());
        getSprite().setLayoutX(x);
        getSprite().setLayoutY(y);
        random = new Random();
        this.group = group;
        projectiles = new ArrayList();
        debugText = new Text();
        debugText.setFont(new Font(20));
        //group.getChildren().add(debugText);
        debugText.setLayoutX(1500);
        debugText.setLayoutY(200);

        getSprite().setLayoutX(x);
        getSprite().setLayoutY(y);
        getHitbox().setLayoutX(x);
        getHitbox().setLayoutY(x);

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
        //PatrolController controller = new PatrolController(this);
        //this.addController(controller);
        //controller.setRange(150.);
        //controller.setStart(getPosition().getX() - 50.);
        //controller.setEnd(getPosition().getX() + 50.);
        this.addEntityClass(entityClass, 1);
        this.setMeleeAttack(new MeleeAttackController(this));
        this.setProjectileAttack(new ProjectileAttackController(this));

    }


    @Override
    public void update(){


        count++;
        if (count >= FIRE_RATE){
            count = 0;
            fireBox();
        }

        debugText.setText("n: " + n + " y: " + getRocketDogYDistance());


        setPosition(new Point2D(getPosition().getX(),getPosition().getY()));
        if (getLevel().getRocketDog().getPosition().getX() > this.getPosition().getX()){
            getSprite().setScaleX(-1);
            hitboxes.get(hitboxes.size() - 1).setOffsetX(10);
        } else {
            getSprite().setScaleX(1);
            hitboxes.get(hitboxes.size() - 1).setOffsetX(250);
        }
        getSprite().setLayoutX(getPosition().getX());
        getSprite().setLayoutY(getPosition().getY());

        getHitbox().setLayoutX(getPosition().getX());
        getHitbox().setLayoutY(getPosition().getY());

        getSprite().setViewport(animating.getCurrentView());

        hitboxes.forEach((hitbox) -> {
            hitbox.setLayoutX(this.getSprite().getLayoutX());
            hitbox.setLayoutY(getSprite().getLayoutY());
            hitbox.resize(this);
        });

        projectiles.forEach((projectile) -> {
                projectile.update();
            if (!projectile.isDead()){
                projectile.processCollision(rocketDog);
                getLevel().getLaserAttacks().forEach(weapon -> {projectile.processCollision(weapon);});
                getLevel().getLargeLaserAttacks().forEach(weapon -> {projectile.processCollision(weapon);});
                projectile.getHitbox().setVisible(getLevel().getVisibleHitBoxes());
                if (projectile.getSprite().getLayoutY() > 924){
                    projectile.setDead(true);
                    projectile.update();
                }
            } else {
                projectile.getSprite().setLayoutX(-100);
                projectile.getSprite().setLayoutY(1000);
                projectile.getHitbox().setLayoutX(-200);
                projectile.getHitbox().setLayoutY(-200);
            }
        });

        handle(); // Animations
    }

    @Override
    public void attack(TangibleEntity te){

    }

    @Override
    public void processCollision(TangibleEntity te){
      if (invulCount > 10){
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
      } else {
          invulCount++;
      }
    }

    public void fireBox(){
        if (!initialized){
            rocketDog = getLevel().getRocketDog();
            setProjectiles();
        }


        if (Math.abs(getRocketDogXDistance()) < BOX_THROW_RANGE){

            if (n > boxCount) n = 0;

            projectiles.get(n).setDead(false);
            projectiles.get(n).getSprite().setLayoutX(getSprite().getLayoutX() + 100);
            projectiles.get(n).getSprite().setLayoutY(getSprite().getLayoutY() + 50);
            projectiles.get(n).setBoxVelocityY(calculateVelocityY());
            projectiles.get(n).setBoxVelocityX(calculateVelocityX());

            n++;


        }

    }

    public double calculateVelocityX(){
        double newVelocity;
        double xDisplacement = getRocketDogXDistance();
        newVelocity = (xDisplacement / (travelTime)); //0.6 compensate for fps

        return newVelocity;
    }

    public double calculateVelocityY(){
        double newVelocity;
        double yDisplacement = getRocketDogYDistance();
        newVelocity = Math.sqrt(Math.abs(2 * projectileBox.getGRAVITY_ACCELERATION() * yDisplacement));
        travelTime = newVelocity / projectileBox.getGRAVITY_ACCELERATION();

        return newVelocity * 1.125;
    }

    public double getRocketDogXDistance(){
        double rocketDogX = getLevel().getRocketDog().getSprite().localToScene(getLevel().getRocketDog().getSprite().getBoundsInLocal()).getMinX() + 150;
        return this.getSprite().localToScene(getSprite().getBoundsInLocal()).getMaxX() - rocketDogX;
    }

    public double getRocketDogYDistance(){
        double rocketDogY = getLevel().getRocketDog().getSprite().localToScene(getLevel().getRocketDog().getSprite().getBoundsInLocal()).getMinY() + 150;
        return this.getSprite().localToScene(getSprite().getBoundsInLocal()).getMaxY() - rocketDogY;
    }

    public void setProjectiles(){
        IAnimateStrategy a1,a2,a3;
        ProjectileBox p1, p2, p3;

        a1 = new FragileRotatingBoxAnimateStrategy();
        a2 = new FlippingBox1AnimateStrategy();
        a3 = new FlippingBox2AnimateStrategy();

        projectileBox = new ProjectileBox(rocketDog, a1, group);
        p1 = new ProjectileBox(rocketDog, a1, group);
        p1.setDead(true);
        p1.setGroup(group);


        group.getChildren().add(p1.getSprite());
        group.getChildren().add(p1.getHitbox());
        projectiles.add(p1);

        p2= new ProjectileBox(rocketDog,a1, group);
        p2.setDead(true);
        p2.setGroup(group);

        group.getChildren().add(p2.getSprite());
        group.getChildren().add(p2.getHitbox());
        projectiles.add(p2);

        p3= new ProjectileBox(rocketDog,a1, group);
        p3.setDead(true);
        p3.setGroup(group);

        group.getChildren().add(p3.getSprite());
        group.getChildren().add(p3.getHitbox());
        projectiles.add(p3);

        boxCount = projectiles.size() - 1;

       initialized = true;
    }


    public void setAnimation(IAnimateStrategy newAnimation) {
        animating = newAnimation;
        getSprite().setImage(animating.getImage());
        getSprite().setLayoutX(getPosition().getX());
        getSprite().setLayoutY(getPosition().getY());
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

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public void setCurrentHealth(double health) {
        super.setCurrentHealth(health);
        if (health <= 0) {

            projectiles.forEach((projectile) -> {
                projectile.getSprite().setLayoutX(-100);
                projectile.getSprite().setLayoutY(1000);
                projectile.getHitbox().setLayoutX(-200);
                projectile.getHitbox().setLayoutY(-200);
                projectile.removeExplosion();
            });
        }
    }


}
