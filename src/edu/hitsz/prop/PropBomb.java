package edu.hitsz.prop;

import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.LinkedList;
import java.util.List;

public class PropBomb extends AbstractProp{

    private List<AbstractFlyingObject> flyingObjects = new LinkedList<>();

    public PropBomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public int effect() {
        if(Main.soundEffect) {
            new MusicThread("src/sounds/bomb_explosion.wav").start();
        }
        return notifyList();
    }

    public void addFlyingObject(AbstractFlyingObject abstractFlyingObject){
        flyingObjects.add(abstractFlyingObject);
    }

    public void deleteFlyingObject(AbstractFlyingObject abstractFlyingObject){
        flyingObjects.remove(abstractFlyingObject);
    }

    public int notifyList(){
        int score = 0;
        for(AbstractFlyingObject flyingObject : flyingObjects){
            score += flyingObject.clear();
        }
        return score;
    }
}
