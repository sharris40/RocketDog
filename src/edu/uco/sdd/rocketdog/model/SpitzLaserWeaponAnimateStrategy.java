package edu.uco.sdd.rocketdog.model;

import javafx.geometry.Rectangle2D;

/*
 * @author Kody
 */
public class SpitzLaserWeaponAnimateStrategy extends AbstractSpitzAnimationStrategy implements IAnimateStrategy {
    
    public SpitzLaserWeaponAnimateStrategy(){
        super("/Shoot.png", new Rectangle2D[]{
            //Width, Height (where) Width, Height (img)
            new Rectangle2D(592, 44, 44, 43),
            new Rectangle2D(702, 43, 44, 44),
            new Rectangle2D(813, 43, 44, 44),
            new Rectangle2D(923, 43, 43, 44)
        });
    }
}
