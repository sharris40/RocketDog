package edu.uco.sdd.rocketdog.commands;

import edu.uco.sdd.rocketdog.model.TangibleEntity;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public class MoveRight extends AbstractCommand {

    private final TangibleEntity tangibleEntity;
    private final int focalSpeed;
    
    public MoveRight(TangibleEntity tangibleEntity, int focalSpeed) {
        this.tangibleEntity = tangibleEntity;
        this.focalSpeed = focalSpeed;
    }

    @Override
    public void execute() {
        //Node sprite = tangibleEntity.getSprite();
        tangibleEntity.getSprite().setScaleX(1);
        tangibleEntity.setHorzSpeed(focalSpeed);
        tangibleEntity.setVelocity(new Point2D(focalSpeed, tangibleEntity.getVelocity().getY()));
        tangibleEntity.setMoving(true);
        //tangibleEntity.setVel(+7,0);
        
        //sprite.setScaleX(1);
        //sprite.setTranslateX(sprite.getTranslateX() + focalSpeed);
    }

}
