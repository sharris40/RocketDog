package edu.uco.sdd.rocketdog.commands;

import edu.uco.sdd.rocketdog.model.TangibleEntity;
import javafx.geometry.Point2D;

public class MoveUp extends AbstractCommand {
    private final TangibleEntity tangibleEntity;
    private final int focalSpeed;

    public MoveUp(TangibleEntity tangibleEntity, int focalSpeed) {
        this.tangibleEntity = tangibleEntity;
        this.focalSpeed = focalSpeed;
    }

    @Override
    public void execute() {
        // Don't go above level
        //if (tangibleEntity.getSprite().getTranslateY() - focalSpeed <=0 ) {
            //return;
        //}
        tangibleEntity.setVertSpeed(-focalSpeed);
        tangibleEntity.setVelocity(new Point2D(tangibleEntity.getVelocity().getX(), -focalSpeed));
        tangibleEntity.setMoving(true);
        
    }

}
