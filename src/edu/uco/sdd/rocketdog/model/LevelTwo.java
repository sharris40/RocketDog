package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.commands.RocketDogController;
import edu.uco.sdd.rocketdog.controller.RocketDogGame;
import edu.uco.sdd.rocketdog.model.Animations.SpitzIdleAnimateStrategy;
import edu.uco.sdd.rocketdog.view.LevelTwoLayout;
import edu.uco.sdd.rocketdog.view.Props;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Dubs
 */
public class LevelTwo extends Level {

    final public static int LEVEL_WIDTH = 3000; // Stage is 1000x924
    final public static int LEVEL_HEIGHT = 924;
    final public static int FOCAL_SPEED = 25;
    final public static int VIEWPORT_MAX_X = RocketDogGame.GAME_SCREEN_WIDTH / 2;
    final public static int VIEWPORT_MIN_X = 0;

    private Group backgroundGroup;
    private Group viewportGroup;
    private Group levelItems;
    private Text viewportCoordinates;
    private Boolean isDone;
    private RocketDogController gameController;
    private SoundManager soundManager;

    UglyDog badguy;

    public LevelTwo(Group root, int width, int height, SoundManager soundManager) {
        super(root, width, height);

        root.setAutoSizeChildren(false);
        isDone = false;

        // Initialize Groups
        backgroundGroup = new Group();
        viewportGroup = new Group();
        levelItems = getLevelItems();

        // Initialize Rocketdog
        //rocketdog = new RocketDog();
        // Initialize ROcketdog
        //rocketdog = new RocketDog();
        //rocketDog = getRocketDog();
        //rocketDog.setPosition(new Point2D(150,300));
        //rocketDog.setAnimation(new SpitzIdleAnimateStrategy());
        // Initialize ROcketdog
        //rocketdog = new RocketDog();
        //rocketdog.setAnimation(new SpitzIdleAnimateStrategy());
        //rocketDog.getSprite().setTranslateY(rocketDog.getSprite().getTranslateY() + 500);

        // Initialize sound
        soundManager = new SoundManager();
//        soundManager.resetMediaPlayer(soundManager.getMp_bg(), "intense.mp3");
//        soundManager.mp_bg.setVolume(0);
//        soundManager.mp_bg.setCycleCount(100);
//        soundManager.mp_am.setMute(true);

        // Initialize Viewport
        viewportGroup.getChildren().add(rocketDog.getSprite());
        viewportGroup.getChildren().add(rocketDog.getHitbox());
        viewportGroup.getChildren().add(getViewportItems());

        // Initialize Background objects
        backgroundGroup.getChildren().add(Props.sunAndSky());
        backgroundGroup.getChildren().add(Props.sod(0, 2000, LEVEL_HEIGHT));
        backgroundGroup.getChildren().add(Props.house(0, LEVEL_HEIGHT - 300));
        backgroundGroup.getChildren().add(Props.house(LEVEL_WIDTH - 300, LEVEL_HEIGHT - 300));
        backgroundGroup.getChildren().add(levelItems);

        //Add Level Items - Aid , enemies, obstructions etc
        //Add AidItems
        addAidItem(new HealthItem(new Point2D(1364, 700)), 56, 56);
        addAidItem(new ShieldItem(new Point2D(810, 350)), 56, 56);

        // Hazards
        //Obstructions
        LevelTwoLayout l = new LevelTwoLayout(this);
        l.addLayout();

        // Bad Guys
        EntityClass enemy = new EntityClass("Enemy");
        enemy.setRelationship(getPlayer(), EntityClass.Relationship.ENEMY);
        addEnemy(new Enemy.Builder("/Ugly Dog.png", 64, 64).setX(1350).setY(476).setStart(1244).setEnd(1500).setRange(300).setEntityClass(enemy).setLevel(this).build(), 64, 64);
        addEnemy(new Enemy.Builder("/Ugly Dog.png", 32, 32).setX(1692).setY(224).setStart(50).setEnd(650).setRange(300).setEntityClass(enemy).setLevel(this).build(), 32, 32);
        addEnemy(new DeliveryMan(2000, 400), 400, 400);

        //Surfaces
        // Add Viewport + Background to root
        root.getChildren().add(backgroundGroup);
        root.getChildren().add(viewportGroup);

        badguy = new UglyDog("/Ugly Dog.png");
        badguy.setTranslateX(500);
        badguy.setTranslateY(500);

        backgroundGroup.getChildren().add(badguy);

        // All Commands go through gameController
        gameController = new RocketDogController(
                rocketDog, backgroundGroup, viewportGroup,
                FOCAL_SPEED, VIEWPORT_MIN_X, VIEWPORT_MAX_X, LEVEL_WIDTH, LEVEL_HEIGHT
        );

        // Set up key controller
        this.setOnKeyPressed((KeyEvent event) -> {
            super.keyMapping.getKeyMapping().handleKeyPressed(gameController, this, event, 0.0d + super.getRocketDog().getAgilityAttribute());

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
             gameController.shootButton(backgroundGroup);
             break;
             case H:
             setVisibleHitBoxes(true);
             }
             }*/
        });
        this.setOnKeyReleased((KeyEvent event) -> {
            super.keyMapping.getKeyMapping().handleKeyReleased(gameController, this, event, 0.0d);
        });
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    protected Bounds absoluteBounds(Node x) {
        return x.localToScene(x.getBoundsInLocal());
    }

    public boolean levelIntersect(Node x, Node y) {
        return absoluteBounds(x).intersects(absoluteBounds(y));
    }

    @Override
    public void updateKeys() {
        //this.setOnKeyPressed((KeyEvent event) -> {
        //    keyMapping.getKeyMapping().handleKeyPressed(this, event, 3.0d + rocketDog.getAgilityAttribute());
        //});

        //this.setOnKeyReleased((KeyEvent event) -> {
        //    keyMapping.getKeyMapping().handleKeyReleased(this, event, 0.0d);
        //});
        this.setOnKeyPressed((KeyEvent event) -> {
            keyMapping.getKeyMapping().handleKeyPressed(rocketDogController, this, event, 3.0d + rocketDog.getAgilityAttribute());
        });

        this.setOnKeyReleased((KeyEvent event) -> {
            keyMapping.getKeyMapping().handleKeyReleased(rocketDogController, this, event, 0.0d);
        });
    }

    @Override
    public void levelUpdate() {
        super.levelUpdate();
        super.update(0);
        updateKeys();
        rocketDog.update();
        for (Node x : backgroundGroup.getChildren()) {
            if (x instanceof Bullet) {
                x.setTranslateX(x.getTranslateX() + 5);
                Bullet b = (Bullet) x;
                b.update();
                if (levelIntersect(b, badguy)) {
                    badguy.setTranslateY(badguy.getTranslateY() - 5);

                }
            }
        }

        if (levelIntersect(rocketDog.getSprite(), badguy)) {
            badguy.setTranslateX(badguy.getTranslateX() + 10);
        }
    }

}
