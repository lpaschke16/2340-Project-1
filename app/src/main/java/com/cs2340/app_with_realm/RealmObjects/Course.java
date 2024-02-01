package com.cs2340.app_with_realm.RealmObjects;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Course extends RealmObject {
    @PrimaryKey String id;
    public String name;
    public String time;
    public String instructor;
    public String location;
    public RealmList<Exam> exams;
    public RealmList<Assignment> assignments;
    public RealmList<Task> tasks;
    public Course() {}
    public Course(String name, String time, String instructor, String location) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.time = time;
        this.instructor = instructor;
        this.location = location;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}