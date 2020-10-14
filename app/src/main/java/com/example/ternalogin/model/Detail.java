package com.example.ternalogin.model;

public class Detail {
    String name,branch,email,div,roll, attendance, tattendance, id;

    public Detail() {

    }

    public Detail(String name, String branch, String email, String div, String roll, String attendance, String tattendance, String id) {
        this.name = name;
        this.branch = branch;
        this.email = email;
        this.div = div;
        this.roll = roll;
        this.attendance = attendance;
        this.tattendance = tattendance;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getTattendance() {
        return tattendance;
    }

    public void setTattendance(String tattendance) {
        this.tattendance = tattendance;
    }
}
