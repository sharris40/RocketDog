/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

import java.util.Comparator;

/**
 *
 * @author Sophia
 */
public class ScoreInfoComparator implements Comparator<ScoreInformation>{
    public int compare(ScoreInformation s1, ScoreInformation s2){
        if(s1.getScore()>s2.getScore()){
            return -1;
        }
        else if(s1.getScore()<s2.getScore()){
            return 1;
        }
        else return 0;

    }
}
