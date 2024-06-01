package com.example.kursovaya_igdb.model;

import androidx.annotation.NonNull;

public class Cover {
    int id;
    String url;

    public Cover(int id, String url) {
        this.id = id;
        this.url = url;
    }
    Cover(){

    }

    public String getUrl() {
        if(!url.contains("https:")) {
            return "https:" + url;
        }
        else{
            return url;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Cover{" +
                "url='" + url + '\'' +
                '}';
    }
}
