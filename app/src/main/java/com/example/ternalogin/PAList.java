package com.example.ternalogin;

public class PAList {
    int present;
    String absent,name;

    public PAList() {
    }

    public PAList(int present, String absent, String name) {
        this.present = present;
        this.absent = absent;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }
}
