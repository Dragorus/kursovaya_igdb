package com.example.kursovaya_igdb.model;

import androidx.annotation.NonNull;

public class Genre {
    int id;
    String name;

    Genre() {

    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                '}';
    }
}
