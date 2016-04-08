package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.commands.RocketDogController;
import edu.uco.sdd.rocketdog.controller.KeyMappingContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Level extends Scene implements Observer, ILevel {
    public SoundManager s;
    protected RocketDog rocketDog;
    protected RocketDogController rocketDogController; //RocketDog's controller
    final private EntityClass player;
    private ArrayList<Modification> entities;
    private ArrayList<Enemy> enemies;
    private ArrayList<AidItem> AidItems;             //container of AitItems
    private ArrayList<ActiveAidItem> ActiveAidItems; //container of ActiveAidItems
    private ArrayList<Hazard> Hazards; // container of Hazards
    private ArrayList<Obstruction> Obstructions; //container of Obstructions
    private ArrayList<Projectile> projectiles;
    private ArrayList<Surface> surfaces;
    private boolean visibleHitBoxes;
    private Group root;
    private Group levelItems;
    private Group viewportItems;
    //private KeyMappingContext keyMapping;
    protected KeyMappingContext keyMapping;
    protected boolean isDone;
    private Text scoreText;
    private int largeLaserCharge;
    final private ArrayList<LaserAttack> weapon;
    //final private LaserWeapon weapon;
    final private ArrayList<LargeLaserAttack> largeWeapon;

    public Level(Group root, int width, int height) {
        this(root, new ImageView(), width, height);
    }

    public Level(Group root, ImageView background, int width, int height) {
        super(root, width, height);
        this.root = root;

        //Initialization
        scoreText = new Text(5, 20, "");
        entities = new ArrayList<>();
        keyMapping = new KeyMappingContext();
        visibleHitBoxes = false;
        rocketDog = new RocketDog();
        rocketDogController = new RocketDogController(rocketDog, this.root, this.root, 5, 0, 1000, width, height);
        player = new EntityClass("Player");
        enemies = new ArrayList<>();
        AidItems = new ArrayList<>();
        ActiveAidItems = new ArrayList<>();
        Hazards = new ArrayList<>();
        levelItems = new Group();
        viewportItems = new Group();
        Obstructions = new ArrayList<>();
        projectiles = new ArrayList<>();
        weapon = new ArrayList();
        //largeWeapon = new LargeLaserWeapon();
        largeWeapon = new ArrayList();
        surfaces = new ArrayList<>();

        //Background Added to game
        root.getChildren().add(background);
        //root.setAlignment(Pos.TOP_LEFT);

        //Hero information added to game
        rocketDog.setPosition(new Point2D(150, 350));
        rocketDog.addEntityClass(player, 1);
        rocketDog.getHitbox().setWidth(130);
        rocketDog.getHitbox().setHeight(130);
        rocketDog.setLevel(this);
        rocketDog.setCurrentHealth(10000);

        // Add Sound?
        s = new SoundManager();

        //Invisible obstruction on screen border
        //addObstruction(new Obstruction(new Point2D(0,0)),width, 1);
        //addObstruction(new Obstruction(new Point2D(0,0)),1, height);
        //addObstruction(new Obstruction(new Point2D(0,height)),width, 1);
        //addObstruction(new Obstruction(new Point2D(width,0)),1, height);

        //Laser Weapon information added to game
        for (int i = 0; i < 3; i++) {
            weapon.add(new LaserAttack());
            getLaserWeapon(i).setPosition(new Point2D(0, -150));
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
            getLargeLaserWeapon(i).setPosition(new Point2D(0, -150));
            getLargeLaserWeapon(i).getHitbox().setWidth(200);
            getLargeLaserWeapon(i).getHitbox().setHeight(133);
            getLargeLaserWeapon(i).getHitbox().setStroke(Color.TRANSPARENT);
            root.getChildren().add(getLargeLaserWeapon(i).getSprite());
            root.getChildren().add(getLargeLaserWeapon(i).getHitbox());
        }

        //levelItems.getChildren().add(rocketDog.getHitbox());
        viewportItems.getChildren().add(scoreText);

        scoreText.setText("Score : " + rocketDog.getScore() + "                 Health: " + rocketDog.getCurrentHealth());
        scoreText.setFont(new Font(20));

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
        enemy.setCurrentHealth(10);
        enemy.setLevel(this);

        //Add enemy information to level
        enemies.add(enemy);
        this.levelItems.getChildren().add(enemy.getSprite());
        if(!enemy.isMultiHibox()){
            this.levelItems.getChildren().add(enemy.getHitbox());
        } else if (enemy.isMultiHibox()){
            enemy.getHitboxes().stream().forEach((hitbox) -> {
                this.levelItems.getChildren().add(hitbox);
            });
        }
    }

    public void removeEnemy(Enemy enemy) {
        //Make sure the ArrayList has the enemy within it
        //before tyring to remove

        //Make sure the root has the enemy in its children
        //before ting to remove
        levelItems.getChildren().remove(enemy.getSprite());

        //Make sure the root has the enemy in its children
        //before ting to remove
        levelItems.getChildren().remove(enemy.getHitbox());
        root.getChildren().remove(enemy.getHitbox());
        enemies.remove(enemy);
    }

    public void addAidItem(AidItem aidItem, double width, double height) {
        //Setup powerup hitbox information
        aidItem.setLevel(this);
        aidItem.getHitbox().setWidth(width);
        aidItem.getHitbox().setHeight(height);

        //Add powerup information to level
        AidItems.add(aidItem);
        levelItems.getChildren().add(aidItem.getSprite());
        levelItems.getChildren().add(aidItem.getHitbox());
    }

    public void removeAidItem(AidItem aidItem) {
        //Make sure the ArrayList has the item within it 
        //before tyring to remove

        //Make sure the root has the item in its children
        //before ting to remove
        levelItems.getChildren().remove(aidItem.getSprite());

        //Make sure the root has the item in its children
        //before ting to remove
        levelItems.getChildren().remove(aidItem.getHitbox());
    }

    public void addActiveAidItem(ActiveAidItem activeAidItem, double width, double height) {
        //Setup active powerup hitbox information
        activeAidItem.getHitbox().setWidth(width);
        activeAidItem.getHitbox().setHeight(height);
        activeAidItem.setLevel(this);
        activeAidItem.addObserver(this);
        activeAidItem.setCurrentHealth(3);
        //Add active powerup information to level
        ActiveAidItems.add(activeAidItem);
        viewportItems.getChildren().add(activeAidItem.getSprite());
        viewportItems.getChildren().add(activeAidItem.getHitbox());
    }

    public void removeActiveAidItem(ActiveAidItem activeAidItem) {
        //Make sure the ArrayList has the item within it 
        //before tyring to remove

        //Make sure the root has the item in its children
        //before ting to remove
        if (viewportItems.getChildren().indexOf(activeAidItem.getSprite()) > -1) {
            viewportItems.getChildren().remove(activeAidItem.getSprite());
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (viewportItems.getChildren().indexOf(activeAidItem.getHitbox()) > -1) {
            viewportItems.getChildren().remove(activeAidItem.getHitbox());
        }
    }

    public void addHazard(Hazard hazard, double width, double height) {
        //Setup powerup hitbox information
        hazard.getHitbox().setWidth(width);
        hazard.getHitbox().setHeight(height);

        //Add powerup information to level
        Hazards.add(hazard);
        levelItems.getChildren().add(hazard.getSprite());
        levelItems.getChildren().add(hazard.getHitbox());
    }

    public void removeHazard(Hazard hazard) {
        //Make sure the ArrayList has the item within it 
        //before tyring to remove
        if (Hazards.indexOf(hazard) > -1) {
            Hazards.remove(hazard);
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (levelItems.getChildren().indexOf(hazard.getSprite()) > -1) {
            levelItems.getChildren().remove(hazard.getSprite());
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (levelItems.getChildren().indexOf(hazard.getHitbox()) > -1) {
            levelItems.getChildren().remove(hazard.getHitbox());
        }
    }

    public void addSurface(Surface surface) {
        surfaces.add(surface);
        levelItems.getChildren().add(surface.getSprite());
        levelItems.getChildren().add(surface.getHitbox());
    }

    public void removeSurface(Surface surface) {
        surfaces.remove(surface);
        levelItems.getChildren().remove(surface.getSprite());
        levelItems.getChildren().remove(surface.getHitbox());
    }

    public void addObstruction(Obstruction obstruction, double width, double height) {
        //Setup powerup hitbox information
        obstruction.setLevel(this);
        obstruction.getHitbox().setWidth(width);
        obstruction.getHitbox().setHeight(height);

        //Add powerup information to level
        Obstructions.add(obstruction);
        if (obstruction.isVisible()){
            levelItems.getChildren().add(obstruction.getSprite());
        }
        
        levelItems.getChildren().add(obstruction.getHitbox());
    }

    public void removeObstruction(Obstruction obstruction) {
        //Make sure the ArrayList has the item within it 
        //before tyring to remove
        if (Obstructions.indexOf(obstruction) > -1) {
            Obstructions.remove(obstruction);
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (levelItems.getChildren().indexOf(obstruction.getSprite()) > -1) {
            levelItems.getChildren().remove(obstruction.getSprite());
        }

        //Make sure the root has the item in its children
        //before ting to remove
        if (levelItems.getChildren().indexOf(obstruction.getHitbox()) > -1) {
            levelItems.getChildren().remove(obstruction.getHitbox());
        }
    }

    public void addProjectile(Projectile p, double width, double height) {
        //Setup enemy hitbox information
        p.getHitbox().setWidth(width);
        p.getHitbox().setHeight(height);

        //Add enemy information to level
        projectiles.add(p);
        levelItems.getChildren().add(p.getSprite());
        levelItems.getChildren().add(p.getHitbox());
    }

    public void removeProjectile(Projectile p) {
        //Make sure the ArrayList has the enemy within it
        //before tyring to remove
        if (projectiles.indexOf(p) > -1) {
            projectiles.remove(p);
        }

        //Make sure the root has the enemy in its children
        //before ting to remove
        if (levelItems.getChildren().indexOf(p.getSprite()) > -1) {
            levelItems.getChildren().remove(p.getSprite());
        }

        //Make sure the root has the enemy in its children
        //before ting to remove
        if (levelItems.getChildren().indexOf(p.getHitbox()) > -1) {
            levelItems.getChildren().remove(p.getHitbox());
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

    protected void finishLevel() {
        root.getChildren().add(rocketDog.getSprite());
    }

    public void setVisibleHitBoxes(boolean value) {
        visibleHitBoxes = value;
    }

    public int checkFiredLaser() {
        for (int i = 0; i < weapon.size(); i++) {
            if (weapon.get(i).getPosition().getX() > super.getWidth() || weapon.get(i).getPosition().getX() < 0) {
                weapon.get(i).setDead(false);
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

    public int largeLaserCharge(){
        if(largeLaserCharge >= 3){
            largeLaserCharge = 0;
        } 
        return largeLaserCharge += 1;
    }

    public int getLargeLaserCharge(){
        return largeLaserCharge;
    }

    public void updateKeys() {
        //this.setOnKeyPressed((KeyEvent event) -> {
        //    keyMapping.getKeyMapping().handleKeyPressed(this, event, 3.0d + rocketDog.getAgilityAttribute());
        //});

        //this.setOnKeyReleased((KeyEvent event) -> {
        //    keyMapping.getKeyMapping().handleKeyReleased(this, event, 0.0d);
        //});
        this.setOnKeyPressed((KeyEvent event) -> {
            keyMapping.getKeyMapping().handleKeyPressed(rocketDogController, this, event, 3.0d + rocketDog.getAgilityAttribute());
        });

        this.setOnKeyReleased((KeyEvent event) -> {
            keyMapping.getKeyMapping().handleKeyReleased(rocketDogController, this, event, 0.0d);
        });
    }

    @Override
    public void levelUpdate() {
        //Keyboard Handling
        //updateKeys();

        updateKeys();
        //update(1);
        //Update RocketDog
        //rocketDog.update();
        if (rocketDog.getPosition().getX() > 500 && rocketDog.getLuckAttribute() > 1 && Math.random() > 0.9991) {
            this.addAidItem(new HealthItem(new Point2D(100, 1)), 56, 56);
        }

        //Set rocketDog hitbox visibility
        rocketDog.getHitbox().setVisible(visibleHitBoxes);
        rocketDog.getHealthText().setVisible(visibleHitBoxes);
        rocketDog.addObserver(this);

        Map<Entity, Boolean> changed = new HashMap<>();
        changed.put(rocketDog, true);
        //Update the weapon attack
        weapon.stream().forEach((laser) -> {
            if (laser.getPosition().getX() > super.getWidth() || laser.getPosition().getX() < 0) {
                laser.setPos(0, -45);
                laser.setDead(false);
                laser.setVisableOff();
                laser.setVel(0, 0);
            }
            laser.update();
            laser.getHitbox().setVisible(visibleHitBoxes);

            for(int i = 0; i < enemies.size(); i++){
                if (laser.hasCollided(enemies.get(i))){
                laser.setPos(0, -45);
                laser.setDead(false);
                laser.setVisableOff();
                laser.setVel(0, 0);
                rocketDog.setScore(rocketDog.getScore() + 20);
                update(rocketDog.getScore());
                enemies.get(i).setCurrentHealth(enemies.get(i).getCurrentHealth() - 2);
                update(enemies.get(i).getCurrentHealth());

                if(enemies.get(i).currentHealth <= 0){
                    removeEnemy(enemies.get(i));
                    }
                }
            }
        });
        //weapon.update();

        //Set weapon hitbox visibility
        //weapon.getHitbox().setVisible(visibleHitBoxes);
        //Update the large weapon attack
        largeWeapon.stream().forEach((largeLaser) -> {
            if (largeLaser.getPosition().getX() > super.getWidth() || largeLaser.getPosition().getX() < 0) {
                largeLaser.setPos(0, -150);
                largeLaser.setDead(false);
                largeLaser.setVisableOff();
                largeLaser.setVel(0, 0);
            }
            largeLaser.update();
            largeLaser.getHitbox().setVisible(visibleHitBoxes);

            for(int i = 0; i < enemies.size(); i++){
                if (largeLaser.hasCollided(enemies.get(i))){
                    largeLaser.setPos(0, -150);
                    largeLaser.setDead(false);
                    largeLaser.setVisableOff();
                    largeLaser.setVel(0, 0);
                    rocketDog.setScore(rocketDog.getScore() + 50);
                    update(rocketDog.getScore());
                    enemies.get(i).setCurrentHealth(enemies.get(i).getCurrentHealth() - 5);
                    update(enemies.get(i).getCurrentHealth());
                    if(enemies.get(i).currentHealth <= 0)
                        removeEnemy(enemies.get(i));
                }
            }
        });
        //largeWeapon.update();

        enemies.stream().forEach((enemy) -> {
            changed.put(enemy, true);
            enemy.process(changed);
        });

        projectiles.stream().forEach((p) -> {
            changed.put(p, true);
            if (p.hasCollided(rocketDog)) {
                p.setDead(true);
                rocketDog.setCurrentHealth(rocketDog.getCurrentHealth() - 5);
                rocketDog.getHitbox().setStroke(Color.RED);
                update(rocketDog.getCurrentHealth());
            }
            p.process(changed);
        });

        enemies.stream().forEach((entity) -> {
            if (entity.hasCollided(rocketDog)) {
                if (rocketDog.getPowerAttribute() > 1) {
                    entity.setCurrentHealth(entity.getCurrentHealth() - rocketDog.getPowerAttribute());
                }
                if (rocketDog.getDefenseAttribute() < 2) {
                    rocketDog.setCurrentHealth(rocketDog.getCurrentHealth() - rocketDog.getDefenseAttribute());
                }
                entity.getHitbox().setStroke(Color.RED);
                
                rocketDog.getHitbox().setStroke(Color.RED);
                update(rocketDog.getCurrentHealth());
            } else {
                entity.getHitbox().setStroke(Color.GREEN);
            }
        });

        enemies.stream().forEach((enemy) -> {
            //Update enemy
            enemy.update();

            //Set enemy hitbox visibility
            enemy.getHitbox().setVisible(visibleHitBoxes);
            if (enemy.isMultiHibox()){
                enemy.getHitboxes().stream().forEach((hitbox) -> {
                    hitbox.setVisible(visibleHitBoxes);
                });
            }

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
                if (p.hasCollided(rocketDog)) {
                    rocketDog.setCurrentHealth(rocketDog.getCurrentHealth() - rocketDog.getDefenseAttribute());
                    rocketDog.getHitbox().setStroke(Color.RED);
                    update(rocketDog.currentHealth);
                } else {
                    rocketDog.getHitbox().setStroke(Color.GREEN);
                }
            } else {
                removeProjectile(p);
                --i;
            }
        }

        AidItems.stream().forEach((aidItem) -> {
                //Update AidItem
                aidItem.update();

                //Set enemy hitbox visibility
                aidItem.getHitbox().setVisible(visibleHitBoxes);

                //Check for collision
                //aidItem.processCollision(rocketDog);
                aidItem.processCollision(rocketDog);

                if (aidItem.isDead()) {   //aid item has been collected
                    rocketDog.setScore(rocketDog.getScore() + 5);
                    rocketDog.setPowerAttribute(25);
                    rocketDog.setDefenseAttribute(50);
                    rocketDog.setAgilityAttribute(5);
                    rocketDog.setLuckAttribute(2);
                    update(rocketDog.getCurrentHealth());
                    removeAidItem(aidItem);
                } else if (aidItem.isColliding() && aidItem.getClass() == edu.uco.sdd.rocketdog.model.BoostItem.class) {
                    removeAidItem(aidItem);
                }
            
        });

        ActiveAidItems.stream().forEach((activeAidItem) -> {
            //Update active power ups
            activeAidItem.update();

            //Set active power up hitbox visibility
            activeAidItem.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            enemies.forEach((enemy) -> activeAidItem.processCollision(enemy));
            projectiles.forEach((projectile) -> activeAidItem.processCollision(projectile));
            if (!activeAidItem.isActive()) {
                rocketDog.setPowerAttribute(0);
                rocketDog.setAgilityAttribute(1);
                rocketDog.setLuckAttribute(1);
                rocketDog.setDefenseAttribute(1);
                update(rocketDog.getCurrentHealth());
                removeActiveAidItem(activeAidItem);
            }
        });

        Hazards.stream().forEach((hazard) -> {
            //Update Hazard
            hazard.update();

            //Set hazard hitbox visibility
            hazard.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            hazard.processCollision(rocketDog);

        });

        Obstructions.stream().forEach((obstruction) -> {
            //Update Hazard
            obstruction.update();

            //Set hazard hitbox visibility
            obstruction.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            obstruction.processCollision(rocketDog);

        });

        surfaces.stream().forEach(surface -> {
            surface.update();
            surface.getHitbox().setVisible(visibleHitBoxes);
            surface.process(changed);
        });

        enemies.stream().forEach((entity) -> {
            //Change entity states appropriately before detecting collision
            if (entity.getCurrentHealth() <= 0) {
                removeEnemy(entity);
                rocketDog.setScore(rocketDog.getScore() + 10);
                update(rocketDog.getCurrentHealth());
            }
        });

        entities.removeIf(entity -> entity.getCurrentHealth() <= 0);
        ActiveAidItems.removeIf(entity -> entity.getCurrentHealth() <= 0);
        AidItems.removeIf(entity -> entity.getCurrentHealth() <= 0);
        enemies.removeIf(entity -> entity.getCurrentHealth() <= 0);
    }

    @Override
    public void update(double currentHealth) {
        //Update score
        this.scoreText.setText(
                "Score : " + rocketDog.getScore() + "                 Health: " + rocketDog.getCurrentHealth()
                + "\nPower:   " + rocketDog.getPowerAttribute()
                + "                 Charge: " + getLargeLaserCharge()
                + "\nDefense: " + rocketDog.getDefenseAttribute()
                + "\nAgility: " + rocketDog.getAgilityAttribute()
                + "\nLuck:    " + rocketDog.getLuckAttribute());
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean value) {
        isDone = value;
    }

    public Group getLevelItems() {
        return levelItems;
    }

    public ArrayList<Obstruction> getObstructions() {
        return (ArrayList<Obstruction>)Obstructions.clone();
    }

    public void setRocketDog(RocketDog rocketDog) {
        this.rocketDog = rocketDog;
    }

    public Group getViewportItems() {
        return viewportItems;
    }


}
