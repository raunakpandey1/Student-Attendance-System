package com.example.ternalogin;

public class PAList {

    String total, present, absent,name, id;

    public PAList() {
    }

    public PAList(String total, String present, String absent, String name, String id) {
        this.total = total;
        this.present = present;
        this.absent = absent;
        this.name = name;
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
