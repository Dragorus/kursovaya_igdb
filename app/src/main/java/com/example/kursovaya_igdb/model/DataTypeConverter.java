package com.example.kursovaya_igdb.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DataTypeConverter {
    private static final Gson gson = new Gson();
    @TypeConverter
    public static Cover stringToCover(String data) {
        if (data == null) {
            return null;
        }
        Type listType = new TypeToken<Cover>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String CoverToString(Cover cover) {
        return gson.toJson(cover);
    }
    @TypeConverter
    public static List<Genre> stringToGenre(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Genre>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String GenreToString(List<Genre> someObjects) {
        return gson.toJson(someObjects);
    }
    @TypeConverter
    public static List<Platform> stringToPlatform(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Platform>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String PlatformToString(List<Platform> someObjects) {
        return gson.toJson(someObjects);
    }
    @TypeConverter
    public static List<Franchise> stringToFranchise(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Franchise>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String FranchiseToString(List<Franchise> someObjects) {
        return gson.toJson(someObjects);
    }
    @TypeConverter
    public static List<InvolvedCompany> stringToInvolvedCompany(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<InvolvedCompany>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String InvolvedCompanyToString(List<InvolvedCompany> someObjects) {
        return gson.toJson(someObjects);
    }
    @TypeConverter
    public static List<Screenshot> stringToScreenshot(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Screenshot>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ScreenshotToString(List<Screenshot> someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static List<Integer> stringToIntegerList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Integer>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String IntegerListToString(List<Integer> someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static List<Video> stringToVideoList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Video>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String VideoListToString(List<Video> someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static List<ReleaseDate> stringToReleaseDatesList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ReleaseDate>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ReleaseDatesListToString(List<ReleaseDate> someObjects) {
        return gson.toJson(someObjects);
    }
}
