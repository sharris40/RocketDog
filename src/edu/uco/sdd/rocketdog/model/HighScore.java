/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Sophia
 */
public class HighScore {

    private int MAX = 10;
    private ArrayList<ScoreInformation> scoresList;
    private String fileName = "HighScore.txt";
    private String saveDataPath;
    private File file;
    private FileWriter fileWriter;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;
    BufferedInputStream bis = null;
    int currentSize;
    int minScore = 0, maxScore = 0;
    private Object scoresPane;
    String[] lineParts;
//    HighScoreDisplay hsd;
//    GridPane grid;

    public HighScore() {
//        try {
        scoresList = new ArrayList<>();
        //adding records to our file and arraylist
//            ScoreInformation s1 = new ScoreInformation("lilian", 100);
//            ScoreInformation s2 = new ScoreInformation("kkk", 500);
//            ScoreInformation s3 = new ScoreInformation("appp", 700);
//            addNewScore(s1);
//            addNewScore(s2);
//            addNewScore(s3);
        file = new File(fileName);
        if (!file.exists()) {
            try {
                createNewHighScoreFile();
            } catch (IOException ex) {
                Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        scoresList = getAllRecords();
//        } catch (IOException ex) {
//            Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public int getCurrentSize(ArrayList<ScoreInformation> scoresList) {
        currentSize = scoresList.size();
        return currentSize;
    }

    public HighScore(ArrayList<ScoreInformation> scoresList) {
        scoresList = new ArrayList<>();
        for (int i = 0; i < scoresList.size(); i++) {
            scoresList.add(scoresList.get(i));
        }
        this.sortRecord();

    }

    private void loadRecordsFromFile() {
        try {
            readHighScores();
        } catch (FileNotFoundException e) {
            System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Laad] CNF Error: " + e.getMessage());
        }
    }

    public void readHighScores() throws ClassNotFoundException, IOException {
        System.out.println("READ HIGHSCORES CALLED");
        String line = null;
        file = new File(fileName);
        fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
        int i = 0;
        scoresList.clear();
        while ((line = bufferedReader.readLine()) != null) {
            i++;
            lineParts = line.split("-", 2);
            int score = Integer.parseInt(lineParts[1]);
            String name = lineParts[0];
            ScoreInformation s = new ScoreInformation(name, score);
            scoresList.add(s);
        }
        try {
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Could not close Buffer Reader for HighScore text File");
        }
        // Read objects one by one.
        scoresList.stream().forEach((s) -> {
            System.out.println(s);
        });
        currentSize = scoresList.size();
        if (!scoresList.isEmpty()) {
            minScore = scoresList.get(0).getScore();
            maxScore = scoresList.get(scoresList.size()-1).getScore();
        }
    }

    public ArrayList<ScoreInformation> getAllRecords() {
        loadRecordsFromFile();
        sortRecord();
        System.out.println("CurrentSize="+currentSize+" | MinScore= "+minScore+" | MaxScore= "+maxScore);
        return scoresList;
    }

    private void sortRecord() {
        ScoreInfoComparator c = new ScoreInfoComparator();
        Collections.sort(scoresList, c);
    }

    public void writeHighScores() throws ClassNotFoundException, IOException {

        fileWriter = new FileWriter(fileName);
        bufferedWriter = new BufferedWriter(fileWriter);
        for (ScoreInformation s : scoresList) {
            bufferedWriter.write(s.getName() + "-" + s.getScore() + "\n");
            System.out.println(s.getName() + "-" + s.getScore() + "\n");
        }
//        for (int i=0;i<scoresList.size();i++) {
//            bufferedWriter.write(scoresList.get(i).getName() + "-" +scoresList.get(i).getScore()+"\n");
//        }
        bufferedWriter.close();
    }

    public void writeHighScores(ScoreInformation newInfo) throws ClassNotFoundException, IOException {
        fileWriter = new FileWriter(fileName, true);
        bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.append(newInfo.getName() + "-" + newInfo.getScore() + "\n");
        System.out.println("Done");
        bufferedWriter.close();
    }

    public void addNewScore(ScoreInformation newInfo) throws ClassNotFoundException, IOException {
        loadRecordsFromFile();
        scoresList.add(newInfo);
        sortRecord();
        this.writeHighScores(newInfo);
        /// update current size
        currentSize++;
    }

    public void addNewScore(String n, int s) {
        ScoreInformation si = new ScoreInformation(n, s);
        loadRecordsFromFile();
        scoresList.add(si);
        sortRecord();
        try {
            writeHighScores(si);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addNewScore(int score) {
        String name = "";

        if (scoresList.size() > 0 && score > scoresList.get(scoresList.size() - 1).getScore()) {
            name = JOptionPane.showInputDialog("New high score!  Enter your name:");

            if (name.isEmpty()) {
                name = "Unkown";
            }
            ScoreInformation si = new ScoreInformation(name, score);
            scoresList.add(si);
            sortRecord();
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
                bw.append(name + "-" + score + "\n");
                System.out.println("Done");
                bw.close();
            } catch (Exception e) {
            }
        }
    }

    public void updateScoreFile() {
        try {
            file = new File(saveDataPath, fileName);
            try {
                writeHighScores();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public String getHighscoreString() {
        String highscoreString = "";
        ArrayList<ScoreInformation> scores;
        scores = getAllRecords();

        int i = 0;
        int x = scores.size();
        if (x > MAX) {
            x = MAX;
        }
        while (i < x) {
            highscoreString += (i + 1) + ".\t" + scores.get(i).getName() + "-" + scores.get(i).getScore() + System.lineSeparator();
            i++;
        }
        return highscoreString;
    }

    private void createNewHighScoreFile() throws IOException {
        file = new File(saveDataPath, fileName);
        file.createNewFile();
    }

}
