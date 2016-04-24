/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.view.CreditsDisplay;
import edu.uco.sdd.rocketdog.view.HighScoreDisplay;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Sophia
 */
public class MenuItem extends StackPane{
    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);
    private  VBox buttonContainer, menuButtonVBox;
    private  Insets buttonContainerPadding;
    private  Button startButton,instructionsButton,optionsButton,
            scoresButton,creditsButton, exitButton;
    private  Button optionsCloseButton,
            optionsDefaultButton,optionsWASDButton;
    private Text text;
    private Runnable script;
    private StackPane menupane= new StackPane();
    private Button keybindingsButton;
    HighScoreDisplay hsd;
    CreditsDisplay cd;
    public MenuItem() {
        VBox v= new VBox();
        v= createMenuVBox();
        this.hsd = new HighScoreDisplay();
        this.cd= new CreditsDisplay();
        menupane.getChildren().add(v);
    }
    public StackPane menuPane(){
        menupane.setAlignment(Pos.CENTER);
        return menupane;
    }

    public StackPane getMenupane() {
        return menupane;
    }
    
    public HighScoreDisplay getHsd() {
        return hsd;
    }
    
    private Button createMenuButton(String name) {
        Button button = new Button(name);
        button.setEffect(new GaussianBlur(2));
        button.setPrefSize(250, 20);
        button.setMaxWidth(Double.MAX_VALUE); 
        setActive(false);
        return button;
    }

    public Button getStartButton() {
        return startButton;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }
    public void displayMenu(boolean state){
        menuPane().setVisible(state);
    }
    public void displayHsd(boolean state){
        this.getHsd().getScoresPane().setVisible(state);
    }

//creates all menu buttons
    public void createMenuButtons(){
            startButton = createMenuButton("Back");
            instructionsButton = createMenuButton("Instructions");
            optionsButton = createMenuButton("Options");
            keybindingsButton = createMenuButton("KeyBindings");
            scoresButton = createMenuButton("Scores");
            creditsButton= createMenuButton("Credits");
            exitButton= createMenuButton("Exit");

        /**
         * *****************EXIT APPLICATION******************
         */

        exitButton.setOnAction((ActionEvent) -> {
            System.out.println("Quitting!");
            System.exit(0);
        });

        /**
         * *****************GAME INSTRUCTIONS******************
         */
        instructionsButton.setOnAction((ActionEvent) -> {
            System.out.println("instruct clicked");
        });

        /**
         * *****************KEYBOARD KEY MAPPING******************
         */
        optionsButton.setOnAction((ActionEvent) -> {
            System.out.println("options clicked");
            }
        );

        /**
         * *****************SCORES BOARD******************
         */
        scoresButton.setOnAction((ActionEvent) -> {
            System.out.println("view high scores button clicked");
            this.menuPane().setVisible(false);
            this.getHsd().getScoresPane().toFront();
            this.getHsd().getScoresPane().setVisible(true);
        });
        /**
         * *****************SCORES BOARD******************
         */
        creditsButton.setOnAction((ActionEvent) -> {
            System.out.println("credits clicked");
            this.menupane.setVisible(false);
            this.getCd().showCredits();
            
        }
        );
        startButton.setOnAction((ActionEvent) -> {
            System.out.println("back button clicked");

            this.menuPane().setVisible(false);
            this.displayHsd(false);
        }
        );
    }

    public CreditsDisplay getCd() {
        return cd;
    }

    public void setCd(CreditsDisplay cd) {
        this.cd = cd;
    }

    //Vbox contains menu buttons
    public VBox createMenuVBox(){
        createMenuButtons();
        menuButtonVBox= new VBox(6);//box holding all buttons
        menuButtonVBox.setSpacing(10);
        buttonContainerPadding= new Insets(0, 10, 0, 10);
        menuButtonVBox.setPadding(buttonContainerPadding);
        menuButtonVBox.setStyle("-fx-background-color: #336699;");
        menuButtonVBox.setTranslateX(350);
        menuButtonVBox.setTranslateY(200);
        menuButtonVBox.setAlignment(Pos.CENTER);
        menuButtonVBox.getChildren().addAll(startButton, instructionsButton,keybindingsButton, optionsButton,scoresButton,creditsButton,exitButton);
        return menuButtonVBox;
    }

    public VBox getMenuButtonVBox() {
        return menuButtonVBox;
    }

    public void setMenuButtonVBox(VBox menuButtonVBox) {
        this.menuButtonVBox = menuButtonVBox;
    }

    public Button getScoresButton() {
        return scoresButton;
    }

    public void setScoresButton(Button scoresButton) {
        this.scoresButton = scoresButton;
    }


    public void setOnActivate(){

    }
    public void setActive(boolean b) {
        setEffect(shadow);
    }

        DropShadow shadow = new DropShadow();
    }
