package com.cs2340.app_with_realm.RealmObjects;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Exam extends RealmObject {
    @PrimaryKey String id = UUID.randomUUID().toString();
    String time;
    String location;
    boolean isFinal;
}
