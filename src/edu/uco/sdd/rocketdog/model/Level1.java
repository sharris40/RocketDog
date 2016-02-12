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

        // Bad Guys
        BadGuys = new ArrayList();
        EntityClass enemy = new EntityClass("Enemy");
        enemy.setRelationship(player, EntityClass.Relationship.ENEMY);
        BadGuys.add(new BadGuy.Builder("/Ugly Dog.png",32,32).setX(650).setY(700).setEntityClass(enemy).build());
        BadGuys.add(new BadGuy.Builder("/Ugly Dog.png",64,64).setX(500).setY(700).setEntityClass(enemy).build());
        
        // Add to view
        for (BadGuy b : BadGuys) {
            root.getChildren().add(b.sprite);
        }

        // Set Controls
        this.setOnKeyPressed((KeyEvent event) -> {
            Point2D rdPosition = rd.getPosition();
            System.out.println(rdPosition);
            double deltax = 0., deltay = 0.;
            switch (event.getCode()) {
              case LEFT:  rd.getSprite().setScaleX(-1);
                          rd.setVelX(-10); 
                          break; //rd.x -= 10;         
              case RIGHT: rd.getSprite().setScaleX(1);
                          rd.setVelX(10);  
                          break; //rd.x +=  10;
              case UP:    rd.setVelY(-10); break; //rd.y -= -10; 
              case DOWN:  rd.setVelY(10); break; //rd.y +=  10; 
              case F1:  root.getChildren().remove(1);
                        rd = new RocketDog(new SpitzIdleAnimateStrategy(),rdPosition);
                        rd.addEntityClass(player, 1);
                        rd.setPosition(rdPosition);
                        rd.getSprite().setTranslateX(rdPosition.getX());
                        rd.getSprite().setTranslateY(rdPosition.getY());
                        root.getChildren().add(1,rd.getSprite());
                        break;
                        
              case F2:  root.getChildren().remove(1);
                        rd = new RocketDog(new SpitzDeadAnimateStrategy(),rdPosition);
                        rd.addEntityClass(player, 1);
                        rd.setPosition(rdPosition);
                        rd.getSprite().setTranslateX(rdPosition.getX());
                        rd.getSprite().setTranslateY(rdPosition.getY());
                        root.getChildren().add(1,rd.getSprite());
                        break;
                  
            }
            rd.setPosition(rd.getPosition().add(deltax, deltay));
        });
        
           this.setOnKeyReleased((KeyEvent event) -> {
            double deltax = 0., deltay = 0.;
            switch (event.getCode()) {
              case LEFT:  rd.setVelX(0); break;        
              case RIGHT: rd.setVelX(0);  break; 
              case UP:    rd.setVelY(0); break; 
              case DOWN:  rd.setVelY(0); break;  
            }
            rd.setPosition(rd.getPosition().add(deltax, deltay));
        });
        
        
    }

    @Override
    public void update() {
        rd.update();
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
    }
}
