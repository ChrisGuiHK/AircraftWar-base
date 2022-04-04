package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

public interface PropFactory {
    AbstractProp createProp(AbstractAircraft abstractAircraft);
}
