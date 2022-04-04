package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class PropBlood extends AbstractProp{

    private int recoverNum = 50;

    public PropBlood(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(Object obj) {
        if(obj instanceof HeroAircraft){
            HeroAircraft heroAircraft = (HeroAircraft) obj;
            heroAircraft.setHp(recoverNum + heroAircraft.getHp());
        }
    }
}
