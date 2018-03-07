package com.example.ebtesam.educationexchange.models;

/**
 * Created by ebtesam on 18/02/2018 AD.
 */

public class Announcement {
    private String title;
    private String date_created;
    private String id_announcement;
    private String user_id;
    private String status;
    private String course_id;
    private String type;
    private String text;
    private String faculty;


    public Announcement() {
    }

    public Announcement(String title, String date_created, String id_announcement, String user_id, String status, String course_id, String type, String text, String faculty) {
        this.title = title;
        this.date_created = date_created;
        this.id_announcement = id_announcement;
        this.user_id = user_id;
        this.status = status;
        this.course_id = course_id;
        this.type = type;
        this.text = text;
        this.faculty = faculty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getId_announcement() {
        return id_announcement;
    }

    public void setId_announcement(String id_announcement) {
        this.id_announcement = id_announcement;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
