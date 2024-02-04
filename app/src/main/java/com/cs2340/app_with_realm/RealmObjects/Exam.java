package com.cs2340.app_with_realm.RealmObjects;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Exam extends RealmObject {
    @PrimaryKey String id = UUID.randomUUID().toString();
    public String name;
    public String date;
    public String time;
    public String location;
    boolean isFinal;
    public Exam() {}
    public Exam(String name, String date, String time, String location) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
