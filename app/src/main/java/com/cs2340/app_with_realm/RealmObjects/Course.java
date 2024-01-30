package com.cs2340.app_with_realm.RealmObjects;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Course extends RealmObject {
    @PrimaryKey String id = UUID.randomUUID().toString();
    public String name;
    String time;
    String instructor;
    String location;
    RealmList<Exam> exams;
    RealmList<Assignment> assignments;
    RealmList<Task> tasks;
    public Course() {}
    public Course(String name, String time, String instructor, String location) {
        this.name = name;
        this.time = time;
        this.instructor = instructor;
        this.location = location;
    }
}