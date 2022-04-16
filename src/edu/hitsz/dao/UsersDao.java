package edu.hitsz.dao;

import java.util.ArrayList;

public interface UsersDao {
    ArrayList<User> getAllUsers();
    void addUser(User user) ;
}
