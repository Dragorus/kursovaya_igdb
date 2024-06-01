package com.example.kursovaya_igdb.model;

import androidx.annotation.NonNull;

public class Video {
    private String name;
    private String video_id;

    Video(){

    }

    public String getName() {
        return name;
    }

    public String getVideo_id() {
        return video_id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Video{" +
                "name='" + name + '\'' +
                ", video_id='" + video_id + '\'' +
                '}';
    }
}
