package com.example.kursovaya_igdb.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kursovaya_igdb.model.GameApiResponse;

import java.util.List;


@Dao
public interface GamesDao {
    @Query("SELECT * FROM GameApiResponse")
    List<GameApiResponse> getAll();
    @Query("SELECT * FROM GameApiResponse WHERE id = :id")
    GameApiResponse getGame(long id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertGamesList(List<GameApiResponse> gameApiResponses);
    @Query("SELECT * FROM gameapiresponse WHERE is_popular = 1 ORDER BY follows DESC")
    List<GameApiResponse> getPopularGame();
    @Query("SELECT * FROM gameapiresponse WHERE is_best = 1 ORDER BY totalRating DESC")
    List<GameApiResponse> getBestGame();
    @Query("SELECT * FROM gameapiresponse WHERE is_latest = 1 ORDER BY firstReleaseDate DESC")
    List<GameApiResponse> getLatestGame();
    @Query("SELECT * FROM gameapiresponse WHERE is_incoming = 1 ORDER BY firstReleaseDate ASC")
    List<GameApiResponse> getIncomingGames();
    @Query("DELETE FROM GameApiResponse")
    int deleteAll();
    @Update
    int updateSingleWantedGame(GameApiResponse game);
    @Update
    int updateSinglePlayingGame(GameApiResponse game);
    @Update
    int updateSinglePlayedGame(GameApiResponse game);
    @Query("SELECT * FROM gameapiresponse WHERE is_wanted = 1 ORDER BY added")
    List<GameApiResponse> getWantedGame();
    @Query("SELECT * FROM gameapiresponse WHERE is_playing = 1 ORDER BY added")
    List<GameApiResponse> getPlayingGame();
    @Query("SELECT * FROM gameapiresponse WHERE is_played = 1 ORDER BY added")
    List<GameApiResponse> getPlayedGame();
    @Query("SELECT * FROM gameapiresponse WHERE is_explore = 1")
    List<GameApiResponse> getExploreGames();
}
