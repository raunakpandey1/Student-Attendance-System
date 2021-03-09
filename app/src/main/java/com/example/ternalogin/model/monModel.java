package com.example.ternalogin.model;

public class monModel {
    String id,name,roll;
    long present,total;
    long gPresent=0, gTotal=0;
    public monModel() {
    }

    public monModel(String id, String name, String roll, long present, long total) {
        this.id = id;
        this.name = name;
        this.roll = roll;
        this.present = present;
        this.total = total;
        gPresent = gPresent + present;
        gTotal = gTotal + total;
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

    public void setPresent(long present) {
        this.present = present;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getgPresent() {
        return gPresent;
    }

    public void setgPresent(long gPresent) {
        this.gPresent = gPresent;
    }

    public long getgTotal() {
        return gTotal;
    }

    public void setgTotal(long gTotal) {
        this.gTotal = gTotal;
    }
}
