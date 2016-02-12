/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.geometry.Point2D;
import java.util.ArrayList;

/**
 * This class is an instance for
 * every hitbox in the game, it can be assigned to other
 * objects and initialized for what type of outcome 
 * is expected depending on the type of objects colliding
 * @author Doobifier
 */
public class Hitbox extends Shape {
    private Point2D position;
    private double x;
    private double y;
    private double w; // width of box(left to right)
    private double h; // height of box(top to bottom)
    private boolean shown = true; //developer option to visually show hitbox
    private boolean intersecting = false;
    private Rectangle hitbox;
    private TangibleEntity te;
    private TangibleEntity reference;
    
    public Hitbox(Point2D pos, double w, double h, TangibleEntity t){
        this.position = pos;
        this.x = pos.getX();
        this.y = pos.getY();
        this.w = 
        this.h = h;
        this.te = t;
        hitbox = new Rectangle(x,y,w,h);
        this.updateLocation(x,y);
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setStroke(Color.GREEN);
        hitbox.setStrokeWidth(3);
    }

    public Hitbox(double x, double y, double w, double h, TangibleEntity t){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        hitbox = new Rectangle(x,y,w,h);
        this.updateLocation(x,y);
        this.te = t;
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setStroke(Color.GREEN);
        hitbox.setStrokeWidth(3);
    }
    
    @Override
    public com.sun.javafx.geom.Shape impl_configShape() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void update(){
        hitbox.setTranslateX(te.getPosition().getX());
        hitbox.setTranslateY(te.getPosition().getY());
        if (!this.shown) hitbox.setStroke(Color.TRANSPARENT);//Hide hitbox when developer turns it off
        else if(this.shown) hitbox.setStroke(Color.GREEN);//
        //if (this.intersecting) hitbox.setStroke(Color.RED); //change hitbox red when hit is detected
    }
    
    public void updateLocation(double x, double y){
        this.x = x;
        this.y = y;
        hitbox.setX(x);
        hitbox.setY(y);
    }
    
    /* 
    *Check for collision(intersection) verse another hitbox object
    */
    public Boolean intersectionCheck(Rectangle r){
        boolean intersect = r.getBoundsInParent().intersects(this.hitbox.getBoundsInParent());
        if(intersect && this.shown) {
            hitbox.setStroke(Color.RED);
            intersecting = true;
            return intersect;
        } else {
            intersecting = false;
            return intersect;
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public TangibleEntity getReference() {
        return reference;
    }    
}
