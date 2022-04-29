package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.util.Random;

public class EliteEnemyFactory implements AircraftFactory{

    private static double rate = 1;
    private static int hp = 60;
    private static int power = 10;

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
                    (int) (4 * rate),
                    (int)(hp * rate),
                    (int)(power * rate));
    }

    public static void setRate(double rate) {
        EliteEnemyFactory.rate = rate;
    }
    public static void setHp(int hp) {
        EliteEnemyFactory.hp = hp;
    }
    public static void setPower(int power) {
        EliteEnemyFactory.power = power;
    }
}
