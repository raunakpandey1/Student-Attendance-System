package com.example.ternalogin.model;

public class DocumentDetail {
    public String url;
    public String title;
    public String description;
    public String date;

    public DocumentDetail() {
    }

    public DocumentDetail(String url, String title, String description, String date) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
