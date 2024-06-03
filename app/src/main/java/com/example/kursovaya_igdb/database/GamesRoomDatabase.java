package com.example.kursovaya_igdb.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kursovaya_igdb.model.GameApiResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Database(entities = {GameApiResponse.class}, version = 1)
public abstract class GamesRoomDatabase extends RoomDatabase {
    public abstract GamesDao gamesDao();
    private static volatile GamesRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static GamesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GamesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GamesRoomDatabase.class, "databaseRoom").build();
                }
            }
        }
        return INSTANCE;
    }
}
