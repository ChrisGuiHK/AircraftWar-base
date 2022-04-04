package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

public class PropBulletFactory implements PropFactory{
    @Override
    public AbstractProp createProp(AbstractAircraft abstractAircraft) {
        return new PropBullet(abstractAircraft.getLocationX(),
                abstractAircraft.getLocationY(),
                0,5);
    }
}
