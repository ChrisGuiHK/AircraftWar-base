package edu.hitsz.aircraft;

public class BossEnemyFactory implements AircraftFactory{
    private static boolean ifStrength = false;
    @Override
    public AbstractAircraft createAircraft() {
        return BossEnemy.getInstance(ifStrength);
    }

    public static void setIfStrength(boolean ifStrength) {
        BossEnemyFactory.ifStrength = ifStrength;
    }
}
