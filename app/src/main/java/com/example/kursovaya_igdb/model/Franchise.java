package com.example.kursovaya_igdb.model;

import androidx.annotation.NonNull;

public class Franchise {
    private int id;
    private String name;

    public Franchise(int id, String name) {
        this.id = id;
        this.name = name;
    }
    Franchise() {

    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Franchises{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
