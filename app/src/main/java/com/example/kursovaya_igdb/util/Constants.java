package com.example.kursovaya_igdb.util;

public class Constants {
    public static final int FRESH_TIMEOUT = 60*30*1000;
    public static final String LAST_UPDATE_HOME = "last_update_home";
    public static final String YOUTUBE_URL = "http://www.youtube.com/watch?v=";
    public static final String GAME_API_BASE_URL = "https://api.igdb.com/v4/";
    public static final String TOP_HEADLINES_ENDPOINT = "games";
    public static final String CLIENT_ID = "Client-ID";
    public static final String CLIENT_ID_VALUE = "5ows3sd8v75czya1adc2ma7m4bhz2x"; // ID Клиента
    public static final String TOKEN_API = "Authorization";
    public static final String TOKEN_API_VALUE = "Bearer 6pf9qfim8l039hfvypnjy1ax3l8jvr"; // Токен доступа

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/json;charset=utf-8";

    public static final String ENCRYPTED_SHARED_PREFERENCES_FILE_NAME = "com.example.kursovaya_igdb.encrypted_preferences";
    public static final String SHARED_PREFERENCES_FILE_NAME = "com.example.kursovaya_igdb.preferences";
    public static final String ENCRYPTED_DATA_FILE_NAME = "com.example.kursovaya_igdb.encrypted_file.txt";

    public static final String SHARED_PREFERENCES_FIRST_LOADING_WANTED = "first_loading";
    public static final String SHARED_PREFERENCES_FIRST_LOADING_PLAYING = "first_loading";
    public static final String SHARED_PREFERENCES_FIRST_LOADING_PLAYED = "first_loading";
}
