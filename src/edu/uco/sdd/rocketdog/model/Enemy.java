package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.controller.MeleeAttackController;
import edu.uco.sdd.rocketdog.controller.PatrolController;
import edu.uco.sdd.rocketdog.controller.ProjectileAttackController;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Dubs
 */
public class Enemy extends TangibleEntity implements Attacker {

    private Image img;
    private MeleeAttackController meleeAttack;
    private ProjectileAttackController projectileAttack;

    static class Builder {

        private Image img;
        private int x;
        private int y;
        private EntityClass entityClass;
        private Level level;
        private int start;
        private int end;
        private int range;

        /**
         * @param imgFile
         * @param x requestWidth
         * @param y requestHeight
         */
        public Builder(String imgFile, double x, double y) {
            try {
                img = new Image(imgFile, x, y, true, false);
            } catch (IllegalArgumentException exc) {
                System.out.println("'" + imgFile + "'" + " not found, exiting...");
                Platform.exit();
            }
            // Set Defaults so client code can use out of the box
            x = y = 0;
            entityClass = new EntityClass("Enemy");
        }

        public Builder setX(int x) {
            this.x = x;
            return this;
        }

        public Builder setY(int y) {
            this.y = y;
            return this;
        }

        public Builder setLevel(Level level) {
          this.level = level;
          return this;
        }

        public Builder setEntityClass(EntityClass ec) {
            this.entityClass = ec;
            return this;
        }

        public EntityClass getEntityClass() {
            return this.entityClass;
        }

        public Builder setStart(int start) {
            this.start = start;
            return this;
        }

        public Builder setEnd(int end) {
            this.end = end;
            return this;
        }

        public Builder setRange(int range) {
            this.range = range;
            return this;
        }

        public Enemy build() {
            Enemy newBadGuy = new Enemy(this);
            PatrolController controller = new PatrolController(newBadGuy);
            newBadGuy.addController(controller);
            controller.setRange(range);
            controller.setStart(start);
            controller.setEnd(end);
            newBadGuy.addEntityClass(entityClass, 1);
            newBadGuy.setMeleeAttack(new MeleeAttackController(newBadGuy));
            newBadGuy.setProjectileAttack(new ProjectileAttackController(newBadGuy));
            newBadGuy.setLevel(level);
            return newBadGuy;
        }
    }

    public Enemy(Builder builder) {
        super();
        img = builder.img;
        setSprite(new ImageView(img));
        ImageView s = this.getSprite();
        s.setLayoutX(builder.x);
        s.setLayoutY(builder.y);
        getHitbox().setLayoutX(builder.x);
        getHitbox().setLayoutY(builder.y);
        setPosition(new Point2D(builder.x, builder.y));
    }

    public Enemy() { // constructor for a child of enemy I.E. DeliveryMan
        super();
    }

    @Override
    public void update() {
        getSprite().setLayoutX(getPosition().getX());
        getSprite().setLayoutY(getPosition().getY());

        getHitbox().setLayoutX(getPosition().getX());
        getHitbox().setLayoutY(getPosition().getY());
    }

    public MeleeAttackController getMeleeAttack() {
        return meleeAttack;
    }

    public void setMeleeAttack(MeleeAttackController meleeAttack) {
        this.meleeAttack = meleeAttack;
    }

    public ProjectileAttackController getProjectileAttack() {
        return projectileAttack;
    }

    public void setProjectileAttack(ProjectileAttackController projectileAttack) {
        this.projectileAttack = projectileAttack;
    }

    @Override
    public void attack(TangibleEntity target) {
        if (meleeAttack != null) {
          meleeAttack.attack(target);
        }
        if (projectileAttack != null) {
          projectileAttack.attack(target);
        }
    }
}
