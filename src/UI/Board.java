package UI;

import edu.hitsz.application.Main;
import edu.hitsz.dao.User;
import edu.hitsz.dao.UsersDao;
import edu.hitsz.dao.UsersDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumMap;

public class Board {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel buttonPanel;
    private JTable scoreTable;
    private JScrollPane tableScrollPane;
    private JButton deleteButton;
    private JLabel headerLabel;
    private JLabel header;

    private final UsersDao usersDao;
    private final DefaultTableModel model;
    private final String[] columnName = {"排名", "姓名", "分数", "时间"};
    private static final EnumMap<Main.GameMode,String> TITLE = new EnumMap<>(Main.GameMode.class);
    static {
        TITLE.put(Main.GameMode.EASY,"难度：EASY");
        TITLE.put(Main.GameMode.NORMAL,"难度：MEDIUM");
        TITLE.put(Main.GameMode.HARD,"难度：HARD");
    }
    public Board() {
        usersDao = new UsersDaoImpl();
        header.setText(TITLE.get(Main.gameMode));
        model = new DefaultTableModel(getUserTable(),columnName){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        scoreTable.setModel(model);
        tableScrollPane.setViewportView(scoreTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null,"是否确定删除选中的玩家？");
                if(choice == JOptionPane.OK_OPTION) {
                    int row = scoreTable.getSelectedRow();
                    if (row != -1) {
                        usersDao.deleteUser(row + 1);
                        model.setDataVector(getUserTable(), columnName);
                        scoreTable.updateUI();
                    }
                }
            }
        });


    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String[][] getUserTable(){
        ArrayList<User> users = usersDao.getAllUsers();
        String[][] tableData = new String[users.size()][4];

        int i = 0;
        for(User user:users){
            tableData[i][0] = String.valueOf(user.getRate());
            tableData[i][1] = user.getUserName();
            tableData[i][2] = String.valueOf(user.getScore());
            tableData[i][3] = user.getTime();
            i += 1;
        }
        return tableData;
    }

    public void addUser(User user){
        usersDao.addUser(user);
        model.setDataVector(getUserTable(),columnName);
        scoreTable.updateUI();
    }
}
