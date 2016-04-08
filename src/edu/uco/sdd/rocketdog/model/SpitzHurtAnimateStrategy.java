package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.model.Animations.AbstractSpitzAnimationStrategy;
import edu.uco.sdd.rocketdog.model.Animations.IAnimateStrategy;
import javafx.geometry.Rectangle2D;

public class SpitzHurtAnimateStrategy extends AbstractSpitzAnimationStrategy implements IAnimateStrategy {

    public SpitzHurtAnimateStrategy() {
        super("/Spitzer_Hurt.png", new Rectangle2D[]{
            new Rectangle2D(60, 24, 125, 148),
            new Rectangle2D(240, 24, 128, 145),
            new Rectangle2D(417, 24, 120, 145),
        });
    }

}
