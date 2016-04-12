package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.controller.ImageViewLoader;
import edu.uco.sdd.rocketdog.controller.RocketDogGame;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class LevelOne extends Level {

    Text t;
    ImageView bg;
    Group root;
    Group houses;
    public SoundManager soundManager = new SoundManager();

    public LevelOne(Group root, ImageView background, int width, int height, SoundManager soundManager) {
        super(root, background, width, height);
        t = new Text();
        this.soundManager = soundManager;
        soundManager.resetMediaPlayer(this.soundManager.getMp_bg(), "ambienthorror.mp3");
        soundManager.mp_bg.setVolume(0.05);
        soundManager.mp_bg.setCycleCount(100);
        soundManager.mp_am.setMute(true);
        this.root = root; // Need a handle to root to add images

        bg = (ImageView) root.getChildren().get(0); // Need a handle to bg to scroll

        addHouses();

        // Aid Items
        //Shield Items
        //addAidItem(new ShieldItem(new Point2D(200,200)),28,28);
        //addAidItem(new ShieldItem(new Point2D(600,200)),28,28);
        //addAidItem(new ShieldItem(new Point2D(400,200)),28,28);
        //health items
        addAidItem(new HealthItem(new Point2D(10, 100)), 56, 56);
        addAidItem(new ShieldItem(new Point2D(800, 200)), 56, 56);
        //addAidItem(new HealthItem(new Point2D(400, 10)), 56, 56);

        // Hazards
        //addHazard(new HazardSpikes(new Point2D(700,300)),64,64);
        //addHazard(new HazardSpikes(new Point2D(700,236)),64,64);
        //addHazard(new HazardSpikes(new Point2D(700,172)),64,64);
        //Obstructions
        //addObstruction(new ObstructionBrickWall(new Point2D(700,300)),64,64);
        //addObstruction(new ObstructionBrickWall(new Point2D(700,236)),64,64);
        //addObstruction(new ObstructionBrickWall(new Point2D(700,172)),64,64);
        addObstruction(new ObstructionBrickWall(new Point2D(200, 624)), 64, 64);
        //addObstruction(new ObstructionBrickWall(new Point2D(500,624)),64,64);

        //Surfaces
        /*Ice ice = new Ice(400, 50);
         ice.setPosition(new Point2D(150, 725));
         addSurface(ice);*/
        // Bad Guys
        EntityClass enemy = new EntityClass("Enemy");
        enemy.setRelationship(getPlayer(), EntityClass.Relationship.ENEMY);
        addEnemy(new Enemy.Builder("/Ugly Dog.png", 64, 64).setX(400).setY(700).setStart(350).setEnd(450).setRange(300).setEntityClass(enemy).setLevel(this).build(), 64, 64);
        addEnemy(new Enemy.Builder("/Ugly Dog.png", 32, 32).setX(300).setY(650).setStart(50).setEnd(650).setRange(300).setEntityClass(enemy).setLevel(this).build(), 32, 32);
        //addEnemy(new Enemy.Builder("/Ugly Dog.png", 64, 64).setX(400).setY(400).setEntityClass(enemy).setLevel(this).build(), 64, 64);
        //addEnemy(new Enemy.Builder("/Ugly Dog.png", 32, 32).setX(300).setY(350).setEntityClass(enemy).setLevel(this).build(), 32, 32);
        addEnemy(new DeliveryMan(500, 400), 400, 400);

        //done
        finishLevel();
    }

    /**
     *
     * Like this, zoneWidth[0] is first zone on the left zoneWidth[1] would be
     * the first vertical line, zoneWidth[2] would be second vertical line, etc.
     * +---+---+---+---+ +---|---|---|---| +---|---|---|---| +---+---+---+---+
     *
     */
    private void loadMusic() {
        SoundManager soundManager = new SoundManager();
        soundManager.setMp_bg(soundManager.createMediaPlayer("bgmusic.mp3"));
        soundManager.playMediaPlayer(soundManager.getMp_bg(), 0.1);
        soundManager.setMp_am(soundManager.createMediaPlayer("forest.mp3"));
        soundManager.playMediaPlayer(soundManager.getMp_am(), 0.1);
    }

    public void positionScreen() {
        RocketDog rd = this.getRocketDog();
        double width = RocketDogGame.GAME_SCREEN_WIDTH;
        double height = RocketDogGame.GAME_SCREEN_HEIGHT;

        double rdx = rd.getPosition().getX();
        double rdy = rd.getPosition().getY();

        /**
         * Divide the screen into 10 zones
         */
        int numZones = 10;
        double[] zoneWidth = new double[numZones];
        double[] zoneHeight = new double[numZones];
        for (int i = 0; i < numZones; i++) {
            zoneWidth[i] = i * (width / numZones);
            zoneHeight[i] = i * (height / numZones);
        }

        if (rdx > zoneWidth[8]) {
            bg.setTranslateX(bg.getTranslateX() - 5);
            houses.setTranslateX(houses.getTranslateX() - 5);
            rd.setPos(zoneWidth[8], rdy);
        }
        if (rdx > zoneWidth[4]) {
            bg.setTranslateX(bg.getTranslateX() - 1);
            houses.setTranslateX(houses.getTranslateX() - 1);
        }

        //RD goes left so BG goes right
        if (rdx < zoneWidth[0]) {
            bg.setTranslateX(bg.getTranslateX() + 1);
            houses.setTranslateX(houses.getTranslateX() + 1);
            rd.setPos(zoneWidth[0], rdy);
        }

        if (rdx < zoneWidth[1]) {
            bg.setTranslateX(bg.getTranslateX() + 1);
            houses.setTranslateX(houses.getTranslateX() + 1);
        }

        /* turning off vertical scrolling
         if (rdy > zoneHeight[8]) {
         bg.setTranslateY(bg.getTranslateY()-5);
         houses.setTranslateY(houses.getTranslateY()-5);
         rd.setPos(rdx,zoneHeight[8]);
         }

         // RD goes down so bg goes up
         if (rdy > zoneHeight[7]) {
         bg.setTranslateY(bg.getTranslateY()-1);
         houses.setTranslateY(houses.getTranslateY()-1);
         }

         // RD goes up so bg goes down
         if (rdy < zoneHeight[1]) {
         bg.setTranslateY(bg.getTranslateY()+1);
         houses.setTranslateY(houses.getTranslateY()+1);
         }
         if (rdy < zoneHeight[0]) {
         bg.setTranslateY(bg.getTranslateY()+5);
         houses.setTranslateY(houses.getTranslateY()+5);
         rd.setPos(rdx,zoneHeight[0]);
         } */
    }

    /**
     * We will not have a background image big enough so more background images
     * will have to be added. They need to be added under a Group object so that
     * they can all be moved at the same time. This would more accurately be be
     * called 'addBackground' or something of that nature. They also need to be
     * added before RocketDog so they are higher in the tree so Rocketdog is not
     * obscured.
     */
    public final void addHouses() {
        // Add random houses every 300 pixels or so
        houses = new Group(); // Create the Group
        ImageViewLoader ldr = ImageViewLoader.getInstance();
        for (int i = 0; i < 10; i++) {
            double x = Math.random() * 10 % 6 + 1; // Integer between 1 and 6
            ImageView sprite = ldr.loadImage("/Resources/houses/house" + (int) x + ".png");
            sprite.setTranslateX(i * 420 + 600);
            houses.getChildren().add(sprite);
        }
        houses.setScaleX(.7);
        houses.setScaleY(.7);
        houses.setTranslateY(360);
        root.getChildren().add(1, houses);
    }

    @Override
    public void levelUpdate() {
        super.levelUpdate();
        positionScreen();
        //houses.setTranslateX(houses.getTranslateX()-1);
    }
}
