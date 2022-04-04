package edu.hitsz.prop;

public class PropBullet extends AbstractProp{

    public PropBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(Object obj) {
        System.out.println("FireSupply active!");
    }
}
