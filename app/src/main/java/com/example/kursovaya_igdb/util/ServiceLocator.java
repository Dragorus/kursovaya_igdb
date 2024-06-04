package com.example.kursovaya_igdb.util;

import static com.example.kursovaya_igdb.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;

import android.app.Application;

import com.example.kursovaya_igdb.database.GamesRoomDatabase;
import com.example.kursovaya_igdb.repository.GamesRepository;
import com.example.kursovaya_igdb.repository.IGamesRepository;
import com.example.kursovaya_igdb.service.GamesApiService;
import com.example.kursovaya_igdb.source.BaseGamesDataSource;
import com.example.kursovaya_igdb.source.BaseGamesLocalDataSource;
import com.example.kursovaya_igdb.source.GamesDataSource;
import com.example.kursovaya_igdb.source.GamesLocalDataSource;

import java.io.IOException;
import java.security.GeneralSecurityException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {

    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    public GamesApiService getGamesApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.GAME_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(GamesApiService.class);
    }
    public GamesRoomDatabase getGamesDao(Application application) {
        return GamesRoomDatabase.getDatabase(application);
    }
    public IGamesRepository getGamesRepository(Application application) throws GeneralSecurityException, IOException {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);
        BaseGamesDataSource gamesDataSource;
        BaseGamesLocalDataSource gamesLocalDataSource;
        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);
        gamesDataSource = new GamesDataSource();
        gamesLocalDataSource = new GamesLocalDataSource(getGamesDao(application), sharedPreferencesUtil, dataEncryptionUtil);
        return new GamesRepository(gamesDataSource, gamesLocalDataSource);
    }
}
