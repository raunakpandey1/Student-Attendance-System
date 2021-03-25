package com.example.ternalogin;

public class msgModel {
    String description, subject, date, percent, faculty, dateRange;

    public msgModel() {
    }

    public msgModel(String description, String subject, String date, String percent, String faculty, String dateRange) {
        this.description = description;
        this.subject = subject;
        this.date = date;
        this.percent = percent;
        this.faculty = faculty;
        this.dateRange = dateRange;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }
}
