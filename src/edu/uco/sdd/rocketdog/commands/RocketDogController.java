package edu.uco.sdd.rocketdog.commands;

import edu.uco.sdd.rocketdog.controller.RocketDogGame;
import edu.uco.sdd.rocketdog.model.TangibleEntity;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;

// RocketDog Controller
public class RocketDogController {

    private final TangibleEntity tangibleEntity;
    private final AbstractCommand moveLeft, moveRight, moveDown, moveUp;
    private final AbstractCommand scrollRight, scrollLeft;
    private final AbstractCommand shoot;

    private final int viewportMinX;
    private final int viewportMaxX;
    private final int levelWidth;
    private final int levelHeight;
    private final int focalSpeed;

    private final Group topLevelBackgroundGroup; // Background that will get scrolled
    private final Group bulletGroup; // Bullets go in this Group

    /**
     *
     * The constructor is huge because commands require different variables but
     * from within the level we want to only construct the controller, not each
     * command. So construct the controller with all the variables each command
     * will need and we will maintain a reference to that. Other controllers
     * could control other objects other than RocketDog
     *
     * @param tangibleEntity The TE you want to move
     * @param topLevelGroup The Background that is going to get moved
     * @param focalSpeed The Speed at which things move
     * @param viewportMinX minimum x coordinate of viewport
     * @param viewportMaxX maximum x coordinate of viewport
     * @param levelWidth maximum x coordinate of level
     * @param levelHeight maximum y coordinate of level
     */

    public RocketDogController(TangibleEntity tangibleEntity, 
            Group topLevelGroup, Group shootGroup,
            int focalSpeed, int viewportMinX, int viewportMaxX, int levelWidth,
            int levelHeight) {

        this.tangibleEntity = tangibleEntity;
        this.focalSpeed = focalSpeed;
        this.topLevelBackgroundGroup = topLevelGroup;
        this.viewportMaxX = viewportMaxX;
        this.viewportMinX = viewportMinX;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.bulletGroup = shootGroup;


        /**
         * Concrete Commands initialized here
         */
        scrollRight = new ScrollRight(topLevelGroup, focalSpeed);
        scrollLeft = new ScrollLeft(topLevelGroup, focalSpeed);

        moveRight = new MoveRight(tangibleEntity, focalSpeed);
        moveLeft = new MoveLeft(tangibleEntity, focalSpeed);
        moveUp = new MoveUp(tangibleEntity, focalSpeed);
        moveDown = new MoveDown(tangibleEntity, focalSpeed, levelHeight);


        shoot = new Shoot();

    }

    public void moveRightButton() {
        Node sprite = tangibleEntity.getSprite();
        double maxX = sprite.localToScene(sprite.getBoundsInLocal()).getMaxX();
        // Do not scroll right past end of level
        if (topLevelBackgroundGroup.getTranslateX() - RocketDogGame.GAME_SCREEN_WIDTH - focalSpeed < -levelWidth) {
            // Allow movement to the right if at the end of the level
            // but not past the end of the viewport
            if (maxX < RocketDogGame.GAME_SCREEN_WIDTH) {
                moveRight.execute();
            }
        } else if (maxX + focalSpeed > viewportMaxX) {
            scrollRight.execute();
        } else {
            moveRight.execute();
        }
    }

    public void moveLeftButton() {
        Node sprite = tangibleEntity.getSprite();
        Bounds spriteBounds = sprite.localToScene(sprite.getBoundsInLocal());
        double minX = spriteBounds.getMinX();
        if (topLevelBackgroundGroup.getTranslateX() + focalSpeed > 0 && minX < 10) {
            return;
        }
        if (minX - focalSpeed < viewportMinX) {
            scrollLeft.execute();
        } else {
            moveLeft.execute();
        }
    }

    public void moveUpButton() {
        moveUp.execute();
    }

    public void moveDownButton() {
        moveDown.execute();
    }

    public void shootButton() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
