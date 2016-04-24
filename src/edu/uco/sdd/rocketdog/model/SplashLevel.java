package edu.uco.sdd.rocketdog.model;

import static edu.uco.sdd.rocketdog.controller.RocketDogGame.GAME_SCREEN_HEIGHT;
import static edu.uco.sdd.rocketdog.controller.RocketDogGame.GAME_SCREEN_WIDTH;
import java.util.Optional;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SplashLevel extends Level {

    private ImageView splashScreenTextArea, splashScreenBackplate;
    private VBox buttonContainer;
    private Insets buttonContainerPadding;
    private Button startButton, instructionsButton, optionsButton,
            scoresButton, creditsButton, exitButton;
    private Button optionsCloseButton,
            optionsDefaultButton, optionsWASDButton;
    private Image splashScreenbg, instructionsLayer, scoresLayer, optionsLayer, creditsLayer;
    
    HBox hboxCenter;
    GridPane grid;
    BorderPane bp;
    private boolean isDone;
    Scene splashScene;
    private Image logo, copyright;
    private ImageView imgView;
    int width, height;
    private Slider musicSlider, ambientSlider;
    private Separator sepHor;
    private Label ambianceVolumeLabel, musicLabel, soundLabel;
    public CheckBox ck;
    private String selectedFont = "Comic Sans MS";
    private boolean musicIsEnabled = true;
    SoundManager soundManager;
    private GridPane optionsPane;

    public SplashLevel(Group root,SoundManager soundManager) {
        super(root, 1000, 924);
        isDone = false;
        this.bp= new BorderPane();
        this.soundManager= soundManager;
        soundManager.startGameMusicForLevel(0);
        HBox hboxTop = addHBox();
        HBox hboxBottom = addHBox();
        createOtherComponents();
        buttonContainer = createMenuVBox();
        loadImages();

        /**
         * *****************START APPLICATION******************
         */
        startButton.setOnAction((ActionEvent) -> {
            CutSceneStage cs = new CutSceneStage();
            String s = "/splash.mp4";
            cs.CutSceneStage("./src/splash.mp4").showAndWait();
            isDone = true;
        });

        /**
         * *****************EXIT APPLICATION******************
         */
        exitButton.setOnAction((ActionEvent) -> {
            System.out.println("Quitting!");
            addStackPaneExit(hboxCenter);
        });

        /**
         * *****************GAME INSTRUCTIONS******************
         */
        instructionsButton.setOnAction((ActionEvent) -> {
            try {
                hboxCenter = createInstructHBox();
            } catch (Exception e) {
                System.out.println("createInstructionBox");
            }
            bp.setCenter(hboxCenter);
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
        });

        /**
         * *****************KEYBOARD KEY MAPPING******************
         */
        optionsButton.setOnAction((ActionEvent) -> {
            try {
                hboxCenter = createOptionsHBox();
            } catch (Exception e) {
                e.printStackTrace();
            }
            bp.setCenter(hboxCenter);
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
        }
        );

        /**
         * *****************SCORES BOARD******************
         */
        scoresButton.setOnAction((ActionEvent) -> {
            try {
                hboxCenter = createScoresHBox();
            } catch (Exception e) {
            }
            bp.setCenter(hboxCenter);
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
        }
        );
        /**
         * *****************SCORES BOARD******************
         */
        creditsButton.setOnAction((ActionEvent) -> {
            hboxCenter = createCreditsHBox();
            bp.setCenter(hboxCenter);
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
        }
        );
        splashScreenBackplate.setImage(splashScreenbg);
        bp.getChildren().add(splashScreenBackplate);
        bp.getChildren().add(splashScreenTextArea);
        bp.setTop(hboxTop);
        addStackPaneLogo(hboxTop);
        bp.setLeft(buttonContainer);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        bp.setBottom(hboxBottom);
        hboxBottom.setAlignment(Pos.CENTER);
        addStackPaneCopyRight(hboxBottom);
        root.getChildren().add(bp);
    }

    private void loadImages() {
        // load all needed images
        splashScreenbg = new Image("/splashscreenbg.png", GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT, true, true, true);
        instructionsLayer = new Image("/instruct.png", 800, GAME_SCREEN_HEIGHT, true, false, true);
        optionsLayer = new Image("/options.png", 800, GAME_SCREEN_HEIGHT, true, true, true);
        scoresLayer = new Image("/scores.png", 800, GAME_SCREEN_HEIGHT, true, true, true);
        creditsLayer = new Image("/credits.png", 800, GAME_SCREEN_HEIGHT, true, true, true);
        copyright = new Image("/copyr.png", GAME_SCREEN_WIDTH, 35, true, true, true);//allrightsreserved png
        logo = new Image("/logo_2.png", GAME_SCREEN_WIDTH, 95, true, true, true);
    }

    private void createOtherComponents() {
        splashScreenTextArea = new ImageView();
        splashScreenBackplate = new ImageView();
    }

    

    public void createOptionsButtons() {
        optionsCloseButton = new Button("Save");
        optionsDefaultButton = new Button("Default KeyMapping");
        optionsWASDButton = new Button("WASD KeyMapping");
        optionsDefaultButton.setDisable(true);
    }
    
    public void createMusicControls() {
        soundLabel = new Label("Sound");
        sepHor = new Separator();
        ck = new CheckBox("Enable");
        ck.selectedProperty().setValue(musicIsEnabled);
        musicLabel = new Label("Music");
        ambianceVolumeLabel = new Label("Ambient");
        musicSlider = new Slider(0.0, 1.0, 1.0);
        ambientSlider = new Slider(0.0, 1.0, 1.0);
        System.out.println("music controls created");
        musicSlider.setValue(soundManager.bgMusicPlayer.getCurrentVolume());
        ambientSlider.setValue(soundManager.amMusicPlayer.getCurrentVolume());
    }

    public GridPane createOptionsGridPane() {
        //Creating a GridPane container
        optionsPane = new GridPane();// grid holding options pane components
        optionsPane.setPadding(new Insets(10, 10, 10, 10));
        optionsPane.setVgap(5);
        optionsPane.setHgap(5);

        createOptionsButtons();
        createMusicControls();
        
        //column then rown
        GridPane.setConstraints(optionsDefaultButton, 0, 3);
        GridPane.setConstraints(optionsWASDButton, 1, 3);
        GridPane.setConstraints(optionsCloseButton, 2, 3);// u can always add this to anchorpane instead

        //Sound constraints
        GridPane.setConstraints(soundLabel, 0, 4);
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 5);
        GridPane.setColumnSpan(sepHor, 7);
        GridPane.setConstraints(ck, 0, 6);
        GridPane.setConstraints(musicLabel, 0, 7);
        GridPane.setConstraints(musicSlider, 1, 7);
        GridPane.setConstraints(ambianceVolumeLabel, 0, 8);
        GridPane.setConstraints(ambientSlider, 1, 8);
        optionsPane.setAlignment(Pos.TOP_CENTER);

        ck.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            System.out.println(ck.isSelected());
            if (ck.isSelected()) {
                soundManager.bgMusicPlayer.setCurrentVolume(musicSlider.getValue());
                soundManager.amMusicPlayer.setCurrentVolume(ambientSlider.getValue());
                soundManager.bgMusicPlayer.unmutePlayer();
                soundManager.amMusicPlayer.unmutePlayer();
                musicIsEnabled = true;
                soundManager.setMusicEnabled(musicIsEnabled);
            } else {
                musicIsEnabled = false;
                soundManager.setMusicEnabled(musicIsEnabled);
                soundManager.bgMusicPlayer.mutePlayer();
                soundManager.amMusicPlayer.mutePlayer();
            }
        });
        musicSlider.valueProperty().addListener((Observable observable) -> {
            soundManager.bgMusicPlayer.setCurrentVolume(musicSlider.getValue());
            soundManager.bgMusicPlayer.setVolumePlayer(musicSlider.getValue());
        });
        ambientSlider.valueProperty().addListener((Observable observable) -> {
            soundManager.amMusicPlayer.setVolumePlayer(ambientSlider.getValue());
            soundManager.amMusicPlayer.setCurrentVolume(ambientSlider.getValue());
        });

        optionsDefaultButton.setOnAction((ActionEvent) -> {
            optionsDefaultButton.setDisable(true);
            optionsWASDButton.setDisable(false);
        });

        optionsWASDButton.setOnAction((ActionEvent) -> {
            optionsWASDButton.setDisable(true);
            optionsDefaultButton.setDisable(false);
        });

        optionsPane.getChildren().addAll(optionsDefaultButton, optionsWASDButton,
                soundLabel, ck, musicLabel, musicSlider, sepHor, ambianceVolumeLabel, ambientSlider);
        return optionsPane;
    }


    public CheckBox getCk() {
        return ck;
    }

    public void createMenuButtons() {
        startButton = new Button("Play");
        startButton.setPrefSize(180, 20);
        startButton.setMaxWidth(Double.MAX_VALUE);
        instructionsButton = new Button("Instructions");
        instructionsButton.setPrefSize(100, 20);
        instructionsButton.setMaxWidth(Double.MAX_VALUE);
        optionsButton = new Button("Options");
        optionsButton.setPrefSize(100, 20);
        optionsButton.setMaxWidth(Double.MAX_VALUE);
        scoresButton = new Button("Scores");
        scoresButton.setPrefSize(100, 20);
        scoresButton.setMaxWidth(Double.MAX_VALUE);
        creditsButton = new Button("Credits");
        creditsButton.setPrefSize(100, 20);
        creditsButton.setMaxWidth(Double.MAX_VALUE);
        exitButton = new Button("Exit");
        exitButton.setPrefSize(100, 20);
        exitButton.setMaxWidth(Double.MAX_VALUE);
    }

    public HBox createScoresHBox() {
        HBox scoreBox= new HBox();
        imgView = new ImageView(scoresLayer);
        scoreBox.getChildren().add(imgView);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setMargin(imgView, new Insets(0, 10, 0, 0)); // Center Pane
        return scoreBox;
    }

    public HBox createCreditsHBox() {
        HBox creditsBox = new HBox();
        imgView = new ImageView(creditsLayer);
        creditsBox.getChildren().add(imgView);
        creditsBox.setAlignment(Pos.CENTER);
        creditsBox.setMargin(imgView, new Insets(0, 10, 0, 0)); // Center Pane
        return creditsBox;
    }

    public HBox createInstructHBox() {
        HBox instructBox = new HBox();
        imgView = new ImageView(instructionsLayer);
        instructBox.getChildren().add(imgView);
        instructBox.setAlignment(Pos.CENTER);
        instructBox.setMargin(imgView, new Insets(0, 10, 0, 0)); // Center Pane
        return instructBox;
    }

    public HBox createOptionsHBox() {
        HBox optionsBox = new HBox();
        grid = createOptionsGridPane();
        optionsBox.getChildren().add(grid);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setMargin(grid, new Insets(0, 10, 0, 0)); // Center Pane
        return optionsBox;
    }

    public VBox createMenuVBox() {
        VBox menuButtonVBox = new VBox();//box holding all buttons
        menuButtonVBox.setSpacing(10);
        buttonContainerPadding = new Insets(0, 10, 0, 10);
        menuButtonVBox.setPadding(buttonContainerPadding);
        menuButtonVBox.setAlignment(Pos.CENTER);
        createMenuButtons();
        Text title = new Text("Menu");
        title.setFont(Font.font(selectedFont, FontWeight.BOLD, 26));
        menuButtonVBox.getChildren().add(title);

        menuButtonVBox.getChildren().addAll(startButton, instructionsButton, optionsButton, scoresButton, creditsButton, exitButton);
        return menuButtonVBox;
    }

    public void addStackPaneLogo(HBox hb) {
        StackPane logoPane = new StackPane();
        imgView = new ImageView(logo);

        logoPane.getChildren().addAll(imgView);
        logoPane.setAlignment(Pos.CENTER_LEFT);     // left-justify logo node in stack
        StackPane.setMargin(imgView, new Insets(0, 10, 0, 0)); // Center Logo
        hb.getChildren().add(logoPane);
    }

    public void addStackPaneInstructions(HBox hb) {
        StackPane instructionnsPane = new StackPane();
        imgView = new ImageView(instructionsLayer);

        instructionnsPane.getChildren().add(imgView);
        StackPane.setAlignment(imgView, Pos.CENTER);

        StackPane.setMargin(imgView, new Insets(10, 10, 10, 10));
        hb.getChildren().add(instructionnsPane);

    }

    public void addStackPaneCredits(HBox hb) {
        StackPane creditsPane = new StackPane();
        imgView = new ImageView(creditsLayer);
        MediaView mv = new MediaView();// if we wanted to change it to video
        creditsPane.getChildren().add(imgView);
        StackPane.setAlignment(imgView, Pos.CENTER);

        StackPane.setMargin(imgView, new Insets(10, 10, 10, 10));
        hb.getChildren().add(creditsPane);

    }

    public void addStackPaneScores(HBox hb) {
        StackPane scoresPane = new StackPane();
        imgView = new ImageView(scoresLayer);

        scoresPane.getChildren().add(imgView);
        StackPane.setAlignment(imgView, Pos.CENTER);

        StackPane.setMargin(imgView, new Insets(10, 10, 10, 10));
        hb.getChildren().add(scoresPane);

    }

    public void addStackPaneExit(HBox hb) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");

        alert.setContentText("Are you sure you want to exit the game?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            System.exit(0);
        } else {
            alert.close();
        }
    }

    public HBox addHBox() {
        HBox hb = new HBox();
        hb.setPadding(new Insets(15, 0, 15, 15));
        hb.setSpacing(0);
        hb.setMaxHeight(15);
        return hb;
    }

    public void addStackPaneCopyRight(HBox hb) {
        StackPane copyrightPane = new StackPane();
        imgView = new ImageView(copyright);

        copyrightPane.getChildren().addAll(imgView);
        copyrightPane.setAlignment(Pos.CENTER);     // center copyright node in stack
        hb.getChildren().add(copyrightPane);

    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void levelUpdate() {

    }
}
