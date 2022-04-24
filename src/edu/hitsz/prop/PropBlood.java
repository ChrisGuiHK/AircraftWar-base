package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;

public class PropBlood extends AbstractProp{

    private int recoverNum = 50;

    public PropBlood(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(Object obj) {
        if(obj instanceof HeroAircraft){
            if(Main.soundEffect) {
                new MusicThread("src/videos/get_supply.wav").start();
            }
            HeroAircraft heroAircraft = (HeroAircraft) obj;
            heroAircraft.setHp(recoverNum + heroAircraft.getHp());
        }
    }
}
