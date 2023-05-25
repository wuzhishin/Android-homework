package com.hitsz.demo;

import java.util.LinkedList;

public interface UserDao {
    boolean findByName(String username);
    LinkedList<User> getAllUsers();
    void doAdd(User user);
    boolean login(String username, String password);

    void writeBack();
}
