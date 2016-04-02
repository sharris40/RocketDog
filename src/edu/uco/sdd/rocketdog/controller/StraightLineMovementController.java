package edu.uco.sdd.rocketdog.controller;

import edu.uco.sdd.rocketdog.model.Attacker;
import edu.uco.sdd.rocketdog.model.Entity;
import edu.uco.sdd.rocketdog.model.EntityClass;
import edu.uco.sdd.rocketdog.model.TangibleEntity;
import java.util.Map;
import javafx.geometry.Point2D;

public class StraightLineMovementController extends AccelerationController {

  public Point2D getVelocity() {
    return controlledObject.getVelocity();
  }

  public void setVelocity(Point2D velocity) {
    controlledObject.setVelocity(velocity);
  }

  public StraightLineMovementController(TangibleEntity entity) {
    super(entity);
  }

  @Override
  public boolean process(Map<Entity, Boolean> changedEntities) {
    super.process(changedEntities);
    final Boolean thisChanged = changedEntities.get(controlledObject);
    if (thisChanged != null && !thisChanged)  // removed
      return false;
    getLevel().getAllEntities().forEach(te -> {
      if (te != controlledObject) {
        Point2D pos = controlledObject.getPosition();
        Point2D otherPos = te.getPosition();
        if (pos != null && otherPos != null) {
          Map<EntityClass, Integer> classes = controlledObject.getEntityClasses();
          EntityClass.Relationship rel = null;
          int maxPriority = 0;
          for (Map.Entry<EntityClass, Integer> myEntry : classes.entrySet()) {
            if (myEntry.getValue() > maxPriority) {
              for (EntityClass otherClass : te.getEntityClasses().keySet()) {
                EntityClass.Relationship otherRel = myEntry.getKey().getRelationship(otherClass);
                if (otherRel != null) {
                  maxPriority = myEntry.getValue();
                  rel = otherRel;
                }
              }
            }
          }
          if (rel == EntityClass.Relationship.ENEMY && controlledObject instanceof Attacker) {
            ((Attacker)controlledObject).attack(te);
          }
        }
      }
    });
    return true;
  }
}