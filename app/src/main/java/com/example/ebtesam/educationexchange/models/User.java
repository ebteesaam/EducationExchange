package com.example.ebtesam.educationexchange.models;

/**
 * Created by ebtesam on 07/02/2018 AD.
 */

public class User {

    private String username;
    private String user_id;
    private String email;

    public User(String username, String user_id, String email) {
        this.username = username;
        this.user_id = user_id;
        this.email = email;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
