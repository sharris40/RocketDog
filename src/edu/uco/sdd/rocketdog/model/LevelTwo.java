package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.commands.RocketDogController;
import edu.uco.sdd.rocketdog.controller.RocketDogGame;
import edu.uco.sdd.rocketdog.model.Animations.SpitzIdleAnimateStrategy;
import edu.uco.sdd.rocketdog.view.Props;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Dubs
 */
public class LevelTwo extends Level implements ILevel {

    final public static int LEVEL_WIDTH = 3000; // Stage is 1000x924
    final public static int LEVEL_HEIGHT = 924;
    final public static int FOCAL_SPEED = 25;
    final public static int VIEWPORT_MAX_X = RocketDogGame.GAME_SCREEN_WIDTH / 2;
    final public static int VIEWPORT_MIN_X = 0;

    private Group backgroundGroup;
    private Group viewportGroup;
    private Text viewportCoordinates;
    private Boolean isDone;
    private RocketDog rocketdog;
    private RocketDogController gameController;
    SoundManager soundManager = new SoundManager();

    public LevelTwo(Group root, int width, int height, SoundManager soundManager) {
        super(root, width, height);
        root.setAutoSizeChildren(false);
        isDone = false;

        // Initialize Groups
        backgroundGroup = new Group();
        viewportGroup = new Group();

        // Initialize ROcketdog
        //rocketdog = new RocketDog();
        rocketdog = getRocketDog();
        //rocketdog.setPosition(new Point2D(150,300));
        rocketdog.setAnimation(new SpitzIdleAnimateStrategy());

        // Initialize Viewport
        soundManager.resetMediaPlayer(soundManager.getMp_bg(), "intense.mp3");

        soundManager.mp_bg.setVolume(0.04);
        soundManager.mp_bg.setCycleCount(100);
        soundManager.mp_am.setMute(true);

        this.soundManager = soundManager;

        viewportGroup.getChildren().add(rocketdog.getSprite());

        // Initialize Background objects
        backgroundGroup.getChildren().add(Props.sunAndSky());
        backgroundGroup.getChildren().add(Props.sod(0, 2000, LEVEL_HEIGHT));
        backgroundGroup.getChildren().add(Props.house(0, LEVEL_HEIGHT - 300));
        backgroundGroup.getChildren().add(Props.house(LEVEL_WIDTH - 300, LEVEL_HEIGHT - 300));
        

        // Add Viewport + Background to root
        root.getChildren().add(backgroundGroup);
        root.getChildren().add(viewportGroup);

        // All Commands go through gameController
        gameController = new RocketDogController(
                rocketdog, backgroundGroup, viewportGroup,
                FOCAL_SPEED,VIEWPORT_MIN_X,VIEWPORT_MAX_X,LEVEL_WIDTH, LEVEL_HEIGHT
        );

        // Set up key controller
        this.setOnKeyPressed((KeyEvent event) -> {
            keyMapping.getKeyMapping().handleKeyPressed(gameController, this, event, 0.0d + super.getRocketDog().getAgilityAttribute());

            /*switch (event.getCode()) {
                case LEFT:
                    gameController.moveLeftButton();
                    break;
                case RIGHT:
                    gameController.moveRightButton();
                    break;
                case UP:
                    gameController.moveUpButton();
                    break;
                case DOWN:
                    gameController.moveDownButton();
                    break;
                case SPACE:
                    gameController.shootButton();
                    break;
            }*/
        });
        this.setOnKeyReleased((KeyEvent event) -> {
          keyMapping.getKeyMapping().handleKeyReleased(gameController, this, event, 0.0d);
        });
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void levelUpdate() {
        super.levelUpdate();
        //rocketdog.update();
    }
}
