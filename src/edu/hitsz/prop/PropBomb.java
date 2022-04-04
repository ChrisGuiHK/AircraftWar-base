package edu.hitsz.prop;

public class PropBomb extends AbstractProp{

    public PropBomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(Object obj) {
        System.out.println("BombSupply active!");
    }
}
