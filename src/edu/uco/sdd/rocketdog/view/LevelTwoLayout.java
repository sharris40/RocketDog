/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.view;


import edu.uco.sdd.rocketdog.model.DeliveryMan;
import edu.uco.sdd.rocketdog.model.Enemy;
import edu.uco.sdd.rocketdog.model.EntityClass;
import edu.uco.sdd.rocketdog.model.HazardSpikes;
import edu.uco.sdd.rocketdog.model.HealthItem;
import edu.uco.sdd.rocketdog.model.Level;
import edu.uco.sdd.rocketdog.model.ObstructionBrickWall;
import edu.uco.sdd.rocketdog.model.ShieldItem;
import javafx.geometry.Point2D;
/**
 *
 * @author Doobifier
 */
public class LevelTwoLayout {
    private Level level;

    public LevelTwoLayout(Level level){
        this.level = level;
    }

    public void addLayout(){
        // Hazards
        rowOfItems("HAZ",5,0,936,1);
        rowOfItems("HAZ",2,0,1180,604);
        rowOfItems("HAZ",2,0,1436,604);

        //Obstructions
        rowOfItems("OBS",5,1,744,1);
        rowOfItems("OBS",5,0,1000,1);
        rowOfItems("OBS",5,0,744,1);

        rowOfItems("OBS",5,1,1244,540);
        rowOfItems("OBS",3,0,1500,540);
        rowOfItems("OBS",5,0,1244,540);

        rowOfItems("OBS",10,1,1500,1);
        rowOfItems("OBS",10,1,1500,256);

        //Surfaces
}

private void rowOfItems(String type, int numberOfItems, int orientation, int x, int y){
        //row of obstructions
        if (type.equals("OBS")){
            if (orientation == 1){
                for (int i=0; i < numberOfItems; i++){
                level.addObstruction(new ObstructionBrickWall(new Point2D(x + (i*64),y)),64,64);
                }
            } else {
                for (int i=0; i < numberOfItems; i++){
                level.addObstruction(new ObstructionBrickWall(new Point2D(x ,y + (i*64))),64,64);
                }
            }
        } else if (type.equals("HAZ")) {
            if (orientation == 1){
                for (int i=0; i < numberOfItems; i++){
                level.addHazard(new HazardSpikes(new Point2D(x + (i*64),y)),64,64);
                }
            } else {
                for (int i=0; i < numberOfItems; i++){
                level.addHazard(new HazardSpikes(new Point2D(x ,y + (i*64))),64,64);
                }
            }
        }

    }
}


