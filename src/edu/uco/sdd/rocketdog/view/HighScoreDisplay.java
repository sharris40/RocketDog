/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.view;

import edu.uco.sdd.rocketdog.model.HighScore;
import edu.uco.sdd.rocketdog.model.ScoreInformation;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Sophia
 */
public class HighScoreDisplay {

    ImageView imgView;
    StackPane scoresPane;
    GridPane grid;
    HighScore hs;
    ArrayList<ScoreInformation> highscoreRecords;
    Label L;
    Text l;
    private HBox scoreBox;
    private Button backButton;

    public HighScoreDisplay() {
        backButton= new Button("Back");
        scoreBox = new HBox();
        imgView = new ImageView();
        scoresPane = new StackPane();
        grid = new GridPane();
        hs = new HighScore();
        highscoreRecords = hs.getAllRecords();

        scoreBox = createScoresHBox();
        scoreBox.resize(500, 500);
        addStackPaneScores(scoreBox);
    }

    public GridPane getGrid() {
        return grid;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(Button backButton) {
        this.backButton = backButton;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    public ArrayList<ScoreInformation> getHighscoreRecords() {
        return highscoreRecords;
    }

    public void setHighscoreRecords(ArrayList<ScoreInformation> highscoreRecords) {
        this.highscoreRecords = highscoreRecords;
    }

    public HighScore getHs() {
        return hs;
    }

    public void setHs(HighScore hs) {
        this.hs = hs;
    }

    public StackPane getScoresPane() {
        scoresPane.setAlignment(Pos.CENTER);
        return scoresPane;
    }

    public void setScoresPane(StackPane scoresPane) {
        this.scoresPane = scoresPane;
    }
    
    public HBox createScoresHBox() {
        scoreBox.getChildren().add(imgView);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setMargin(imgView, new Insets(0, 10, 0, 0)); // Center Pane
        scoreBox.setStyle("-fx-background-color: \n" +
"        #090a0c,\n" +
"        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
"        linear-gradient(#20262b, #191d22),\n" +
"        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
                + "-fx-padding: 10 20 10 20;");
        return scoreBox;
    }

    public void showScoresPane(boolean paneState) {
        scoresPane.setVisible(paneState);
    }

    public void addStackPaneScores(HBox hb) {
        gridScores();
        scoresPane.setTranslateX(350);
        scoresPane.setTranslateY(200);
        scoresPane.getChildren().add(scoreBox);
    }

    public void gridScores() {
        Label titleLabel = new Label("HIGH SCORES");
//        ArrayList<ScoreInformation> contestWinners = new ArrayList<>(highscoreRecords.subList(numberRecords - 10, numberRecords));
        // Title in column 2, row 1
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.WHITESMOKE);
        grid.add(titleLabel, 1, 0);
        grid.setAlignment(Pos.CENTER);
        grid.setGridLinesVisible(false);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        
//        if (highscoreRecords.size() > 10) {

            for (int j = 0; j < highscoreRecords.size(); j++) {

                Label l1 = new Label();
                Label l2 = new Label();
                l1.setText(highscoreRecords.get(j).getName());
                l2.setText(String.valueOf(highscoreRecords.get(j).getScore()));
                l2.setTextFill(Color.WHITE);
                l1.setTextFill(Color.WHITE);
                l1.toFront();
                l2.toFront();
                l1.setFont(Font.font(20));
                l2.setFont(Font.font(20));
                grid.add(l1, 0, j+1);
                grid.add(l2, 1, j+1);

            }
            grid.add(backButton, 1,highscoreRecords.size()+1);

//        } else {
//            //todo
//        for (int i = 0; i < highscoreRecords.size(); i++) {
//            l1.setText(highscoreRecords.get(highscoreRecords.size() - i - 1).name);
//            l2.setText(String.valueOf(highscoreRecords.get(highscoreRecords.size() - i - 1).score));
//            System.out.println(l1.getText());
//            l1.setFont(Font.font(10));
//            l2.setFont(Font.font(10));
////            grid.add(l1, i, 0);
////            grid.add(l2, i, 1);
//        }
//        }
        grid.setVisible(true);
        scoreBox.getChildren().add(grid);
    }
}
