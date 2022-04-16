package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.ScatteringStrategy;
import edu.hitsz.aircraft.StraightShootStrategy;

public class PropBullet extends AbstractProp{

    public PropBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(Object obj) {
        if(obj instanceof HeroAircraft){
            HeroAircraft heroAircraft = (HeroAircraft) obj;
            heroAircraft.setShootNum(3);
            heroAircraft.setStrategy(new ScatteringStrategy());
        }
    }
}
