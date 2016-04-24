/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Sophia
 */
public class CreditsDisplay {
    VBox creditsBox;
    StackPane creditsPane;
    private final Separator sepHor;
    public CreditsDisplay() {
        creditsPane = new StackPane();
        creditsBox= new VBox(); 
        sepHor = new Separator();

        creditsBox= createCreditsVbox();
        addStackPaneCredits(creditsBox);
        hideCredits();
    }

    public void hideCredits(){
        creditsPane.setVisible(false);
    }

    public VBox getCreditsBox() {
        return creditsBox;
    }

    public StackPane getCreditsPane() {
        return creditsPane;
    }

    public void showCredits(){
        creditsPane.setVisible(true);
    }

    public void addStackPaneCredits(VBox hb) {
        creditsPane.setAlignment(Pos.CENTER);
        creditsPane.setTranslateX(350);
        creditsPane.setTranslateY(200);
        creditsPane.getChildren().add(hb);
    }
    public VBox createCreditsVbox() {
        String myFont= "Comic Sans";
        Label creditsLabel = new Label("Credits");
        Label lbl1 = new Label("Richard Dobie");
        Label lbl2 = new Label("Spencer Harris");
        Label lbl3 = new Label("Sophia Msaaf");
        Label lbl4 = new Label("Dwayne Page");
        Label lbl5 = new Label("Noah Sefcik");
        Label lbl6 = new Label("Kody Strickland");
        sepHor.setValignment(VPos.CENTER);
        Label closeLabel= new Label("Hit Escape to go back to main menu");
        creditsLabel.setFont(Font.font(myFont, FontWeight.BOLD, 35));
        lbl1.setFont(Font.font(myFont, FontWeight.BOLD, 35));
        lbl2.setFont(Font.font(myFont, FontWeight.BOLD, 35));
        lbl3.setFont(Font.font(myFont, FontWeight.BOLD, 35));
        lbl4.setFont(Font.font(myFont, FontWeight.BOLD, 35));
        lbl5.setFont(Font.font(myFont, FontWeight.BOLD, 35));
        lbl6.setFont(Font.font(myFont, FontWeight.BOLD, 35));
        closeLabel.setFont(Font.font(myFont, FontWeight.BOLD, 15));
        creditsLabel.setTextFill(Color.WHITESMOKE);
        lbl1.setTextFill(Color.WHITESMOKE);
        lbl2.setTextFill(Color.WHITESMOKE);
        lbl3.setTextFill(Color.WHITESMOKE);
        lbl4.setTextFill(Color.WHITESMOKE);
        lbl5.setTextFill(Color.WHITESMOKE);
        lbl6.setTextFill(Color.WHITESMOKE);
        closeLabel.setTextFill(Color.WHITESMOKE);

        creditsBox.getChildren().addAll(creditsLabel, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6,sepHor,closeLabel);
        creditsBox.setAlignment(Pos.CENTER);
        creditsBox.setStyle("-fx-background-color: \n" +
"        #090a0c,\n" +
"        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
"        linear-gradient(#20262b, #191d22),\n" +
"        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
                + "-fx-padding: 10 20 10 20;");
        return creditsBox;
    }
}
