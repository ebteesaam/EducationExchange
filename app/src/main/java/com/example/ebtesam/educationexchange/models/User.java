package com.example.ebtesam.educationexchange.models;

/**
 * Created by ebtesam on 07/02/2018 AD.
 */

public class User {

    private String username;
    private String user_id;
    private String email;
    private String profile_photo;
    private String status;
    private String report;


    public User(String username, String user_id, String email, String profile_photo, String status, String report) {
        this.username = username;
        this.user_id = user_id;
        this.email = email;
        this.profile_photo = profile_photo;
        this.status = status;
        this.report = report;
    }

    public User() {
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
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
                ", profile_photo='" + profile_photo + '\'' +
                ", status='" + status + '\'' +
                ", report='" + report + '\'' +
                '}';
    }
}
