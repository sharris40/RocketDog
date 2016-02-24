package edu.uco.sdd.rocketdog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class Level extends Scene {

    final private RocketDog rocketDog;
    final private EntityClass player;
    final private ArrayList<LaserWeapon> weapon;
    //final private LaserWeapon weapon;
    final private LargeLaserWeapon largeWeapon;
    private ArrayList<Enemy> enemies;
    private boolean visibleHitBoxes;
    private StackPane root;
    private KeyMappingContext keyMapping;

    public Level(StackPane root, ImageView background, int width, int height) {
        super(root, width, height);
        this.root = root;

        //Initialization
        keyMapping = new KeyMappingContext();
        visibleHitBoxes = false;
        rocketDog = new RocketDog();
        //weapon = new LaserWeapon();
        weapon = new ArrayList();
        largeWeapon = new LargeLaserWeapon();
        player = new EntityClass("Player");
        enemies = new ArrayList();

        //Background Added to game
        root.getChildren().add(background);
        root.setAlignment(Pos.TOP_LEFT);

        //Hero information added to game
        rocketDog.setPosition(new Point2D(70, 700));
        rocketDog.addEntityClass(player, 1);
        rocketDog.getHitbox().setWidth(130);
        rocketDog.getHitbox().setHeight(130);
        root.getChildren().add(rocketDog.getSprite());
        root.getChildren().add(rocketDog.getHitbox());
        
        //Laser Weapon information added to game
        for(int i = 0; i < 3; i++){
            weapon.add(new LaserWeapon());
            getLaserWeapon(i).setPosition(new Point2D(100,600));
            getLaserWeapon(i).getHitbox().setWidth(44);
            getLaserWeapon(i).getHitbox().setHeight(44);
            root.getChildren().add(getLaserWeapon(i).getSprite());
            root.getChildren().add(getLaserWeapon(i).getHitbox());
            //weapon.getHitbox().setWidth(44);
            //weapon.getHitbox().setHeight(44);
            //root.getChildren().add(weapon.getSprite());
            //root.getChildren().add(weapon.getHitbox());
        }
          
        //Large Laser Weapon information added to game
        largeWeapon.setPosition(new Point2D(100,600));
        largeWeapon.getHitbox().setWidth(200);
        largeWeapon.getHitbox().setHeight(133);
        root.getChildren().add(largeWeapon.getSprite());
        root.getChildren().add(largeWeapon.getHitbox());

        //Keyboard Handling
        this.setOnKeyPressed((KeyEvent event) -> {
            keyMapping.getKeyMapping().handleKeyPressed(this, event, 5.0d);
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
    
    public LaserWeapon getLaserWeapon(int i){
        return weapon.get(i);
    }
    
    public LargeLaserWeapon getLargeLaserWeapon(){
        return largeWeapon;
    }

    public void addEnemy(Enemy enemy, double width, double height) {
        //Setup enemy hitbox information
        enemy.getHitbox().setWidth(width);
        enemy.getHitbox().setHeight(height);

        //Add enemy information to level
        enemies.add(enemy);
        root.getChildren().add(enemy.getSprite());
        root.getChildren().add(enemy.getHitbox());
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
    
    public int checkFired(){
        for(int i = 0; i < weapon.size(); i++){
            if(weapon.get(i).getPosition().getX() > root.getWidth()){
                 weapon.get(i).setDead(false);
            }
            if(!weapon.get(i).isDead()){
                weapon.get(i).setDead(true);
                return i;    
            }
           
        }
        return -1;        
    }
    
    public void setVisibleHitBoxes(boolean value) {
        visibleHitBoxes = value;
    }
    
    public void update() {

        //Update RocketDog
        rocketDog.update();

        //Set rocketDog hitbox visibility
        rocketDog.getHitbox().setVisible(visibleHitBoxes);
        
        //Update the weapon attack
        weapon.stream().forEach((laser) ->{
            laser.update();
        });
        //weapon.update();
        
        //Set weapon hitbox visibility
        //weapon.getHitbox().setVisible(visibleHitBoxes);
        
        //Update the large weapon attack
        largeWeapon.update();
        
        //Set large weapon hitbox visibility
        largeWeapon.getHitbox().setVisible(visibleHitBoxes);
        
        Map<Entity, Boolean> changed = new HashMap<>();
        changed.put(rocketDog, true);

        enemies.stream().forEach((enemy) -> {
            changed.put(enemy, true);
            enemy.process(changed);
        });

        enemies.stream().forEach((enemy) -> {
            //Update enemy
            enemy.update();

            //Set enemy hitbox visibility
            enemy.getHitbox().setVisible(visibleHitBoxes);

            //Check for collision
            enemy.processCollision(rocketDog);
        });
    }
}
