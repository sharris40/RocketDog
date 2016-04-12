/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author Sophia
 */
public class HealthBar extends Pane {
    Rectangle healthBarBorder;
    Rectangle innerHealthBar;
    double height;
    double outerHealthBarWidth;
    double innerhealthBarWidth;

    public HealthBar() {
        this.height=10;
        this.outerHealthBarWidth=60;
        this.innerhealthBarWidth=50;

        double posX=0.0;
        double posY=0.0;

        healthBarBorder= new Rectangle(posX, posY, outerHealthBarWidth, height);//healthbar outline

        healthBarBorder.setArcHeight(5);
        healthBarBorder.setArcWidth(5);

        healthBarBorder.setStroke(Color.BLACK);
        healthBarBorder.setWidth(2);
        healthBarBorder.setStrokeType(StrokeType.OUTSIDE);
        healthBarBorder.setFill(Color.RED);

        innerHealthBar= new Rectangle(posX, posY,innerhealthBarWidth , height);// draws healthbar
        innerHealthBar.setStrokeType(StrokeType.OUTSIDE);
        innerHealthBar.setFill(Color.GREEN);

        getChildren().addAll(healthBarBorder,innerHealthBar);

    }



}