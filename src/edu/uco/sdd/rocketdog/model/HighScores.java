/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import edu.uco.sdd.rocketdog.controller.RocketDogGame;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Sophia
 */
public class HighScores {

    StackPane scoresPane;
    String saveDataPath;
    private int currentSize;
    private String names[];
    private int scores[];
    //public ArrayList topTenScores= new ArrayList();
    //File file;
    int highScore;
    String fileName = "HighScores";
    int numLines;
    String[] lineParts;
    FileWriter fileWriter;
    FileReader fileReader;
    HBox hb = new HBox();
    ArrayList<String> pnames;
    ArrayList<Integer> pscores;

    public HighScores() {
        scoresPane = new StackPane();
        pnames = new ArrayList<>();
        pnames.add("Player A");
        pnames.add("Player B");
        pnames.add("Player C");
        pscores = new ArrayList<>();
        pscores.add(1000);
        pscores.add(2000);
        pscores.add(5000);
        try {
            String saveDataPath2 = RocketDogGame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            //System.out.println("SAVE DATA PATH"+saveDataPath);
            loadHighScore();
        } catch (URISyntaxException ex) {
            Logger.getLogger(HighScores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        names = new String[100];
        scores = new int[100];
        currentSize = scores.length;
    }

    public int countLines() {
        //if exists
        //open the file
        return numLines;
    }

    public String getName(int i) {
        return names[i];
    }

    public int getScore(int i) {
        return scores[i];
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    private int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        this.highScore = score;
    }

    public void setHighScore() {
        File file = new File(fileName);

        FileWriter f = null;
        try {
            f = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(f);
            bw.write(highScore);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HBox createScoresHBox() {
        HBox scoreBox = new HBox();
        ImageView imgView = new ImageView();
        scoreBox.getChildren().add(imgView);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setMargin(imgView, new Insets(0, 10, 0, 0)); // Center Pane
        return scoreBox;
    }

    public void showScoresPane(boolean paneState) {
        scoresPane.setVisible(paneState);
    }

    public void addStackPaneScores(HBox hb) {
        gridScores();

        hb.getChildren().add(scoresPane);
    }

    public void gridScores() {
        Label titleLabel = new Label("HIGH SCORES");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setGridLinesVisible(true);
        //have to fix the loop
        for (int i = 0; i < 10; i++) {
            if(i < pnames.size()) {
            grid.add(new Label(pnames.get(pnames.size()-i-1)), i, 0);
            grid.add(new Label(String.valueOf(pscores.get(pscores.size()-i-1))), i, 1);
            }
        }
        scoresPane.getChildren().add(grid);
    }

    static int Sortpscores(ArrayList<Integer> list) {
        int count = 0;

        for (int outer = 0; outer < list.size() - 1; outer++) {

            for (int inner = 0; inner < list.size() - outer - 1; inner++) {

                if (list.get(inner) > list.get(inner + 1)) {
                    swapEm(list, inner);
                    count = count + 1;
                }
            }
        }
        return count;

    }

    static void swapEm(ArrayList<Integer> list, int inner) {
        int temp = list.get(inner);
        list.set(inner, list.get(inner + 1));
        list.set(inner + 1, temp);
    }

    public void addHighScore(int score) {
        String name = "";
        pscores.add(score);
        pnames.add("New Name");
        try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
                bw.append(score + "-" + "New Name" + System.lineSeparator());
                System.out.println("Done");
                bw.close();
        }catch(Exception e){
        }

        if (score > pscores.get(pscores.size()-1)) {
            //pscores.add(score);
            name = JOptionPane.showInputDialog("New high score!  Enter your name:");
            if (name != null) {
                pnames.add(name);
            } else {
                name="Unkown";
                pnames.add(name);
            }
            Sortpscores(pscores);
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
                bw.append(score + "-" + "New Name" + System.lineSeparator());
                System.out.println("Done");
                bw.close();
        }catch(Exception e){
        }
        }

    }

    public void loadHighScore() {
        String line = null;

        try {
            //File file = new File(fileName);
            File file = new File(saveDataPath, fileName);
            if (!file.exists()) {
                //if file does not exist create it
                createHighScoreFile();
            } else {
                fileReader = new FileReader(file);
                try (BufferedReader bufferReader = new BufferedReader(fileReader)) {
                    int i = 0;
                    while ((line = bufferReader.readLine()) != null) {
                        i++;
                        lineParts = line.split("-", 2);
                        pscores.add(Integer.parseInt(lineParts[0]));
                        pnames.add(lineParts[1]);
//                        scores[i] = Integer.parseInt(lineParts[0]);
//                        names[i] = lineParts[1];
                    }
                    try {
                        bufferReader.close();
                    } catch (Exception e) {
                        System.out.println("Could not close Buffer Reader for HighScore text File");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void DisplayScores(ArrayList<Integer>list)
      {
            int count = 1;
            for (int i = 0; i < 100; i++)
            {
                  System.out.print(list.get(i) +" ");
                  if (count % 10 == 0)
                  {
                        System.out.println();
                  }
                  count = count + 1;

            }

      }
    public int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    System.lineSeparator();
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return numLines = (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public void DisplayHighScores() {
        for (int i = 0; i < scores.length; i++) {
            System.out.println(scores[i]);
        }

    }

    /*
    * Sort Scores
    * Bubble Sort algorithm
    * worst case (O n2)
     */
    public void sortScores() {
        boolean swapped = true;
        int j = 0;
        String tmpName;
        int tmpScore;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < scores.length - j; i++) {
                if (scores[i] > scores[i + 1]) {
                    tmpName = names[i];
                    tmpScore = scores[i];
                    names[i] = names[i + 1];
                    scores[i] = scores[i + 1];
                    names[i + 1] = tmpName;
                    scores[i + 1] = tmpScore;
                    swapped = true;
                }
            }
        }
    }

    /**
     * create high score file and input score informations
     */
    public void createHighScoreFile() {

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            try {
                // create new file at path location
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for (int i=0;i<pscores.size();i++) {
                    //" - " + names[i] + " - " + scores[i] +;
                    bufferedWriter.write(pscores.get(i).toString() + "-" +pnames.get(i)+System.lineSeparator());

                }
                bufferedWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return Arrays.toString(Arrays.copyOf(scores, scores.length));
        //return  score[i]+"-"+name[i];
    }
}