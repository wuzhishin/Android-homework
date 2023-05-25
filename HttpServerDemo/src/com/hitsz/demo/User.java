package com.hitsz.demo;

public class User {
    private String username;
    private String password;


    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String toString(){
        return String.format("%s %s",username,password);
    }

}
