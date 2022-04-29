package edu.hitsz.application;

import edu.hitsz.aircraft.BossEnemyFactory;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.MobEnemyFactory;

import javax.swing.*;
import java.awt.*;

public class HardGame extends Game{

    private int enhancedTime = 0;
    private Timer timer;

    @Override
    public void initialize() {
        this.enhancedInterval = 10 * 1000;
        this.originEnemyMaxNum = 6;
        this.enemyNumberIncreaseRate = 0.02;
        this.eliteEnemyRate = 0.3;
        this.eliteEnemyIncreaseRate = 0.02;
        this.bossScoreThreshold = 500;
        this.bossScoreRate = 0.05;
        this.enhancedEliteRate = 0.04;
        this.enhancedMobRate = 0.03;
        this.heroShootInterval = 500;
        this.enemyShootInterval = 700;
        this.ifGenerateBoss = true;
    }

    @Override
    public void modeInitialize() {
        timer = new Timer(enhancedInterval, event->{
            enhancedTime += 1;

            //精英机数值随时间增强
            EliteEnemyFactory.setRate(1 + enhancedTime * enhancedEliteRate);
            //普通敌机数值随时间增强
            MobEnemyFactory.setRate(1 + enhancedTime * enhancedMobRate);
            //精英机产生率随时间增加
            eliteEnemyRate = Math.min((eliteEnemyRate + eliteEnemyIncreaseRate * enhancedTime), 0.7);
            //最大敌机数量随时间增加
            enemyMaxNumber = (int) Math.min((originEnemyMaxNum * (1 + enhancedTime * enemyNumberIncreaseRate)), 8);
            //boss机产生分数阈值随时间减少
            bossScoreThreshold = (int) Math.max((bossScoreThreshold * (1 - bossScoreRate)), 300);
            //敌机产生周期减少
            cycleDuration = (int) Math.max((cycleDuration * (1 - enhancedTime * intervalTimeRate)), 400);

            System.out.println("提高难度！精英机概率：" + eliteEnemyRate
                    + ", 敌机周期：" + cycleDuration + ", 普通敌机属性提高倍率：" + (1 + enhancedTime * enhancedMobRate)
                    + ", 精英敌机属性提高倍率：" + (1 +enhancedTime * enhancedEliteRate) + ", 最大敌机数量：" + enemyMaxNumber
                    + ", boss机产生阈值：" + bossScoreThreshold);
        });
        timer.start();
        BossEnemyFactory.setIfStrength(true);
        EliteEnemyFactory.setPower(20);
        EliteEnemyFactory.setHp(60);
        MobEnemyFactory.setHp(40);
    }

    @Override
    public void gameOverOperation() {
        timer.stop();
    }
    @Override
    public Image getBackgroundImage() {
        return ImageManager.BACKGROUND_IMAGE_HARD;
    }
}
