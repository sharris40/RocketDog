/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import javafx.geometry.Rectangle2D;

/**
 *
 * @author Kody
 */
public class SpitzLargeLaserWeaponAnimateStrategy extends AbstractSpitzAnimationStrategy implements IAnimateStrategy {
    
    public SpitzLargeLaserWeaponAnimateStrategy(){
        super("/Shoot.png", new Rectangle2D[]{
           new Rectangle2D(20, 127, 202, 133),
           new Rectangle2D(277, 128, 201, 131),
           new Rectangle2D(527, 127, 203, 133),
           new Rectangle2D(785, 128, 198, 131)
        });
    }
}
