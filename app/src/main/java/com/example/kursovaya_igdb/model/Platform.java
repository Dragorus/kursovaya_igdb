package com.example.kursovaya_igdb.model;

import androidx.annotation.NonNull;

public class Platform {
    String name;
    Platform(){

    }

    public String getName() {
        return name;
    }
    @NonNull
    @Override
    public String toString() {
        return "Platform{" +
                "name='" + name + '\'' +
                '}';
    }
}
