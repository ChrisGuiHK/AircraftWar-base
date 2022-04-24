package edu.hitsz.prop;

import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;

public class PropBomb extends AbstractProp{

    public PropBomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect(Object obj) {
        if(Main.soundEffect) {
            new MusicThread("src/videos/bomb_explosion.wav").start();
        }
        System.out.println("BombSupply active!");
    }
}
