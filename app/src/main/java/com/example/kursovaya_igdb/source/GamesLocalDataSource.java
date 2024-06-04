package com.example.kursovaya_igdb.source;

import static com.example.kursovaya_igdb.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.example.kursovaya_igdb.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.example.kursovaya_igdb.util.Constants.LAST_UPDATE_HOME;
import static com.example.kursovaya_igdb.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.util.Log;

import com.example.kursovaya_igdb.database.GamesDao;
import com.example.kursovaya_igdb.database.GamesRoomDatabase;
import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.util.DataEncryptionUtil;
import com.example.kursovaya_igdb.util.SharedPreferencesUtil;
import java.util.ArrayList;
import java.util.List;

public class GamesLocalDataSource extends BaseGamesLocalDataSource {
    private final GamesDao gamesDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public GamesLocalDataSource(GamesRoomDatabase gamesRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.gamesDao = gamesRoomDatabase.gamesDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }


    public void getGame(int id) {
        GamesRoomDatabase.databaseWriteExecutor.execute(() -> {
            GameApiResponse gameApiResponse = gamesDao.getGame(id);
            if (gameApiResponse != null){
                ArrayList<GameApiResponse> gameApiResponses = new ArrayList<>();
                gameApiResponses.add(gameApiResponse);
                gameCallback.onSuccessFromLocal(new ArrayList<>(gameApiResponses), "SINGLE");
            }
        });
    }

    @Override
    public void getPopularGames() {
        GamesRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<GameApiResponse> gameApiResponse = new ArrayList<>(gamesDao.getPopularGame());
            gameCallback.onSuccessFromLocal(gameApiResponse, "POPULAR");
        });
    }

    @Override
    public void getBestGames() {
        GamesRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<GameApiResponse> gameApiResponse = new ArrayList<>(gamesDao.getBestGame());
            gameCallback.onSuccessFromLocal(gameApiResponse, "BEST");
        });
    }

    @Override
    public void getLatestGames() {
        GamesRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<GameApiResponse> gameApiResponse = new ArrayList<>(gamesDao.getLatestGame());
            gameCallback.onSuccessFromLocal(gameApiResponse, "LATEST");
        });
    }

    @Override
    public void getIncomingGames() {
        GamesRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<GameApiResponse> gameApiResponse = new ArrayList<>(gamesDao.getIncomingGames());
            gameCallback.onSuccessFromLocal(gameApiResponse, "INCOMING");
        });
    }

    @Override
    public void getExploreGames() {
        GamesRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<GameApiResponse> gameApiResponse = new ArrayList<>(gamesDao.getExploreGames());
            gameCallback.onSuccessFromLocal(gameApiResponse, "EXPLORE");
        });
    }

    @Override
    public void insertGames(List<GameApiResponse> gameApiResponses, String nQuery) {
        GamesRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<GameApiResponse> allGames = gamesDao.getAll();
            switch (nQuery) {
                case "POPULAR":
                    for (GameApiResponse gameApiResponse : gameApiResponses)
                        gameApiResponse.setPopular(true);
                    sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME,
                            LAST_UPDATE_HOME, String.valueOf(System.currentTimeMillis()));
                    break;
                case "BEST":
                    for (GameApiResponse gameApiResponse : gameApiResponses)
                        gameApiResponse.setBest(true);
                    break;
                case "LATEST":
                    for (GameApiResponse gameApiResponse : gameApiResponses)
                        gameApiResponse.setLatest(true);
                    break;
                case "INCOMING":
                    for (GameApiResponse gameApiResponse : gameApiResponses)
                        gameApiResponse.setIncoming(true);
                    break;
                case "EXPLORE":
                    for (GameApiResponse gameApiResponse : gameApiResponses)
                        gameApiResponse.setExplore(true);
                    break;
                case "WANTED":
                    for (GameApiResponse gameApiResponse : gameApiResponses)
                        gameApiResponse.setWanted(true);
                    break;
                case "PLAYING":
                    for (GameApiResponse gameApiResponse : gameApiResponses)
                        gameApiResponse.setPlaying(true);
                    break;
                case "PLAYED":
                    for (GameApiResponse gameApiResponse : gameApiResponses)
                        gameApiResponse.setPlayed(true);
                    break;
            }
            // Checks if the game just downloaded has already been downloaded earlier
            // in order to preserve the game status (marked as wanted, playing or played)

            if (!nQuery.equals("WANTED") && !nQuery.equals("PLAYING") && !nQuery.equals("PLAYED")){
                for (GameApiResponse game : allGames) {
                    for (GameApiResponse gameApiResponse1 : gameApiResponses){
                        if (game.getId() == gameApiResponse1.getId()) {
                            game.setSynchronized(true);
                            gameApiResponses.set(gameApiResponses.indexOf(gameApiResponse1), game);
                        }
                    }
                }
            }
            // Writes the game in the database and gets the associated primary keys
            List<Long> insertedGameId;
            insertedGameId = gamesDao.insertGamesList(gameApiResponses);
            for (int i = 0; i < gameApiResponses.size(); i++) {
                // Adds the primary key to the corresponding object GameApiResponse just downloaded so that
                // if the user marks the game as wanted... , we can use its id
                // to know which news in the database must be marked as ...
                gameApiResponses.get(i).setId(insertedGameId.get(i).intValue());
            }

            gameCallback.onSuccessSynchronization();
            gameCallback.onSuccessFromLocal(gameApiResponses, nQuery);
        });
    }

    @Override
    public void deleteAll() {
        GamesRoomDatabase.databaseWriteExecutor.execute(() -> {
            int gamesCounter = gamesDao.getAll().size();
            int gamesDeleted = gamesDao.deleteAll();

            // It means that everything has been deleted
            if (gamesCounter == gamesDeleted) {
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                gameCallback.onSuccessDeletion();
            }
        });
    }
}
