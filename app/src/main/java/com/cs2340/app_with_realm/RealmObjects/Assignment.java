package com.cs2340.app_with_realm.RealmObjects;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Assignment extends RealmObject {
    @PrimaryKey
    String id = UUID.randomUUID().toString();
    String dueDate;
    double percentageDone;

    // Adding appropriate variables for assignment tab
    private String title;
    private String description;

    private String className;

    // Getter and setter for the title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Additional getters and setters for other fields...
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    // If using a primary key, you might also want a method to generate IDs
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
