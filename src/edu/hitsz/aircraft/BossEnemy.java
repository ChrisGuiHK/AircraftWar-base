package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.ScatteringStrategy;
import edu.hitsz.strategy.ShootContext;

import java.util.List;
import java.util.Random;

public class BossEnemy extends AbstractAircraft{

    private static volatile BossEnemy bossEnemy;
    private volatile MusicThread thread;
    private volatile ShootContext shootContext;
    private static volatile int growTime = 0;

    private BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int power) {
        super(locationX, locationY, speedX, speedY, hp);
        shootContext = new ShootContext(new ScatteringStrategy(5));
        this.score = 100;
        this.power = power;
        shootNum = 5;
        direction = 1;
    }

    public static BossEnemy getInstance(boolean ifGrow){
        if(bossEnemy == null){
            synchronized (BossEnemy.class){
                if(bossEnemy == null){
                    growTime = ifGrow ? (growTime + 1) : 0;
                    bossEnemy = new BossEnemy(
                            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                            3,
                            0,
                            (int)(500 * (1 + growTime * 0.2)),
                            (int)(30 * (1 + growTime * 0.1)));
                    if(ifGrow){
                        System.out.println("BOSS机增强，血量倍率：" + (1 + growTime * 0.2)
                            + "攻击倍率：" + (1 + growTime * 0.1));
                    }
                    if(Main.soundEffect){
                        Main.backgroundMusic.setFlag(true);
                        bossEnemy.thread = new MusicThread("src/sounds/bgm_boss.wav");
                        bossEnemy.thread.setLoop(true);
                        bossEnemy.thread.start();
                    }
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
                Main.backgroundMusic = new MusicThread("src/sounds/bgm.wav");
                Main.backgroundMusic.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        bossEnemy = null;
    }

    @Override
    public List<BaseBullet> shoot() {
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
