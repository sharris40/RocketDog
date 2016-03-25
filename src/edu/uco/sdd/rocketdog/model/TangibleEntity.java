package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.controller.MovementController;
import edu.uco.sdd.rocketdog.model.Animations.IAnimateStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class TangibleEntity implements Entity{

    protected double currentHealth;
    private double maximumHealth;
    private boolean dead;
    private boolean colliding; //flag for collision
    private Point2D position;
    private Point2D acceleration;
    private Point2D velocity;
    protected Point2D stuckVelocity;
    private Hitbox hitbox;
    private final Map<EntityClass, Integer> entityClasses = new HashMap<>();
    private ImageView sprite;
    private Level level;
    private State currentState;
    private ArrayList<Modification> modifications;
    private final ArrayList<Observer> observers;
    private boolean movementRestricted;
    public TangibleEntity() {
        this(new Point2D(0, 0));
    }

    public TangibleEntity(Point2D startPosition) {
        this(startPosition, 0, 0, 0);
    }

    public TangibleEntity(Point2D startPosition, double hitboxWidth, double hitboxHeight, int startHealth) {
        this.dead = false;
        this.colliding = false;
        this.movementRestricted = false;
        this.modifications = new ArrayList<>();
        this.currentState = new FullHealthState(startHealth);
        this.observers = new ArrayList<>();
        this.hitbox = new Hitbox(0, 0);
        this.acceleration = new Point2D(0, 0);
        this.position = startPosition;
        this.velocity = new Point2D(0, 0);
        this.stuckVelocity = new Point2D(0, 0);
        this.hitbox.setWidth(hitboxWidth);
        this.hitbox.setHeight(hitboxHeight);
    }

    public Map<EntityClass, Integer> getEntityClasses() {
        return new HashMap<>(entityClasses);
    }

    public ArrayList<Modification> getModifications() {
        return modifications;
    }
    protected ArrayList<MovementController> controllers = new ArrayList<>();

    public boolean hasCollided(TangibleEntity otherEntity) {
        return getHitbox().getBoundsInParent().intersects(otherEntity.getHitbox().getBoundsInParent());
    }

    public State getState() {
        return this.currentState;
    }

    @Override
    public boolean process(Map<Entity, Boolean> changedEntities) {
        boolean changed = false;
        for (int i = 0; i < controllers.size(); ++i) {
            MovementController c = controllers.get(i);
            if (c.process(changedEntities)) {
                changed = true;
                if (c != controllers.get(i)) {
                    --i;
                }
            }
        }
        if (movementRestricted) {
          if (stuckVelocity != null) {
            position = position.add(stuckVelocity);
            return true;
          }
          return false;
        } else {
          if (acceleration == null) {
              if (velocity == null) {
                  return false;
              } else {
                  double x = velocity.getX();
                  if (x >= 1 || x <= -1) {
                      position = position.add(velocity);
                      return true;
                  }
                  return false;
              }
          } else {
              if (velocity == null) {
                  velocity = new Point2D(0, 0);
              }
              position = position.add(velocity);
              velocity = velocity.add(acceleration);
              return true;
          }
        }
    }

    public void addModification(Modification newModification) {
        modifications.add(newModification);
    }

    public void removeModification(Modification oldModification) {
        modifications.remove(oldModification);
    }

    public void setState(State newState) {
        this.currentState = newState;
    }

    public void setCurrentHealth(double newHealth) {
        this.currentHealth = newHealth;
        notifyObservers();
    }

    public double getCurrentHealth() {
        return this.currentHealth;
    }

    public double getMaximumHealth() {
        return this.maximumHealth;
    }

    public void setMaximumHealth(double newMaximumHealth) {
        this.maximumHealth = newMaximumHealth;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public void setSprite(ImageView newSprite) {
        sprite = newSprite;
    }

    public Point2D getPosition() {
        return position;
    }

    /**
     * The client should not have to call new Point2D every time.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setPos(double x, double y) {
        setPosition(new Point2D(x, y));
    }

    public void setPosition(Point2D newPosition) {
        position = newPosition;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Point2D getActualVelocity() {
        if (isMovementRestricted())
          return stuckVelocity;
        else
          return velocity;
    }

    /**
     * The client should not have to call new Point2D every time.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setVel(double x, double y) {
        setVelocity(new Point2D(x, y));
    }

    public void setVelocity(Point2D newVelocity) {
        velocity = newVelocity;
    }

    public Point2D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Point2D newAcceleration) {
        acceleration = newAcceleration;
    }

    public boolean isMovementRestricted() {
        return movementRestricted;
    }

    public void setMovementRestricted(boolean movementRestricted) {
        if (!this.movementRestricted && movementRestricted)
          stuckVelocity = velocity;
        this.movementRestricted = movementRestricted;
    }

    public int getEntityClassPriority(EntityClass entityClass) {
        return entityClasses.get(entityClass);
    }

    public void addEntityClass(EntityClass entityClass, int priority) {
        if (entityClass == null) {
            throw new IllegalArgumentException("entityClass", new NullPointerException());
        }
        entityClasses.put(entityClass, priority);
    }

    public void removeEntityClass(EntityClass entityClass) {
        entityClasses.remove(entityClass);
    }

    public void addController(MovementController controller) {
        controllers.remove(controller);
        controllers.add(controller);
    }

    public void removeController(MovementController controller) {
        controllers.remove(controller);
    }

    public void processCollision(TangibleEntity otherEntity) {
        if (getHitbox().getBoundsInParent().intersects(otherEntity.getHitbox().getBoundsInParent())) {
            otherEntity.getHitbox().setStroke(Color.RED);
            getHitbox().setStroke(Color.RED);
            colliding = true;
        } else {
            getHitbox().setStroke(Color.GREEN);
            otherEntity.getHitbox().setStroke(Color.GREEN);
            colliding = false;
        }
    }

    public boolean isColliding() {
        return colliding;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public void addObserver(Observer newObserver) {
        this.observers.add(newObserver);
    }

    @Override
    public void removeObserver(Observer oldObserver) {
        observers.remove(oldObserver);
    }

    @Override
    public void notifyObservers() {
        this.observers.stream().forEach((observer) -> {
            observer.update(getCurrentHealth());
        });
    }

    public abstract void update();
}
