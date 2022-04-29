package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.ShootContext;
import edu.hitsz.strategy.StraightShootStrategy;

import java.util.List;
import java.util.Random;

public class EliteEnemy extends AbstractAircraft{

    private ShootContext shootContext;
    private double dropRate = 0.6;  //道具掉落率

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int power) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootContext = new ShootContext(new StraightShootStrategy(1));
        this.score = 20;
        this.power = power;
        shootNum = 1;
        direction = 1;
    }

    @Override
    public void forward() {
        super.forward();
        if(locationY >= Main.WINDOW_HEIGHT){
            vanish();
        }
    }

    public AbstractProp createProp() {
        Random r = new Random();
        double random = r.nextDouble();
        PropFactory propFactory;
        if(random < AbstractProp.genBloodRate){
            propFactory = new PropBloodFactory();
        }else if(random < AbstractProp.genBloodRate + AbstractProp.genBulletRate){
            propFactory = new PropBulletFactory();
        }else{
            propFactory = new PropBombFactory();
        }
        return propFactory.createProp(this);
    }

    @Override
    public List<BaseBullet> shoot() {
        return shootContext.executeStrategy(this);
    }

    public double getDropRate() { return dropRate; }
}
