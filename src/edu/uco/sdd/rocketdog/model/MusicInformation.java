/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.model;

/**
 *
 * @author Sophia
 */
public class MusicInformation {
    String bg;
    String am;
    MusicPlayer mp_bg;
    MusicPlayer mp_am;

    public MusicInformation(String bg, String am) {
        this.bg = bg;
        this.am = am;
    }
    
    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public MusicPlayer getMp_bg() {
        return mp_bg;
    }

    public void setMp_bg(MusicPlayer mp_bg) {
        this.mp_bg = mp_bg;
    }

    public MusicPlayer getMp_am() {
        return mp_am;
    }

    public void setMp_am(MusicPlayer mp_am) {
        this.mp_am = mp_am;
    }

}
