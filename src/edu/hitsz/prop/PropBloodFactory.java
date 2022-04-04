package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

public class PropBloodFactory implements PropFactory{
    @Override
    public AbstractProp createProp(AbstractAircraft abstractAircraft) {
        return new PropBlood(abstractAircraft.getLocationX(),
                abstractAircraft.getLocationY(),
                0,5);
    }
}
