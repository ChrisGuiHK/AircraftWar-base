package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.ScatteringStrategy;
import edu.hitsz.strategy.StraightShootStrategy;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;

import javax.swing.*;

public class PropBullet extends AbstractProp{
    private static final Timer TIMER = new Timer(15000, event -> HeroAircraft.getInstance().setStrategy(new StraightShootStrategy(1)));

    public PropBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public int effect() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        if(Main.soundEffect) {
            new MusicThread("src/sounds/bullet.wav").start();
        }
        if(TIMER.isRunning()){
            TIMER.stop();
        }
        heroAircraft.setStrategy(new ScatteringStrategy(Math.min(heroAircraft.getShootNum() + 2,7)));
        TIMER.setRepeats(false);
        TIMER.start();
        return 0;
    }
}
