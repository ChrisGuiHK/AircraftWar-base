package edu.hitsz.application;

import edu.hitsz.dao.User;
import UI.Board;
import UI.UserMenu;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object LOCK = new Object();
    public static GameMode gameMode;
    public static boolean soundEffect;
    public static MusicThread backgroundMusic;
    public enum GameMode{
        /**
         * 枚举类中定义游戏难度EASY，NORMAL，HARD
         *
         */
        EASY,NORMAL,HARD
    }

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        JPanel jPanel = new UserMenu().getMainPanel();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(jPanel);
        frame.setVisible(true);

        synchronized (LOCK){
            try{
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        frame.remove(jPanel);

        BaseGame game;
        if(gameMode == GameMode.EASY){
            game = new EasyBaseGame();
        }else if(gameMode == GameMode.NORMAL){
            game = new NormalBaseGame();
        }else{
            game = new HardBaseGame();
        }
        frame.setContentPane(game);
        frame.setVisible(true);
        game.action();
        if(Main.soundEffect) {
            backgroundMusic = new MusicThread("src/sounds/bgm.wav");
            backgroundMusic.setLoop(true);
            backgroundMusic.start();
        }
        synchronized (LOCK){
            try{
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        frame.remove(game);
        if(Main.soundEffect) {
            backgroundMusic.setFlag(true);
        }

        Board board = new Board();
        jPanel = board.getMainPanel();
        frame.setContentPane(jPanel);
        frame.setVisible(true);
        int score = game.getScore();
        String userName = JOptionPane.showInputDialog("游戏结束，你的得分为" + score + ",\n请输入名字记录得分:");
        if(userName != null){
            if("".equals(userName)){
                userName = "defaultUser";
            }
            board.addUser(new User(userName,score));
        }
    }
}
