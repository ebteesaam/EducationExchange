package com.example.ebtesam.educationexchange.models;

/**
 * Created by ebtesam on 18/02/2018 AD.
 */

public class Book {
    private String book_name;
    private String date_created;
    private String image_path;

    private String user_id;
    private String price;
    private String availability;
    private String status;
    private String course_id;

    public Book() {
    }

    public Book(String book_name, String date_created, String image_path,  String user_id, String price, String availability, String status, String course_id) {
        this.book_name = book_name;
        this.date_created = date_created;
        this.image_path = image_path;
        this.user_id = user_id;
        this.price = price;
        this.availability = availability;
        this.status = status;
        this.course_id = course_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}
