package com.hitsz.demo;

import java.util.LinkedList;

public class UserDaoImpl implements UserDao{
    private LinkedList<User> Users;

    public UserDaoImpl(){
        Users = FileIO.readTxtToUser();
    }

    @Override
    public LinkedList<User> getAllUsers(){
        return Users;
    }

    public boolean findByName(String username){
        for(User user:Users){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void doAdd(User user){
        Users.add(user);
    }

    public boolean login(String username, String password){
        for(User user:Users){
            if(user.getUsername().equals(username )&& user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public void writeBack(){
        FileIO.writeToTxt(Users);
    }

}
