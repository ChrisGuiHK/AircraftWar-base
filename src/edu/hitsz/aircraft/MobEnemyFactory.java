package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobEnemyFactory implements AircraftFactory{
    private static double rate = 1;
    private static int hp = 30;
    @Override
    public AbstractAircraft createAircraft() {
        return new MobEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                0,
                10,
                (int) (hp * rate)
        );
    }

    public static void setRate(double rate) {
        MobEnemyFactory.rate = rate;
    }
    public static void setHp(int hp) {
        MobEnemyFactory.hp = hp;
    }
}
