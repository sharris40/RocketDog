package edu.uco.sdd.rocketdog.commands;

import javafx.scene.Group;

class ScrollLeft extends AbstractCommand {

    private final Group topLevelBackground;
    private final int focalSpeed;

    public ScrollLeft(Group topLevelGroup, int focalSpeed) {
        this.topLevelBackground = topLevelGroup;
        this.focalSpeed = focalSpeed;
    }

    @Override
    public void execute() {
        topLevelBackground.setTranslateX(topLevelBackground.getTranslateX() + focalSpeed + 15);
    }

}
