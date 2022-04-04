package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

public class PropBombFactory implements PropFactory{
    @Override
    public AbstractProp createProp(AbstractAircraft abstractAircraft) {
        return new PropBomb(abstractAircraft.getLocationX(),
                abstractAircraft.getLocationY(),
                0,5);
    }
}
