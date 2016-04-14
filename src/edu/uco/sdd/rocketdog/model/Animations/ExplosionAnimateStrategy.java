package edu.uco.sdd.rocketdog.model.Animations;

import javafx.geometry.Rectangle2D;

public class ExplosionAnimateStrategy extends AbstractSpitzAnimationStrategy implements IAnimateStrategy {

    public ExplosionAnimateStrategy() {
        super("/Explosion.png", new Rectangle2D[]{
            new Rectangle2D(10.5, 8, 294, 254),
            new Rectangle2D(393, 8, 294, 254),
            new Rectangle2D(762.5, 8, 294, 254),
            new Rectangle2D(1149.5, 8, 294, 254),
            new Rectangle2D(1541, 8, 294, 254),
            new Rectangle2D(1956, 8, 294, 254),
            new Rectangle2D(2380, 8, 294, 254),
            new Rectangle2D(2735.5, 8, 294, 254)
        });
    }

}
