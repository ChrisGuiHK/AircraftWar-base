package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class BaseGame extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;

    protected int enhancedInterval = 5 * 1000;        //敌机增强的时间间隔
    protected int originEnemyMaxNum = 5;              //最大敌机数量
    protected double enemyNumberIncreaseRate = 0.01;  //敌机数量增长率
    protected double eliteEnemyRate = 0.2;            //精英机生成概率
    protected double eliteEnemyIncreaseRate = 0.02;   //精英机生成概率增长率
    protected int bossScoreThreshold = 500;           //boss机生成分数
    protected double bossScoreRate = 0.05;            //boss机产生分数减少率
    protected double enhancedEliteRate = 0.02;        //精英机增长率
    protected double enhancedMobRate = 0.02;          //普通敌机增长率
    protected double intervalTimeRate = 0.02;         //敌机产生周期减少率
    protected int heroShootInterval = 600;            //英雄机射击间隔
    protected int enemyShootInterval = 600;           //敌机射击间隔
    protected boolean ifGenerateBoss = false;         //是否生成boss机
    protected int enemyMaxNumber = 5;

    private boolean gameOverFlag = false;
    private int bossGenTime = 0;
    private int enhancedTime = 0;                   //敌机强化次数
    private int score = 0;
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 600;
    private int cycleTime = 0;


    public BaseGame() {
        initialize();
        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        modeInitialize();

        ThreadFactory gameThread =  new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("game thread");
                return t;
            }
        };
        //Scheduled 线程池，用于定时任务调度
        executorService = new ScheduledThreadPoolExecutor(1,gameThread);

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    public void modeInitialize(){}

    public abstract void initialize();
    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    if(ifGenerateBoss && score >= (bossGenTime + 1) * bossScoreThreshold){
                        bossGenTime += 1;
                        if(BossEnemy.isNull()){
                            enemyAircrafts.add(new BossEnemyFactory().createAircraft());
                        }
                    }else{
                        Random r = new Random();
                        if(r.nextDouble() < eliteEnemyRate) {
                            enemyAircrafts.add(new EliteEnemyFactory().createAircraft());
                        } else {
                            enemyAircrafts.add(new MobEnemyFactory().createAircraft());
                        }
                    }
                }
            }
            // 飞机射出子弹
            shootAction();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                gameOverOperation();
                synchronized (Main.LOCK) {
                    Main.LOCK.notify();
                }
                if(Main.soundEffect) {
                    new MusicThread("src/sounds/game_over.wav").start();
                }
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    public void gameOverOperation() {}

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private boolean shootCycleJudge(int shootCycleDuration){
        int shootCycleTime = (time - timeInterval) % shootCycleDuration;
        shootCycleTime += timeInterval;
        if (shootCycleTime >= shootCycleDuration && shootCycleTime - timeInterval < shootCycleTime) {
            // 跨越到新的周期
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // 敌机射击
        if(shootCycleJudge(enemyShootInterval)) {
            for (AbstractAircraft enemy : enemyAircrafts) {
                enemyBullets.addAll(enemy.shoot());
            }
        }

        // 英雄射击
        if(shootCycleJudge(heroShootInterval)) {
            heroBullets.addAll(heroAircraft.shoot());
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction(){
        for(AbstractProp prop : props){
            prop.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if(heroAircraft.crash(bullet)){
                heroAircraft.setHp(heroAircraft.getHp() - bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    if(Main.soundEffect) {
                        new MusicThread("src/sounds/bullet_hit.wav").start();
                    }
                    enemyAircraft.setHp(enemyAircraft.getHp() - bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // 获得分数，产生道具补给
                        if(enemyAircraft instanceof EliteEnemy){
                            Random r = new Random();
                            if(r.nextDouble() < ((EliteEnemy) enemyAircraft).getDropRate()) {
                                props.add(((EliteEnemy) enemyAircraft).createProp());
                            }
                        } else if(enemyAircraft instanceof BossEnemy){
                            props.add(((BossEnemy) enemyAircraft).createProp());
                        }
                        score += enemyAircraft.getScore();
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.setHp(heroAircraft.getHp() - Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if(heroAircraft.crash(prop)){
                if(prop instanceof PropBomb){
                    for(AbstractAircraft aircraft : enemyAircrafts){
                        if(aircraft instanceof BossEnemy){
                            continue;
                        }
                        ((PropBomb) prop).addFlyingObject(aircraft);
                    }
                    for(BaseBullet bullet : enemyBullets){
                        ((PropBomb) prop).addFlyingObject(bullet);
                    }
                }
                score += prop.effect();
                prop.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public final void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(getBackgroundImage(), 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(getBackgroundImage(), 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, props);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    public abstract Image getBackgroundImage();

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    public int getScore() { return score;}
}
