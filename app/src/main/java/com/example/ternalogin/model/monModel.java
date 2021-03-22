package com.example.ternalogin.model;

public class monModel {
    String id,name,roll;
    int present,total;
    float percentage;
    public monModel() {
    }

    public monModel(String id, String name, String roll, int present, int total, float percentage) {
        this.id = id;
        this.name = name;
        this.roll = roll;
        this.present = present;
        this.total = total;
        this.percentage = percentage;
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

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public long getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
