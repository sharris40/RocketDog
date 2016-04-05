package edu.uco.sdd.rocketdog.commands;

import edu.uco.sdd.rocketdog.model.TangibleEntity;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public class MoveDown extends AbstractCommand {
    private final TangibleEntity tangibleEntity;
    private final int focalSpeed;
    private final int levelHeight;

    public MoveDown(TangibleEntity tangibleEntity, int focalSpeed, int levelHeight) {
        this.tangibleEntity = tangibleEntity;
        this.focalSpeed = focalSpeed;
        this.levelHeight = levelHeight;
    }

    @Override
    public void execute() {
        // Don't go below level
        if (absoluteBounds(tangibleEntity.getSprite()).getMaxY() > levelHeight )
            return;
        tangibleEntity.setVertSpeed(focalSpeed);
        tangibleEntity.setVelocity(new Point2D(tangibleEntity.getVelocity().getX(), focalSpeed));
        tangibleEntity.setMoving(true);
    }
}
