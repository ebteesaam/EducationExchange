package com.example.ebtesam.educationexchange.models;

/**
 * Created by ebtesam on 18/02/2018 AD.
 */

public class Report {

    private String id_book;
    private String report_id;
    private String status;
    private String type;
    private String id_user;

    public Report(String id_book, String report_id, String status, String type, String id_user) {
        this.id_book = id_book;
        this.report_id = report_id;
        this.status = status;
        this.type = type;
        this.id_user = id_user;
    }

    public Report() {
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_book() {
        return id_book;
    }

    public void setId_book(String id_book) {
        this.id_book = id_book;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}