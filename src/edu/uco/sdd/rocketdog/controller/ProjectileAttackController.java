package edu.uco.sdd.rocketdog.controller;

import edu.uco.sdd.rocketdog.model.Attackable;
import edu.uco.sdd.rocketdog.model.Projectile;
import edu.uco.sdd.rocketdog.model.TangibleEntity;
import javafx.geometry.Point2D;

/**
 * Attack controller for entities that fire projectiles. A new entity is
 * created that has a MeleeAttackController.
 * @author Spencer Harris
 */
public class ProjectileAttackController implements AttackController {
  private double damage, minRange, maxRange;
  protected TangibleEntity controlledObject;


  public ProjectileAttackController(TangibleEntity entity) {
    if (entity == null)
      throw new IllegalArgumentException(new NullPointerException("entity"));
    this.controlledObject = entity;
    this.damage = 1.;
    this.minRange = 100.;
    this.maxRange = 300.;
  }

  public double getDamage() {
    return damage;
  }

  public void setDamage(double damage) {
    this.damage = damage;
  }

  public double getMinRange() {
    return minRange;
  }

  public void setMinRange(double minRange) {
    this.minRange = minRange;
  }

  public double getMaxRange() {
    return maxRange;
  }

  public void setMaxRange(double maxRange) {
    this.maxRange = maxRange;
  }

  @Override
  public boolean attack(TangibleEntity target) {
    double distance = controlledObject.getPosition().distance(target.getPosition());
    if (target instanceof Attackable && Math.abs(distance) < maxRange
            && Math.abs(distance) > minRange && !target.isImmune()) {
        Point2D velocity = target.getPosition()
                                 .subtract(controlledObject.getPosition())
                                 .normalize()
                                 .multiply(4);

        Projectile projectile = new Projectile.Builder("/bullet.png", 32, 32)
                        .setX((int)controlledObject.getPosition().getX())
                        .setY((int)controlledObject.getPosition().getY())
                        .setVelocity(velocity)
                        .setLevel(controlledObject.getLevel()).build();
        controlledObject.getEntityClasses().forEach((c, i) -> {
          projectile.addEntityClass(c, i);
        });
        controlledObject.getLevel().addProjectile(projectile, 32, 32);
      return true;
    }
    return false;
  }
}
