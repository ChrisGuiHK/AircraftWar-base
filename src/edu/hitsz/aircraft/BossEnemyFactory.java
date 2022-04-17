package edu.hitsz.aircraft;

public class BossEnemyFactory implements AircraftFactory{
    @Override
    public AbstractAircraft createAircraft() {
        return BossEnemy.getInstance();
    }
}
