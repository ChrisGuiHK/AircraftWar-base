package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements AircraftFactory{
    @Override
    public AbstractAircraft createAircraft() {
        return BossEnemy.getInstance();
    }
}
