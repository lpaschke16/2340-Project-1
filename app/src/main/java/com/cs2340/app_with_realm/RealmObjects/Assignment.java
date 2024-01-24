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
}
