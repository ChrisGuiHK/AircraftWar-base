package edu.hitsz.application;

import java.awt.*;

public class EasyGame extends Game{

    @Override
    public void initialize() {
        this.cycleDuration = 600;
        this.originEnemyMaxNum = 3;
        this.eliteEnemyRate = 0.3;
        this.heroShootInterval = 500;
        this.enemyShootInterval = 800;
    }

    @Override
    public Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE_EASY;
    }

}
