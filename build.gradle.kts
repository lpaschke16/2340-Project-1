// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
//    id("realm-android") apply true
}

buildscript {
    repositories {
        jcenter()
        google()
        mavenCentral()

    }
    dependencies {
        classpath("io.realm:realm-gradle-plugin:10.15.1")
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6")
    }
}
