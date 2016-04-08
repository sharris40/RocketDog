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

    public ScrollRight(Group topLevelGroup, int focalSpeed) {
        this.focalSpeed = focalSpeed;
        this.topLevelBackground = topLevelGroup;
    }

    @Override
    public void execute() {
        topLevelBackground.setTranslateX(topLevelBackground.getTranslateX() - focalSpeed - 15);
    }

}
