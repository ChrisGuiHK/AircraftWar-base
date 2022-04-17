package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class StraightShootStrategy implements ShootStrategy{
    private int shootNum;
    public StraightShootStrategy(int shootNum){
        this.shootNum = shootNum;
    }

    @Override
    public List<BaseBullet> shoot(AbstractAircraft abstractAircraft) {
        List<BaseBullet> res = new LinkedList<>();
        BaseBullet baseBullet;
        abstractAircraft.setShootNum(shootNum);
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + abstractAircraft.getDirection()*2;
        for(int i=0; i<abstractAircraft.getShootNum(); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if(!(abstractAircraft instanceof HeroAircraft)) {
                baseBullet = new EnemyBullet(x + (i * 2 - abstractAircraft.getShootNum() + 1) * 10, y,
                        0, abstractAircraft.getSpeedY() + 5 * abstractAircraft.getDirection(),
                        abstractAircraft.getPower());
                res.add(baseBullet);
            }
            else{
                baseBullet = new HeroBullet(x + (i * 2 - abstractAircraft.getShootNum() + 1) * 10, y,
                        0, abstractAircraft.getSpeedY() + 5 * abstractAircraft.getDirection(),
                        abstractAircraft.getPower());
                res.add(baseBullet);
            }
        }
        return res;
    }
}
