package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;

public class PropBlood extends AbstractProp{

    private int recoverNum = 100;

    public PropBlood(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public int effect() {
        if(Main.soundEffect) {
            new MusicThread("src/sounds/get_supply.wav").start();
        }
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        heroAircraft.setHp(recoverNum + heroAircraft.getHp());
        return 0;
    }
}
