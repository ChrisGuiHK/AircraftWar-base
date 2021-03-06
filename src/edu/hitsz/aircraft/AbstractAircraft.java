package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.Clear;
import edu.hitsz.strategy.ShootContext;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject implements Clear {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    protected int shootNum = 0;     //子弹一次发射数量
    protected int power = 0;       //子弹伤害
    protected int direction = 0;  //子弹射击方向 (向上发射：1，向下发射：-1)
    protected int score = 0;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public int getHp() {return hp;}
    public int getShootNum() {return shootNum;}
    public int getPower() {return power;}
    public int getDirection() {return direction;}
    public int getScore() { return score; }

    public void setShootNum(int shootNum) {this.shootNum = shootNum;}
    public void setHp(int hp) {
        if(hp > maxHp) {
            this.hp = maxHp;
        } else if(hp <= 0){
            this.hp =0;
            vanish();
        } else {
            this.hp = hp;
        }
    }
    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();

    @Override
    public final int clear() {
        vanish();
        return getScore();
    }
}


