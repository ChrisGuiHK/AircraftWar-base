package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class ShootContext {
    private ShootStrategy shootStrategy;

    public ShootContext(ShootStrategy shootStrategy){
        this.shootStrategy = shootStrategy;
    }
    public void setStrategy(ShootStrategy shootStrategy){
        this.shootStrategy = shootStrategy;
    }
    public List<BaseBullet> executeStrategy(AbstractAircraft abstractAircraft){
        return shootStrategy.shoot(abstractAircraft);
    }
}
