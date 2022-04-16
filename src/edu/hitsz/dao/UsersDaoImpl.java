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
        } catch (EOFException e) {
            users = new TreeSet<>();
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
        int i = 1;
        readInData();
        users.add(user);
        for(User usr:users){
            usr.setRate(i);
            i += 1;
        }
        writeBackData();
    }

    @Override
    public ArrayList<User> getAllUsers() {
        readInData();
        return new ArrayList<User>(users);
    }
}
