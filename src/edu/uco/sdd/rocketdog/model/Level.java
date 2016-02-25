package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.controller.KeyMappingContext;
import edu.uco.sdd.rocketdog.model.Animations.SpitzDeadAnimateStrategy;
import edu.uco.sdd.rocketdog.controller.RocketDogGame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Level extends Scene implements ILevel {

    final private RocketDog rocketDog;
    final private EntityClass player;
    private ArrayList<Enemy> enemies;
    private ArrayList<AidItem> AidItems;             //container of AitItems
    private ArrayList<ActiveAidItem> ActiveAidItems; //container of ActiveAidItems
    private ArrayList<Hazard> Hazards; // container of Hazards
    private ArrayList<Obstruction> Obstructions; //container of Obstructions
    private ArrayList<Projectile> projectiles;
    private boolean visibleHitBoxes;
    private Group root;
    private KeyMappingContext keyMapping;
    private boolean isDone;

    public Level(Group root, ImageView background, int width, int height) {
        super(root, width, height);
        this.root = root;

        //Initialization
        keyMapping = new KeyMappingContext();
        visibleHitBoxes = false;
        rocketDog = new RocketDog();
        player = new EntityClass("Player");
        enemies = new ArrayList<>();
        AidItems = new ArrayList<>();
        ActiveAidItems = new ArrayList<>();
        Hazards = new ArrayList<>();
        Obstructions = new ArrayList<>();
        projectiles = new ArrayList<>();

        //Background Added to game
        root.getChildren().add(background);
        //root.setAlignment(Pos.TOP_LEFT);

        //Hero information added to game
        rocketDog.setPosition(new Point2D(RocketDogGame.GAME_SCREEN_WIDTH/3,RocketDogGame.GAME_SCREEN_HEIGHT/2));
        rocketDog.addEntityClass(player, 1);
        rocketDog.getHitbox().setWidth(130);
        rocketDog.getHitbox().setHeight(130);
        rocketDog.setLevel(this);
        root.getChildren().add(rocketDog.getSprite());
        root.getChildren().add(rocketDog.getHitbox());
        root.getChildren().add(rocketDog.getHealthText());

        //Keyboard Handling
        this.setOnKeyPressed((KeyEvent event) -> {
            keyMapping.getKeyMapping().handleKeyPressed(this, event, 10.0d);
        });

        this.setOnKeyReleased((KeyEvent event) -> {
            keyMapping.getKeyMapping().handleKeyReleased(this, event, 0.0d);
        });
    }

    public KeyMappingContext getKeyMapping() {
        return keyMapping;
    }

    public RocketDog getRocketDog() {
        return rocketDog;
    }

    public boolean getVisibleHitBoxes() {
        return visibleHitBoxes;
    }

    public EntityClass getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
      return enemies;
    }

    public List<TangibleEntity> getAllEntities() {
      ArrayList<TangibleEntity> entities = new ArrayList<>(1 + enemies.size());
      entities.add(getRocketDog());
      entities.addAll(getEnemies());
      return entities;
    }

    public void addEnemy(Enemy enemy, double width, double height) {
        //Setup enemy hitbox information
        enemy.getHitbox().setWidth(width);
        enemy.getHitbox().setHeight(height);

        //Add enemy information to level
        enemies.add(enemy);
        this.root.getChildren().add(enemy.getSprite());
        this.root.getChildren().add(enemy.getHitbox());
    }

    public void removeEnemy(Enemy enemy) {
        //Make sure the ArrayList has the enemy within it
        //before tyring to remove
        if (enemies.indexOf(enemy) > -1) {
            enemies.remove(enemy);
        }

        //Make sure the root has the enemy in its children
        //before ting to remove
        if (root.getChildren().indexOf(enemy.getSprite()) > -1) {
            root.getChildren().remove(enemy.getSprite());
        }

        //Make sure the root has the enemy in its children
        //before ting to remove
        if (root.getChildren().indexOf(enemy.getHitbox()) > -1) {
            root.getChildren().remove(enemy.getHitbox());
        }
    }
    
    public void addAidItem(AidItem aidItem, double width, double height) {
        //Setup powerup hitbox information
        aidItem.getHitbox().setWidth(width);
        aidItem.getHitbox().setHeight(height);

        //Add powerup information to level
        AidItems.add(aidItem);
        root.getChildren().add(aidItem.getSprite());
        root.getChildren().add(aidItem.getHitbox());
    }
    
    public void removeAidItem(AidItem aidItem) {
        //Make sure the ArrayList has the item within it 
        //before tyring to remove
        if (AidItems.indexOf(aidItem) > -1) {
            AidItems.remove(aidItem);
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (root.getChildren().indexOf(aidItem.getSprite()) > -1) {
            root.getChildren().remove(aidItem.getSprite());
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (root.getChildren().indexOf(aidItem.getHitbox()) > -1) {
            root.getChildren().remove(aidItem.getHitbox());
        }
    }
    
    public void addActiveAidItem(ActiveAidItem activeAidItem, double width, double height) {
        //Setup active powerup hitbox information
        activeAidItem.getHitbox().setWidth(width);
        activeAidItem.getHitbox().setHeight(height);

        //Add active powerup information to level
        ActiveAidItems.add(activeAidItem);
        root.getChildren().add(activeAidItem.getSprite());
        root.getChildren().add(activeAidItem.getHitbox());
    }
    
    public void removeActiveAidItem(ActiveAidItem activeAidItem) {
        //Make sure the ArrayList has the item within it 
        //before tyring to remove
        if (ActiveAidItems.indexOf(activeAidItem) > -1) {
            ActiveAidItems.remove(activeAidItem);
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (root.getChildren().indexOf(activeAidItem.getSprite()) > -1) {
            root.getChildren().remove(activeAidItem.getSprite());
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (root.getChildren().indexOf(activeAidItem.getHitbox()) > -1) {
            root.getChildren().remove(activeAidItem.getHitbox());
        }
    }
    
    public void addHazard(Hazard hazard, double width, double height) {
        //Setup powerup hitbox information
        hazard.getHitbox().setWidth(width);
        hazard.getHitbox().setHeight(height);

        //Add powerup information to level
        Hazards.add(hazard);
        root.getChildren().add(hazard.getSprite());
        root.getChildren().add(hazard.getHitbox());
    }
    
    public void removeHazard(Hazard hazard) {
        //Make sure the ArrayList has the item within it 
        //before tyring to remove
        if (Hazards.indexOf(hazard) > -1) {
            Hazards.remove(hazard);
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (root.getChildren().indexOf(hazard.getSprite()) > -1) {
            root.getChildren().remove(hazard.getSprite());
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (root.getChildren().indexOf(hazard.getHitbox()) > -1) {
            root.getChildren().remove(hazard.getHitbox());
        }
    }

    public void addObstruction(Obstruction obstruction, double width, double height) {
        //Setup powerup hitbox information
        obstruction.getHitbox().setWidth(width);
        obstruction.getHitbox().setHeight(height);

        //Add powerup information to level
        Obstructions.add(obstruction);
        root.getChildren().add(obstruction.getSprite());
        root.getChildren().add(obstruction.getHitbox());
    }
    
    public void removeHazard(Obstruction obstruction) {
        //Make sure the ArrayList has the item within it 
        //before tyring to remove
        if (Hazards.indexOf(obstruction) > -1) {
            Hazards.remove(obstruction);
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (root.getChildren().indexOf(obstruction.getSprite()) > -1) {
            root.getChildren().remove(obstruction.getSprite());
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (root.getChildren().indexOf(obstruction.getHitbox()) > -1) {
            root.getChildren().remove(obstruction.getHitbox());
        }
    }
    
    public void addProjectile(Projectile p, double width, double height) {
        //Setup enemy hitbox information
        p.getHitbox().setWidth(width);
        p.getHitbox().setHeight(height);

        //Add enemy information to level
        projectiles.add(p);
        root.getChildren().add(p.getSprite());
        root.getChildren().add(p.getHitbox());
    }

    public void removeProjectile(Projectile p) {
        //Make sure the ArrayList has the enemy within it
        //before tyring to remove
        if (projectiles.indexOf(p) > -1) {
            projectiles.remove(p);
        }

        //Make sure the root has the enemy in its children
        //before ting to remove
        if (root.getChildren().indexOf(p.getSprite()) > -1) {
            root.getChildren().remove(p.getSprite());
        }

        //Make sure the root has the enemy in its children
        //before ting to remove
        if (root.getChildren().indexOf(p.getHitbox()) > -1) {
            root.getChildren().remove(p.getHitbox());
        }
    }

    public void setVisibleHitBoxes(boolean value) {
        visibleHitBoxes = value;
    }
    

    public void update() {

        //Update RocketDog
        rocketDog.update();

        //Set rocketDog hitbox visibility
        rocketDog.getHitbox().setVisible(visibleHitBoxes);
        rocketDog.getHealthText().setVisible(visibleHitBoxes);

        Map<Entity, Boolean> changed = new HashMap<>();
        changed.put(rocketDog, true);

        enemies.stream().forEach((enemy) -> {
            changed.put(enemy, true);
            enemy.process(changed);
        });

        projectiles.stream().forEach((p) -> {
            changed.put(p, true);
            p.process(changed);
        });

        enemies.stream().forEach((enemy) -> {
            //Update enemy
            enemy.update();

            //Set enemy hitbox visibility
            enemy.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            enemy.processCollision(rocketDog);
        });

        for (int i = 0; i < projectiles.size(); ++i) {
          Projectile p = projectiles.get(i);
          if (!p.isDead()) {
            //Update projectile
            p.update();

            //Set projectile hitbox visibility
            p.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            p.processCollision(rocketDog);
          } else {
            removeProjectile(p);
            --i;
          }
        }
        
        AidItems.stream().forEach((aidItem) -> {
            //Update enemy
            aidItem.update();

            //Set enemy hitbox visibility
            aidItem.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            aidItem.processCollision(rocketDog);
            if (aidItem.isColliding() && aidItem.getClass() == edu.uco.sdd.rocketdog.model.ShieldItem.class){
                removeAidItem(aidItem);
                addActiveAidItem(new ActiveShield(rocketDog),153,150);
            } else if (aidItem.isColliding() && aidItem.getClass() == edu.uco.sdd.rocketdog.model.BoostItem.class){
                removeAidItem(aidItem);
                rocketDog.setVelocity(new Point2D(40,0));
                addActiveAidItem(new ActiveBoost(rocketDog),153,150);
            }
        });
    
    ActiveAidItems.stream().forEach((activeAidItem) -> {
            //Update active power ups
            activeAidItem.update();

            //Set active power up hitbox visibility
            activeAidItem.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            activeAidItem.processCollision(enemies.get(0));
            
            if (!activeAidItem.isActive()){
                removeActiveAidItem(activeAidItem);
            }
            if (activeAidItem.isColliding()){
                activeAidItem.setActive(false);
            }
            
            if (!activeAidItem.isActive()) removeActiveAidItem(activeAidItem);
        });
    
        Hazards.stream().forEach((hazard) -> {
            //Update Hazard
            hazard.update();

            //Set hazard hitbox visibility
            hazard.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            hazard.processCollision(rocketDog);
            if (hazard.isColliding()){
                // Logic for when rocketDog collides with a hazard
                rocketDog.setAnimation(new SpitzDeadAnimateStrategy()); //needs to be handled more appropriately
            }
        });
        
        Obstructions.stream().forEach((obstruction) -> {
            //Update Hazard
            obstruction.update();

            //Set hazard hitbox visibility
            obstruction.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            obstruction.processCollision(rocketDog);
            if (obstruction.isColliding()){
                //set X and Y velocity in the opposite direction
                //then update and set velocity to 0
                //this prevents RD from moving through the obstruction
                    rocketDog.setVelocity(new Point2D(-rocketDog.getVelocity().getX(),-rocketDog.getVelocity().getY()));
                    rocketDog.update();
                    rocketDog.setVelocity(new Point2D(0, 0));               
            }
        });
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
