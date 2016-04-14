package edu.uco.sdd.rocketdog.model.Animations;

import javafx.geometry.Rectangle2D;

public class FragileRotatingBoxAnimateStrategy extends AbstractSpitzAnimationStrategy implements IAnimateStrategy {

    public FragileRotatingBoxAnimateStrategy() {
        super("/BoxSpriteSheet.png", new Rectangle2D[]{
            new Rectangle2D(61, 26, 152, 156),
            new Rectangle2D(245, 24, 152, 156),
            new Rectangle2D(428, 22, 152, 156),
            new Rectangle2D(625, 34, 152, 156),
            new Rectangle2D(63, 204, 152, 156),
            new Rectangle2D(256, 201, 152, 156),
            new Rectangle2D(467, 217, 152, 156),
            new Rectangle2D(630, 202, 152, 156)
        });
    }

}
