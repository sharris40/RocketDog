package edu.uco.sdd.rocketdog.model.Animations;

import javafx.geometry.Rectangle2D;

public class FlippingBox2AnimateStrategy extends AbstractSpitzAnimationStrategy implements IAnimateStrategy {

    public FlippingBox2AnimateStrategy() {
        super("/BoxSpriteSheet.png", new Rectangle2D[]{
            new Rectangle2D(34, 583, 128, 90),
            new Rectangle2D(218, 571, 128, 90)
        });
    }

}
