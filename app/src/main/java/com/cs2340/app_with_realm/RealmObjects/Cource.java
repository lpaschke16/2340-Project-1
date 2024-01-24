package com.cs2340.app_with_realm.RealmObjects;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Cource extends RealmObject {
    @PrimaryKey String id = UUID.randomUUID().toString();
    String name;
    String time;
    String instructor;
    String location;
    RealmList<Exam> exams;
    RealmList<Assignment> assignments;
    RealmList<Task> tasks;
}