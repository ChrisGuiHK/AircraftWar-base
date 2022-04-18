package edu.hitsz.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class User implements Comparable<User>, Serializable {

    private final String userName;
    private final Date date;
    private final int score;
    private int rate;

    public User(String userName, int score){
        this.userName = userName;
        this.score = score;

        Calendar calendar = Calendar.getInstance();
        this.date = calendar.getTime();
        this.rate = 0;
    }

    @Override
    public int compareTo(User o) {
        int num1 = o.score - this.score;
        int num2 = (num1 == 0) ? this.userName.compareTo(o.userName) : num1;
        int num3 = (num2 == 0) ? this.date.compareTo(o.date) :num2;
        return num3;
    }

    public String getUserName() {return userName;}
    public int getScore() {return score;}
    public int getRate() {return rate;}
    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return dateFormat.format(date);
    }

    public void setRate(int rate) {this.rate = rate;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return score == user.score && rate == user.rate && Objects.equals(userName, user.userName) && Objects.equals(date, user.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, date, score, rate);
    }
}
