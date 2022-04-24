package panel;

import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenu {
    private JPanel mainPanel;
    private JButton easyMode;
    private JButton hardMode;
    private JPanel topPanel;
    private JPanel buttomPanel;
    private JButton normalMode;
    private JComboBox soundEffect;
    private JPanel labelPanel;
    private JPanel comboBoxPanel;
    private JLabel label;

    public UserMenu() {

        easyMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSoundEffect();
                Main.gameMode = Main.GameMode.EASY;
                synchronized (Main.OBJECT){
                    Main.OBJECT.notify();
                }
            }
        });
        normalMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSoundEffect();
                Main.gameMode = Main.GameMode.NORMAL;
                synchronized (Main.OBJECT){
                    Main.OBJECT.notify();
                }
            }
        });
        hardMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSoundEffect();
                Main.gameMode = Main.GameMode.HARD;
                synchronized (Main.OBJECT){
                    Main.OBJECT.notify();
                }
            }
        });
    }

    private void setSoundEffect(){
        Main.soundEffect = soundEffect.getSelectedIndex() == 0;
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
