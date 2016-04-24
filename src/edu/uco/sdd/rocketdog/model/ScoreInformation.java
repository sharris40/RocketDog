/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import java.io.Serializable;

/**
 *
 * @author Sophia
 */
public class ScoreInformation implements Serializable{
    public int score;
    public String name;
    private static final long serialVersionUID = -1892561327013038124L;

    public ScoreInformation(String name, int score) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return (name + "-" + score+"\n");
    }
    
}
