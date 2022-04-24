package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.*;

import java.util.List;
import java.util.Random;

public class BossEnemy extends AbstractAircraft{

    private static volatile BossEnemy bossEnemy;
    private static volatile MusicThread thread;

    private BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        shootNum = 5;
        power = 50;
        direction = 1;
    }

    public static BossEnemy getInstance(){
        if(bossEnemy == null){
            synchronized (BossEnemy.class){
                if(bossEnemy == null){
                    if(Main.soundEffect){
                        Main.backgroundMusic.setFlag(true);
                        thread = new MusicThread("src/videos/bgm_boss.wav");
                        thread.setLoop(true);
                        thread.start();
                    }
                    bossEnemy = new BossEnemy(
                            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                            3,
                            0,
                            500);
                }
            }
        }
        return bossEnemy;
    }

    public static boolean isNull(){
        if(bossEnemy == null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void vanish() {
        super.vanish();
        if(Main.soundEffect) {
            thread.setFlag(true);
            try{
                Main.backgroundMusic = new MusicThread("src/videos/bgm.wav");
                Main.backgroundMusic.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        bossEnemy = null;
    }

    @Override
    public List<BaseBullet> shoot() {
        ShootContext shootContext = new ShootContext(new ScatteringStrategy(5));
        return shootContext.executeStrategy(this);
    }

    public AbstractProp createProp() {
        Random r = new Random();
        double random = r.nextDouble();
        PropFactory propFactory;
        if(random < AbstractProp.genBloodRate){
            propFactory = new PropBloodFactory();
        }else if(random < AbstractProp.genBloodRate + AbstractProp.genBulletRate){
            propFactory = new PropBulletFactory();
        }else{
            propFactory = new PropBombFactory();
        }
        return propFactory.createProp(this);
    }
}
