/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sdd.rocketdog.commands;

import javafx.scene.Group;

/**
 *
 * @author Dubs
 */
class ScrollRight extends AbstractCommand {

    private final Group topLevelBackground;
    private final int focalSpeed;
    private final int levelWidth;

    public ScrollRight(Group topLevelGroup, int focalSpeed, int levelWidth) {
        this.focalSpeed = focalSpeed;
        this.topLevelBackground = topLevelGroup;
        this.levelWidth = levelWidth;
    }

    @Override
    public void execute() {
        double translateX = topLevelBackground.getTranslateX();

        // Do not scroll right of screen
        if ( translateX - focalSpeed < -levelWidth) {
            System.out.println("here");
            topLevelBackground.setTranslateX(-levelWidth);
        } else {
            topLevelBackground.setTranslateX(translateX - focalSpeed - 15);
        }
        
    }

}
