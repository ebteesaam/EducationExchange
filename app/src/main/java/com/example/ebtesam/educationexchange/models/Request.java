package com.example.ebtesam.educationexchange.models;

/**
 * Created by ebtesam on 08/04/2018 AD.
 */

public class Request
{
    private String id_book;
    private String request_id;
    private String status;
    private String book_name;
    private String id_user;
    private String mobile;
    private String email;
    private String text;
    private String date;
    private String requester;




    public Request(String id_book, String request_id, String status, String book_name, String id_user, String mobile, String email, String text, String date, String requester) {
        this.id_book = id_book;
        this.request_id = request_id;
        this.status = status;
        this.book_name = book_name;
        this.id_user = id_user;
        this.mobile = mobile;
        this.email = email;
        this.text = text;
        this.date = date;
        this.requester = requester;
    }

    public Request() {
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId_book() {
        return id_book;
    }

    public void setId_book(String id_book) {
        this.id_book = id_book;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
