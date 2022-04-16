package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EliteEnemy extends AbstractAircraft{


    private double dropRate = 0.6;  //道具掉落率

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        shootNum = 1;
        power = 10;
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
        ShootContext shootContext = new ShootContext(new StraightShootStrategy());
        return shootContext.executeStrategy(this);
    }

    public double getDropRate() { return dropRate; }
}
