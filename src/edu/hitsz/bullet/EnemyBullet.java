package edu.hitsz.bullet;

import edu.hitsz.prop.Clear;

/**
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements Clear {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }
}
