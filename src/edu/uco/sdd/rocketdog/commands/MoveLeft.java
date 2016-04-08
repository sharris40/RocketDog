package edu.uco.sdd.rocketdog.commands;

import edu.uco.sdd.rocketdog.model.TangibleEntity;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

public class MoveLeft extends AbstractCommand {

    private final TangibleEntity tangibleEntity;
    private final int focalSpeed;

    public MoveLeft(TangibleEntity tangibleEntity, int focalSpeed) {
        this.tangibleEntity = tangibleEntity;
        this.focalSpeed = focalSpeed;
    }

    @Override
    public void execute() {
        tangibleEntity.getSprite().setScaleX(-1);
        tangibleEntity.setHorzSpeed(-focalSpeed);
        tangibleEntity.setVelocity(new Point2D(-focalSpeed, tangibleEntity.getVelocity().getY()));
        tangibleEntity.setMoving(true);
        //ImageView sprite = tangibleEntity.getSprite();
        //sprite.setScaleX(-1);
        //sprite.setTranslateX(sprite.getTranslateX() - focalSpeed);
    }

}
