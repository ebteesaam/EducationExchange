package com.example.ebtesam.educationexchange.models;

/**
 * Created by ebtesam on 07/02/2018 AD.
 */

public class UserAccountSettings {



    private String profile_photo;
    private String username;
    private String email;
    private long my_book;
    private String my_lecture_note;

    public UserAccountSettings(String profile_photo, String username, String email, long my_book, String my_lecture_note) {
        this.profile_photo = profile_photo;
        this.username = username;
        this.email = email;
        this.my_book = my_book;
        this.my_lecture_note = my_lecture_note;
    }

    public UserAccountSettings() {
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getMy_book() {
        return my_book;
    }

    public void setMy_book(long my_book) {
        this.my_book = my_book;
    }

    public String getMy_lecture_note() {
        return my_lecture_note;
    }

    public void setMy_lecture_note(String my_lecture_note) {
        this.my_lecture_note = my_lecture_note;
    }
}
