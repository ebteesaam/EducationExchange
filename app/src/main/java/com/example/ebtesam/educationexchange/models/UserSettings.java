package com.example.ebtesam.educationexchange.models;

/**
 * Created by ebtesam on 10/02/2018 AD.
 */

public class UserSettings  {


        private User user;

        private Book book;

    public UserSettings(User user) {
            this.user = user;

        }

        public UserSettings() {

        }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }



        @Override
        public String toString() {
            return "UserSettings{" +
                    "user=" + user +
                    '}';
        }
    }