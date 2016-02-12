package edu.uco.sdd.rocketdog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class Level1 extends Scene implements Level {

    RocketDog rd;
    boolean moved;
    BadGuy hench;
    BadGuy hench2;
    ArrayList<BadGuy> BadGuys;
    Hitbox hitbox;
    ArrayList<Hitbox> Hitboxes;
    boolean visibleHitboxes = false;
    
    public Level1(StackPane root, int x, int y) {
        super(root, x, y);

        // Background
        Node bg = new ImageView(new Image("/Level 2.png"));
        root.getChildren().add(bg);
        root.setAlignment(Pos.TOP_LEFT); // So Origin is in top left

        // Hero
        rd = new RocketDog();
        rd.setPosition(new Point2D(70, 700));
        EntityClass player = new EntityClass("Player");
        rd.addEntityClass(player, 1);
        root.getChildren().add(rd.sprite);
        hitbox = new Hitbox(rd.getPosition(),130,130,rd);
        root.getChildren().add(hitbox.getHitbox());
        
        Hitboxes = new ArrayList();
        //Hitboxes.add(hitbox);

        // Bad Guys
        BadGuys = new ArrayList();
        EntityClass enemy = new EntityClass("Enemy");
        enemy.setRelationship(player, EntityClass.Relationship.ENEMY);
        BadGuys.add(new BadGuy.Builder("/Ugly Dog.png",32,32).setX(650).setY(700).setEntityClass(enemy).build());
        BadGuys.add(new BadGuy.Builder("/Ugly Dog.png",64,64).setX(500).setY(700).setEntityClass(enemy).build());
        Hitbox bguy = new Hitbox(BadGuys.get(0).getPosition(),32,32,BadGuys.get(0));
        Hitboxes.add(bguy);
        bguy = new Hitbox(BadGuys.get(1).getPosition(),64,64,BadGuys.get(1));
        Hitboxes.add(bguy);
        
        // Add to view
        for (BadGuy b : BadGuys) {
            root.getChildren().add(b.sprite);
        }
        
        for (Hitbox h : Hitboxes){
            root.getChildren().add(h.getHitbox());
        }

        // Set Controls
        this.setOnKeyPressed((KeyEvent event) -> {
            double deltax = 0., deltay = 0.;
            switch (event.getCode()) {
              case LEFT:  deltax = -10; break;
              case RIGHT: deltax =  10; break;
              case UP:    deltay = -10; break;
              case DOWN:  deltay =  10; break;
              case H: if (visibleHitboxes) {
                  visibleHitboxes = false;
              } else {
                  visibleHitboxes = true;
              }
            }
            rd.setPosition(rd.getPosition().add(deltax, deltay));
        });
    }

    @Override
    public void update() {
        rd.update();
        hitbox.update();
        hitbox.setShown(visibleHitboxes);
        
        Map<Entity, Boolean> changed = new HashMap<>();
        for (BadGuy b: BadGuys) {
            changed.put(b, true);
        }
        changed.put(rd, true);
        for (BadGuy b: BadGuys) {
            b.process(changed);
        }
        for (BadGuy b: BadGuys) {
          b.update();
        }
        for (Hitbox h: Hitboxes){
            h.update();
            h.setShown(visibleHitboxes);
            hitbox.intersectionCheck(h.getHitbox());
            h.intersectionCheck(hitbox.getHitbox());
        }
    }
}
