/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.view;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Sophia
 */
public class GameOver {
    ImageView imgv;

    VBox gameOverBox;
    StackPane gameOverPane;
    private final Separator sepHor;
    private final ImageView gameOverBackplate;
    Image gameOverScreenbg;
    int width; int height;
    public GameOver() {
        width=height=600;
        //Change this image later
        gameOverScreenbg = new Image("/splashscreenbg.png", width, height, true, true, true);
        gameOverBackplate = new ImageView();
        gameOverBackplate.setImage(gameOverScreenbg);
        gameOverPane = new StackPane();
        gameOverBox= new VBox();
        sepHor = new Separator();

        gameOverBox= createGameOverVbox();
        addStackPaneCredits(gameOverBox);
        hideGameOverPane();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public VBox getGameOverBox() {
        return gameOverBox;
    }

    public StackPane getGameOVerPane() {
        return gameOverPane;
    }

    public void hideGameOverPane(){
        gameOverPane.setVisible(false);
    }
    public void showGameOVerPane(){
        gameOverPane.setVisible(true);
    }

    public void addStackPaneCredits(VBox hb) {
        gameOverPane.setAlignment(Pos.CENTER);
        gameOverPane.setTranslateX(150);
        gameOverPane.setTranslateY(150);
        gameOverPane.getChildren().add(hb);
    }
    public VBox createGameOverVbox() {
        String myFont= "Comic Sans";
        Label gameOverLabel = new Label("GAME OVER");

        sepHor.setValignment(VPos.CENTER);
        Label closeLabel= new Label("Hit Escape to go back to main menu");
        gameOverLabel.setFont(Font.font(myFont, FontWeight.BOLD, 35));
        closeLabel.setFont(Font.font(myFont, FontWeight.BOLD, 15));
        gameOverLabel.setTextFill(Color.WHITESMOKE);
        closeLabel.setTextFill(Color.WHITESMOKE);

        gameOverBox.getChildren().addAll(gameOverLabel,sepHor,closeLabel,gameOverBackplate);

        gameOverBox.setAlignment(Pos.CENTER);
        gameOverBox.setStyle("-fx-background-color: \n" +
"        #090a0c,\n" +
"        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
"        linear-gradient(#20262b, #191d22),\n" +
"        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
                + "-fx-padding: 10 20 10 20;");
        return gameOverBox;
    }
}
