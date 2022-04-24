package edu.hitsz.dao;

import edu.hitsz.application.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.TreeSet;

public class UsersDaoImpl implements UsersDao{
    private TreeSet<User> users;
    private final static EnumMap<Main.GameMode,String> PATH = new EnumMap<>(Main.GameMode.class);
    static{
        PATH.put(Main.GameMode.EASY, "src/EASY.dat");
        PATH.put(Main.GameMode.NORMAL,"src/NORMAL.dat");
        PATH.put(Main.GameMode.HARD,"src/HARD.dat");
    }

    private void readInData(){
        try(ObjectInputStream ois = new ObjectInputStream((new FileInputStream(PATH.get(Main.gameMode))))){
            users = (TreeSet<User>) ois.readObject();
        } catch (IOException e) {
            users = new TreeSet<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeBackData() {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH.get(Main.gameMode)));
            oos.writeObject(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {
        int rate = 1;
        readInData();
        users.add(user);
        for(User usr:users){
            usr.setRate(rate);
            rate += 1;
        }
        writeBackData();
    }

    @Override
    public boolean deleteUser(int rate) {
        int rateNum = 1;
        readInData();
        if(rate > users.size() || rate <= 0){
            return false;
        }
        for(User user:users){
            if(user.getRate() == rate){
                users.remove(user);
                break;
            }
        }
        for(User user:users){
            user.setRate(rateNum);
            rateNum += 1;
        }
        writeBackData();
        return true;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        readInData();
        return new ArrayList<>(users);
    }
}
