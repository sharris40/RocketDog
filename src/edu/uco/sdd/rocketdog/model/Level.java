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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Level extends Scene implements Observer, ILevel {

    final private RocketDog rocketDog;
    final private EntityClass player;
    private ArrayList<Modification> entities;
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
    private Text scoreText;
    final private ArrayList<LaserAttack> weapon;
    //final private LaserWeapon weapon;
    final private ArrayList<LargeLaserAttack> largeWeapon;

    public Level(Group root, ImageView background, int width, int height) {
        super(root, width, height);
        this.root = root;

        //Initialization
        scoreText = new Text(5, 20, "");
        entities = new ArrayList<>();
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
        weapon = new ArrayList();
        //largeWeapon = new LargeLaserWeapon();
        largeWeapon = new ArrayList();

        //Background Added to game
        root.getChildren().add(background);
        //root.setAlignment(Pos.TOP_LEFT);

        //Hero information added to game
        rocketDog.setPosition(new Point2D(350, 300));
        rocketDog.addEntityClass(player, 1);
        rocketDog.getHitbox().setWidth(130);
        rocketDog.getHitbox().setHeight(130);
        rocketDog.setLevel(this);
        rocketDog.setState(new FullHealthState(1000));

        //Laser Weapon information added to game
        for (int i = 0; i < 3; i++) {
            weapon.add(new LaserAttack());
            getLaserWeapon(i).setPosition(new Point2D(100, 600));
            getLaserWeapon(i).getHitbox().setWidth(44);
            getLaserWeapon(i).getHitbox().setHeight(44);
            getLaserWeapon(i).getHitbox().setStroke(Color.TRANSPARENT);
            root.getChildren().add(getLaserWeapon(i).getSprite());
            root.getChildren().add(getLaserWeapon(i).getHitbox());
            //weapon.getHitbox().setWidth(44);
            //weapon.getHitbox().setHeight(44);
            //root.getChildren().add(weapon.getSprite());
            //root.getChildren().add(weapon.getHitbox());
        }

        //Large Laser Weapon information added to game
        for (int i = 0; i < 3; i++) {
            largeWeapon.add(new LargeLaserAttack());
            getLargeLaserWeapon(i).setPosition(new Point2D(100, 600));
            getLargeLaserWeapon(i).getHitbox().setWidth(200);
            getLargeLaserWeapon(i).getHitbox().setHeight(133);
            getLargeLaserWeapon(i).getHitbox().setStroke(Color.TRANSPARENT);
            root.getChildren().add(getLargeLaserWeapon(i).getSprite());
            root.getChildren().add(getLargeLaserWeapon(i).getHitbox());
        }

        root.getChildren().add(rocketDog.getSprite());
        root.getChildren().add(rocketDog.getHitbox());
        root.getChildren().add(rocketDog.getHealthText());
        root.getChildren().add(scoreText);

        scoreText.setText("Score : " + rocketDog.getScore() + "                 Health: " + rocketDog.getCurrentHealth());
        scoreText.setFont(new Font(20));

        //Keyboard Handling
        this.setOnKeyPressed((KeyEvent event) -> {
            keyMapping.getKeyMapping().handleKeyPressed(this, event, 10.0d);
        });

        this.setOnKeyReleased((KeyEvent event) -> {
            keyMapping.getKeyMapping().handleKeyReleased(this, event, 0.0d);
        }); 
    }

    public LaserAttack getLaserWeapon(int i) {
        return weapon.get(i);
    }

    public LargeLaserAttack getLargeLaserWeapon(int i) {
        return largeWeapon.get(i);
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
        enemy.setState(new FullHealthState(1000));

        //Add enemy information to level
        enemies.add(enemy);
        this.root.getChildren().add(enemy.getSprite());
        this.root.getChildren().add(enemy.getHitbox());
    }

    public void removeEnemy(Enemy enemy) {
        //Make sure the ArrayList has the enemy within it
        //before tyring to remove

        //Make sure the root has the enemy in its children
        //before ting to remove
        root.getChildren().remove(enemy.getSprite());

        //Make sure the root has the enemy in its children
        //before ting to remove
        root.getChildren().remove(enemy.getHitbox());
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

        //Make sure the root has the item in its children
        //before ting to remove
        root.getChildren().remove(aidItem.getSprite());

        //Make sure the root has the item in its children
        //before ting to remove
        root.getChildren().remove(aidItem.getHitbox());
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

    public void addTangibleEntity(Modification newEntity) {
        entities.add(newEntity);

        //Add entity information to level
        this.root.getChildren().add(newEntity.getSprite());
        this.root.getChildren().add(newEntity.getHitbox());

    }

    public void removeTangibleEntity(Modification oldEntity) {
        //Remove entity information from root
        this.root.getChildren().remove(oldEntity.getSprite());
        this.root.getChildren().remove(oldEntity.getHitbox());
    }

    public void setVisibleHitBoxes(boolean value) {
        visibleHitBoxes = value;
    }

    public int checkFiredLaser() {
        for (int i = 0; i < weapon.size(); i++) {
            if (weapon.get(i).getPosition().getX() > super.getWidth() || weapon.get(i).getPosition().getX() < 0) {
                weapon.get(i).setDead(false);
                weapon.get(i).setVisableOff();
            }
            if (!weapon.get(i).isDead()) {
                weapon.get(i).setDead(true);
                return i;
            }

        }
        return -1;
    }

    public int checkFiredLargerLaser() {
        for (int i = 0; i < largeWeapon.size(); i++) {
            if (largeWeapon.get(i).getPosition().getX() > super.getWidth() || weapon.get(i).getPosition().getX() < 0) {
                largeWeapon.get(i).setDead(false);
            }
            if (!largeWeapon.get(i).isDead()) {
                largeWeapon.get(i).setDead(true);
                return i;
            }
        }
        return -1;
    }

    @Override
    public void levelUpdate() {

        //Update RocketDog
        rocketDog.update();
        rocketDog.getState().doAction(rocketDog);

        //Set rocketDog hitbox visibility
        rocketDog.getHitbox().setVisible(visibleHitBoxes);
        rocketDog.getHealthText().setVisible(visibleHitBoxes);
        rocketDog.addObserver(this);

        Map<Entity, Boolean> changed = new HashMap<>();
        changed.put(rocketDog, true);
        //Update the weapon attack
        weapon.stream().forEach((laser) -> {
            laser.update();
            laser.getHitbox().setVisible(visibleHitBoxes);
        });
        //weapon.update();

        //Set weapon hitbox visibility
        //weapon.getHitbox().setVisible(visibleHitBoxes);
        //Update the large weapon attack
        largeWeapon.stream().forEach((largeLaser) -> {
            largeLaser.update();
            largeLaser.getHitbox().setVisible(visibleHitBoxes);
        });
        //largeWeapon.update();

        enemies.stream().forEach((enemy) -> {
            changed.put(enemy, true);
            enemy.process(changed);
        });

        projectiles.stream().forEach((p) -> {
            changed.put(p, true);
            p.process(changed);
        });

        enemies.stream().forEach((entity) -> {
            if (entity.hasCollided(rocketDog)) {
                update(rocketDog.getCurrentHealth());
            } else if (!entity.hasCollided(rocketDog)) {
                entity.setState(new DamagedState());
            }
        });

        enemies.stream().forEach((enemy) -> {
            //Update enemy
            enemy.update();

            //Set enemy hitbox visibility
            enemy.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            enemy.processCollision(rocketDog);

            enemy.getState().doAction(enemy);
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
                if(p.hasCollided(rocketDog)) {
                rocketDog.setState(new DamagedState());
                update(rocketDog.currentHealth);
                }
            } else {
                removeProjectile(p);
                --i;
            }
        }

        AidItems.stream().forEach((aidItem) -> {
            if (aidItem != null) {
                //Update enemy
                aidItem.update();

                //Set enemy hitbox visibility
                aidItem.getHitbox().setVisible(visibleHitBoxes);

                //Check for collision
                aidItem.processCollision(rocketDog);
                if (aidItem.isColliding() && aidItem.getClass() == edu.uco.sdd.rocketdog.model.ShieldItem.class) {
                    removeAidItem(aidItem);
                    aidItem.setState(new DeathState());
                    addActiveAidItem(new ActiveShield(rocketDog), 153, 150);
                    rocketDog.setScore(rocketDog.getScore() + 5);
                    update(rocketDog.getCurrentHealth());
                } else if (aidItem.isColliding() && aidItem.getClass() == edu.uco.sdd.rocketdog.model.BoostItem.class) {
                    removeAidItem(aidItem);
                    aidItem.setState(new DeathState());
                    rocketDog.setVelocity(new Point2D(40, 0));
                    addActiveAidItem(new ActiveBoost(rocketDog), 153, 150);
                    rocketDog.setScore(rocketDog.getScore() + 5);
                    update(rocketDog.getCurrentHealth());
                }
            }
        });

        ActiveAidItems.stream().forEach((activeAidItem) -> {
            //Update active power ups
            activeAidItem.update();

            //Set active power up hitbox visibility
            activeAidItem.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            enemies.forEach((enemy) -> activeAidItem.processCollision(enemy));

            if (!activeAidItem.isActive()) {
                removeActiveAidItem(activeAidItem);
                activeAidItem.setState(new DeathState());
            }
            if (activeAidItem.isColliding()) {
                activeAidItem.setActive(false);
            }

            if (!activeAidItem.isActive()) {
                removeActiveAidItem(activeAidItem);
                activeAidItem.setState(new DeathState());
            }
        });

        Hazards.stream().forEach((hazard) -> {
            //Update Hazard
            hazard.update();

            //Set hazard hitbox visibility
            hazard.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            hazard.processCollision(rocketDog);
            if (hazard.isColliding()) {
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
            if (obstruction.isColliding()) {
                //set X and Y velocity in the opposite direction
                //then update and set velocity to 0
                //this prevents RD from moving through the obstruction
                rocketDog.setVelocity(new Point2D(-rocketDog.getVelocity().getX(), -rocketDog.getVelocity().getY()));
                rocketDog.update();
                rocketDog.setVelocity(new Point2D(0, 0));
            }
        });

        enemies.stream().forEach((entity) -> {
            //Change entity states appropriately before detecting collision
            if (entity.getCurrentHealth() < 0) {
                entity.setState(new DeathState());
                removeEnemy(entity);
                entity.getState().doAction(entity);
                rocketDog.setScore(rocketDog.getScore() + 10);
                update(rocketDog.getCurrentHealth());
            }
        });

        entities.removeIf(entity -> entity.getState() instanceof DeathState);
        ActiveAidItems.removeIf(entity -> entity.getState() instanceof DeathState);
        AidItems.removeIf(entity -> entity.getState() instanceof DeathState);
        enemies.removeIf(entity -> entity.getState() instanceof DeathState);
    }

    @Override
    public void update(double currentHealth) {
        //Update score
        this.scoreText.setText("Score : " + rocketDog.getScore() + "                 Health: " + rocketDog.getCurrentHealth()  + "      \nMoving: " + rocketDog.getMoving());
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
