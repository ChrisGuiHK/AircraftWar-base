package edu.hitsz.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class User implements Comparable<User>, Serializable {

    public static int id = 0;
    private final String userName;
    private final String time;
    private final int score;
    private int rate;
    private int userId;

    public User(String userName, int score){
        this.userName = userName;
        this.score = score;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        this.time = dateFormat.format(calendar.getTime());
        this.rate = 0;
        this.userId = -1;
    }

    @Override
    public int compareTo(User o) {
        int num1 = o.score - this.score;
        int num2 = (num1 == 0) ? this.userId - o.userId : num1;
        return num2;
    }

    public String getUserName() {return userName;}
    public String getTime() {return time;}
    public int getScore() {return score;}
    public int getRate() {return rate;}

    public void setRate(int rate) {this.rate = rate;}
    public void setUserId() {
        userId = id;
        id += 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return score == user.score && rate == user.rate && userId == user.userId && Objects.equals(userName, user.userName) && Objects.equals(time, user.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, time, score, rate, userId);
    }
}
