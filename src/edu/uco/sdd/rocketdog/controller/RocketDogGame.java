/*
 Copyright 2016
 Noah Sefcik, Richard Dobie, Spencer Harris, Sophia Msaaf
 Dwayne Page, Kody Strickland	

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 OTHER DEALINGS IN THE SOFTWARE.

 Background - Designed by Bilal-khan - Freepik.com
 */
package edu.uco.sdd.rocketdog.controller;

import edu.uco.sdd.rocketdog.model.ILevel;
import edu.uco.sdd.rocketdog.model.SceneFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.scenicview.ScenicView;

public class RocketDogGame extends Application {

    public static final int GAME_SCREEN_WIDTH = 1000;
    public static final int GAME_SCREEN_HEIGHT = 924;
    private GamePlayLoop gamePlayLoop;
    public Scene currentLevel;
    private ILevel updateableLevel;
    Stage currentStage;

    SceneFactory lf; // Level Factory will produce the level
    String[] levels; // Used to sequence the levels
    int levelIndex;  // Used to increment the current Level 

    @Override
    public void init() {
        gamePlayLoop = new GamePlayLoop(this);

        /**
         * This array is used to sequence the levels that will be requested from
         * LevelFactory
         */
        levels = new String[]{
            //"Test",
            //"Splash",
            //"One",
            "Two"
        };

        levelIndex = 0; // Used so levels can be incremented through
        lf = new SceneFactory(levels[levelIndex]); // Start the factory up
        currentLevel = lf.getLevel();
        updateableLevel = (ILevel) currentLevel; // Level must obey ILevel interface

    }

    /* This is the starting point for JavaFX applications */
    @Override
    public void start(Stage primaryStage) {
        /*  Stage and Scene must be constructed from within the start method */
        currentStage = primaryStage;
        primaryStage.setTitle("Rocket Dog!");
        primaryStage.setMaxWidth(GAME_SCREEN_WIDTH);
        primaryStage.setMaxHeight(GAME_SCREEN_HEIGHT);
        primaryStage.setScene(currentLevel);
        ScenicView.show(currentLevel);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
        gamePlayLoop.start();
    }

    public void update() {
        updateableLevel.levelUpdate();
        if (updateableLevel.isDone()) {
            lf.setLevel(levels[++levelIndex]);
            currentLevel = lf.getLevel();
            updateableLevel = (ILevel) currentLevel;
            currentStage.setScene(currentLevel);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
