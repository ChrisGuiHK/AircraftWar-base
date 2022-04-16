package edu.hitsz.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class User implements Comparable<User>, Serializable {

    private String userName;
    private String time;
    private int score;
    private int rate;

    public User(String userName, int score){
        this.userName = userName;
        this.score = score;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        this.time = dateFormat.format(calendar.getTime());
        this.rate = 0;
    }

    @Override
    public int compareTo(User o) {
        int num1 = o.score - this.score;
        int num2 = (num1 == 0) ? this.userName.compareTo(o.userName) : num1;
        int num3 = (num2 == 0) ? this.time.compareTo(o.time) : num2;
        return num3;
    }

    public String getUserName() {return userName;}
    public String getTime() {return time;}
    public int getScore() {return score;}
    public int getRate() {return rate;}

    public void setRate(int rate) {this.rate = rate;}
}
