package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.ScatteringStrategy;
import edu.hitsz.aircraft.StraightShootStrategy;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;

import javax.swing.*;

public class PropBullet extends AbstractProp{
    private static final Timer TIMER = new Timer(15000, event -> HeroAircraft.getInstance().setStrategy(new StraightShootStrategy(1)));

    public PropBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(Object obj) {
        if(obj instanceof HeroAircraft){
            HeroAircraft heroAircraft = (HeroAircraft) obj;
            if(Main.soundEffect) {
                new MusicThread("src/videos/bullet.wav").start();
            }
            if(TIMER.isRunning()){
                TIMER.stop();
            }
            heroAircraft.setStrategy(new ScatteringStrategy(heroAircraft.getShootNum() + 2));
            TIMER.setRepeats(false);
            TIMER.start();
        }
    }
}
