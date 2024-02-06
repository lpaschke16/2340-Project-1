package com.cs2340.app_with_realm.RealmObjects;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Task extends RealmObject {
    @PrimaryKey String id = UUID.randomUUID().toString();
    String description;
    private String name;
    private boolean completed;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


}
