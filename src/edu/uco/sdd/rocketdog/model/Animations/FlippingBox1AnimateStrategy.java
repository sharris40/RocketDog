package edu.uco.sdd.rocketdog.model.Animations;

import javafx.geometry.Rectangle2D;

public class FlippingBox1AnimateStrategy extends AbstractSpitzAnimationStrategy implements IAnimateStrategy {

    public FlippingBox1AnimateStrategy() {
        super("/BoxSpriteSheet.png", new Rectangle2D[]{
            new Rectangle2D(27, 423, 128, 90),
            new Rectangle2D(213, 420, 128, 90)
        });
    }

}
