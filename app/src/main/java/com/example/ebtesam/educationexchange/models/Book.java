package com.example.ebtesam.educationexchange.models;

/**
 * Created by ebtesam on 18/02/2018 AD.
 */

public class Book  {
    private String book_name;
    private String image_path;
    private String id_book;
    private String user_id;
    private String price;
    private String availability;
    private String status;
    private String course_id;
    private String type;
    private String faculty;

    public Book() {
    }

    public Book(String book_name,  String image_path, String id_book, String user_id, String price, String availability, String status, String course_id, String type, String faculty) {
        this.book_name = book_name;
        this.image_path = image_path;
        this.id_book = id_book;
        this.user_id = user_id;
        this.price = price;
        this.availability = availability;
        this.status = status;
        this.course_id = course_id;
        this.type = type;
        this.faculty = faculty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getId_book() {
        return id_book;
    }

    public void setId_book(String id_book) {
        this.id_book = id_book;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
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
