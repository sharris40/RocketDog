package edu.uco.sdd.rocketdog.model;

import static edu.uco.sdd.rocketdog.controller.RocketDogGame.GAME_SCREEN_HEIGHT;
import static edu.uco.sdd.rocketdog.controller.RocketDogGame.GAME_SCREEN_WIDTH;
import java.util.Optional;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SplashLevel extends Scene implements ILevel {

    private  ImageView splashScreenTextArea,splashScreenBackplate;
    private  VBox buttonContainer;
    private  Insets buttonContainerPadding;
    private  Button startButton,instructionsButton,optionsButton,
            scoresButton,creditsButton, exitButton;
    private  Button optionsCloseButton,
            optionsDefaultButton,optionsWASDButton;
    private Image splashScreenbg, instructionsLayer, scoresLayer, optionsLayer, creditsLayer;
    private BorderPane root;
    HBox hboxCenter;
    GridPane grid;
    BorderPane bp;
    private boolean isDone;

    Scene splashScene;
    private Image logo,copyright;
    private ImageView imgView;
    int width,height;
    private Slider musicSlider,ambientSlider;
    private Separator sepHor;
    private Label ambianceVolumeLabel,musicLabel,soundLabel;
    public CheckBox ck;
    private String selectedFont="Comic Sans MS";
    private boolean musicIsEnabled=true;
    SoundManager soundManager;

    public SplashLevel(BorderPane root, SoundManager soundManager) {
        super(root,1000,924);
        isDone = false;
        this.root = root;
        this.soundManager= soundManager;
        HBox hboxTop= addHBox();
        HBox hboxBottom=addHBox();

        createOtherComponents();
        buttonContainer=createMenuVBox();
        loadImages();

        /**
         * *****************START APPLICATION******************
         */
        startButton.setOnAction((ActionEvent) -> {
            CutSceneStage cs = new CutSceneStage();
            String s = "/splash.mp4";
            cs.CutSceneStage("./src/splash.mp4").showAndWait();
            isDone=true;
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
            try{hboxCenter=createInstructHBox();}
            catch(Exception e){System.out.println("createInstructionBox");}
            root.setCenter(hboxCenter);
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
        });

        /**
         * *****************KEYBOARD KEY MAPPING******************
         */
        optionsButton.setOnAction((ActionEvent) -> {
            try{grid=createOptionsGridPane();}
            catch(Exception e){System.out.println("createOptionsGridPane");}
            root.setCenter(grid);
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            }
        );

        /**
         * *****************SCORES BOARD******************
         */
        scoresButton.setOnAction((ActionEvent) -> {
            try{hboxCenter=createScoresHBox();}
            catch(Exception e){System.out.println("createOptionsGridPane");}
            root.setCenter(hboxCenter);
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            }
        );
        /**
         * *****************SCORES BOARD******************
         */
        creditsButton.setOnAction((ActionEvent) -> {
            hboxCenter=createCreditsHBox();
            root.setCenter( hboxCenter);
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
        }
        );
        splashScreenBackplate.setImage(splashScreenbg);
        root.getChildren().add(splashScreenBackplate);
        root.getChildren().add(splashScreenTextArea);
        root.setTop(hboxTop);
        addStackPaneLogo(hboxTop);
        root.setLeft(buttonContainer);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        root.setBottom(hboxBottom);
        hboxBottom.setAlignment(Pos.CENTER);
        addStackPaneCopyRight(hboxBottom);
    }

    private void loadImages() {
           // load all needed images
           splashScreenbg = new Image("/splashscreenbg.png",GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT, true, true, true);
           instructionsLayer = new Image("/instruct.png", 800, GAME_SCREEN_HEIGHT, true, false, true);
           optionsLayer = new Image("/options.png", 800, GAME_SCREEN_HEIGHT, true, true, true);
           scoresLayer = new Image("/scores.png", 800, GAME_SCREEN_HEIGHT, true, true, true);
           creditsLayer= new Image("/credits.png", 800, GAME_SCREEN_HEIGHT, true, true, true);
           copyright= new Image("/copyr.png",GAME_SCREEN_WIDTH, 35, true, true, true);//allrightsreserved png
           logo=new Image("/logo_2.png",GAME_SCREEN_WIDTH, 95, true, true, true);
       }

    private void createOtherComponents(){
        splashScreenTextArea = new ImageView();
        splashScreenBackplate = new ImageView();
    }
    public VBox createCreditsVbox(){
        VBox creditsBox= new VBox();
        Label creditsLabel= new Label("Credits");
        Label lbl1= new Label("Richard Dobie");
        Label lbl2= new Label("Spencer Harris");
        Label lbl3= new Label("Sophia Msaaf");
        Label lbl4= new Label("Dwayne Page");
        Label lbl5= new Label("Noah Sefcik");
        Label lbl6= new Label("Kody Strickland");
        creditsBox.getChildren().addAll(creditsLabel,lbl1,lbl2,lbl3,lbl4,lbl5,lbl6);
        return creditsBox;
    }
    public void createOptionsButtons(){
        optionsCloseButton = new Button("Save");
        optionsDefaultButton = new Button("Default KeyMapping");
        optionsWASDButton = new Button("WASD KeyMapping");
        optionsDefaultButton.setDisable(true);
    }
    public void createMusicControls(){
        soundLabel = new Label("Sound");
        sepHor = new Separator();
        VBox vCheckBox= new VBox();
        ck= new CheckBox("Enable");
        ck.selectedProperty().setValue(musicIsEnabled);
        root.getChildren().add(vCheckBox);
        musicLabel = new Label("Music");
        ambianceVolumeLabel= new Label("Ambient");
        musicSlider = new Slider(0.0, 1.0, 1.0);
        ambientSlider = new Slider(0.0, 1.0, 1.0);
    }
    public GridPane createOptionsGridPane(){
        GridPane optionsPane=new GridPane();// grid holding options pane components
        //Creating a GridPane container
        optionsPane.setPadding(new Insets(10, 10, 10, 10));
        optionsPane.setVgap(5);
        optionsPane.setHgap(5);

        createOptionsButtons();
        createMusicControls();
        musicSlider.setValue(soundManager.mp_bg.getVolume());
        ambientSlider.setValue(soundManager.mp_am.getVolume());
        //column then rown
        GridPane.setConstraints(optionsDefaultButton, 0, 3);
        GridPane.setConstraints(optionsWASDButton, 1, 3);
        GridPane.setConstraints(optionsCloseButton, 2, 3);// u can always add this to anchorpane instead

        //Sound constraints
        GridPane.setConstraints(soundLabel, 0, 4);
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 5);
        GridPane.setColumnSpan(sepHor, 7);
        GridPane.setConstraints(ck,0,6);
        GridPane.setConstraints(musicLabel, 0, 7);
        GridPane.setConstraints(musicSlider, 1, 7);
        GridPane.setConstraints(ambianceVolumeLabel, 0, 8);
        GridPane.setConstraints(ambientSlider, 1, 8);
        optionsPane.setAlignment(Pos.TOP_CENTER);

        ck.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            System.out.println(ck.isSelected());
            if(ck.isSelected()){
                soundManager.muteBgSound(false);
                soundManager.mp_bg.setVolume(musicSlider.getValue());
                soundManager.muteAmSound(false);
                soundManager.mp_am.setVolume(ambientSlider.getValue());
                musicIsEnabled=true;
            }
            else{
                musicIsEnabled=false;
                soundManager.muteAmSound(true);
                soundManager.muteBgSound(true);
            }
        });
        musicSlider.valueProperty().addListener((Observable observable) -> {
            soundManager.mp_bg.setVolume(musicSlider.getValue());
        });
        ambientSlider.valueProperty().addListener((Observable observable) -> {
            soundManager.mp_am.setVolume(ambientSlider.getValue());
        });

        optionsDefaultButton.setOnAction((ActionEvent) -> {
            optionsDefaultButton.setDisable(true);
            optionsWASDButton.setDisable(false);
        });

        optionsWASDButton.setOnAction((ActionEvent) -> {
            optionsWASDButton.setDisable(true);
            optionsDefaultButton.setDisable(false);
        });

        optionsPane.getChildren().addAll(optionsDefaultButton,optionsWASDButton,
                soundLabel,ck,musicLabel,musicSlider,sepHor,ambianceVolumeLabel,ambientSlider);
        return optionsPane;
    }

    public void createMenuButtons(){
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
        creditsButton= new Button("Credits");
        creditsButton.setPrefSize(100, 20);
        creditsButton.setMaxWidth(Double.MAX_VALUE);
        exitButton= new Button("Exit");
        exitButton.setPrefSize(100, 20);
        exitButton.setMaxWidth(Double.MAX_VALUE);
    }

    public HBox createScoresHBox(){
        HBox scoreBox= new HBox();
        imgView= new ImageView(scoresLayer);
        scoreBox.getChildren().add(imgView);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setMargin(imgView, new Insets(0, 10, 0, 0)); // Center Pane
        return scoreBox;
        }

    public HBox createCreditsHBox(){
        HBox creditsBox= new HBox();
        imgView= new ImageView(creditsLayer);
        creditsBox.getChildren().add(imgView);
        creditsBox.setAlignment(Pos.CENTER);
        creditsBox.setMargin(imgView, new Insets(0, 10, 0, 0)); // Center Pane
        return creditsBox;
        }
    public HBox createInstructHBox(){
        HBox instructBox= new HBox();
        imgView= new ImageView(instructionsLayer);
        instructBox.getChildren().add(imgView);
        instructBox.setAlignment(Pos.CENTER);
        instructBox.setMargin(imgView, new Insets(0, 10, 0, 0)); // Center Pane
        return instructBox;
        }

    public VBox createMenuVBox()
    {
        VBox menuButtonVBox= new VBox();//box holding all buttons
        menuButtonVBox.setSpacing(10);
        buttonContainerPadding= new Insets(0, 10, 0, 10);
        menuButtonVBox.setPadding(buttonContainerPadding);
        menuButtonVBox.setAlignment(Pos.CENTER);
        createMenuButtons();
        Text title= new Text("Menu");
        title.setFont(Font.font(selectedFont, FontWeight.BOLD,26));
        menuButtonVBox.getChildren().add(title);

        menuButtonVBox.getChildren().addAll(startButton, instructionsButton, optionsButton,scoresButton,creditsButton,exitButton);
        return menuButtonVBox;
    }

    public void addStackPaneLogo(HBox hb){
        StackPane logoPane= new StackPane();
        imgView= new ImageView(logo);

        logoPane.getChildren().addAll(imgView);
        logoPane.setAlignment(Pos.CENTER_LEFT);     // left-justify logo node in stack
        StackPane.setMargin(imgView, new Insets(0, 10, 0, 0)); // Center Logo
        hb.getChildren().add(logoPane);
    }

    public void addStackPaneInstructions(HBox hb){
        StackPane instructionnsPane= new StackPane();
        imgView=new  ImageView(instructionsLayer);

        instructionnsPane.getChildren().add(imgView);
        StackPane.setAlignment(imgView, Pos.CENTER);

        StackPane.setMargin(imgView, new Insets(10, 10, 10, 10));
        hb.getChildren().add(instructionnsPane);

    }

    public void addStackPaneCredits(HBox hb){
         StackPane creditsPane= new StackPane();
        imgView=new  ImageView(creditsLayer);
        MediaView mv= new MediaView();// if we wanted to change it to video
        creditsPane.getChildren().add(imgView);
        StackPane.setAlignment(imgView, Pos.CENTER);

        StackPane.setMargin(imgView, new Insets(10, 10, 10, 10));
        hb.getChildren().add(creditsPane);

    }

    public void addStackPaneScores(HBox hb){
        StackPane scoresPane= new StackPane();
        imgView=new  ImageView(scoresLayer);

        scoresPane.getChildren().add(imgView);
        StackPane.setAlignment(imgView, Pos.CENTER);

        StackPane.setMargin(imgView, new Insets(10, 10, 10, 10));
        hb.getChildren().add(scoresPane);

    }

    public void addStackPaneExit(HBox hb){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");

        alert.setContentText("Are you sure you want to exit the game?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            System.exit(0);
        } else {
            alert.close();
        }
    }

    public HBox addHBox(){
        HBox hb= new HBox();
        hb.setPadding(new Insets(15,0,15,15));
        hb.setSpacing(0);
        hb.setMaxHeight(15);
        return hb;
    }

    public void addStackPaneCopyRight(HBox hb){
        StackPane copyrightPane= new StackPane();
        imgView= new ImageView(copyright);

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