package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.util.Random;

public class EliteEnemyFactory implements AircraftFactory{
    private int random(){
        Random r = new Random();
        return r.nextBoolean() ? 1 : -1;
    }

    @Override
    public AbstractAircraft createAircraft() {
            return new EliteEnemy(
                    (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                    5*random(),
                    5,
                    60,
                    10);
    }
}
