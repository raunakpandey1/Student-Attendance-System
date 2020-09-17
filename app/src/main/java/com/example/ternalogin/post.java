package com.example.ternalogin;

public class post {
    String name, branch, email, div, roll;
    int attendance, tattendance;

    public post(){
    }

    public post(String name, String branch, String email, String div, String roll, int attendance, int tattendance) {
        this.name = name;
        this.branch = branch;
        this.email = email;
        this.div = div;
        this.roll = roll;
        this.attendance = attendance;
        this.tattendance = tattendance;
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

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getTattendance() {
        return tattendance;
    }

    public void setTattendance(int tattendance) {
        this.tattendance = tattendance;
    }
}
