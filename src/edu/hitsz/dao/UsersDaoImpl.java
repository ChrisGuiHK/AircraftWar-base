package edu.hitsz.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class UsersDaoImpl implements UsersDao{
    private TreeSet<User> users;
    private final String path = "src/Users.dat";

    private void readInData(){
        try(ObjectInputStream ois = new ObjectInputStream((new FileInputStream(path)))){
            users = (TreeSet<User>) ois.readObject();
            User.id = users.size();
        } catch (EOFException e) {
            users = new TreeSet<>();
            User.id = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeBackData() {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {
        int rate = 1;
        readInData();
        user.setUserId();
        users.add(user);
        for(User usr:users){
            usr.setRate(rate);
            rate += 1;
        }
        writeBackData();
    }

    @Override
    public ArrayList<User> getAllUsers() {
        readInData();
        return new ArrayList<User>(users);
    }
}
