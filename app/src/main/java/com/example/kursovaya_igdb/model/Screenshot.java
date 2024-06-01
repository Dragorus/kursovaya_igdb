package com.example.kursovaya_igdb.model;

import androidx.annotation.NonNull;

public class Screenshot {
    private String url;
    public Screenshot(String url) {
        this.url = url;
    }
    public Screenshot(){

    }
    public String getUrl() {
        if (!url.startsWith("https:"))
            return "https:" + url;
        return url;
    }
    @NonNull
    @Override
    public String toString() {
        return "Screenshot{" +
                "url='" + url + '\'' +
                '}';
    }
}
