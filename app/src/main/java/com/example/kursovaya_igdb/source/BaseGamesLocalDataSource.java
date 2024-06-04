package com.example.kursovaya_igdb.source;

import com.example.kursovaya_igdb.model.GameApiResponse;

import java.util.List;

public abstract class BaseGamesLocalDataSource {
    protected GameCallback gameCallback;

    public void setGameCallback(GameCallback gameCallback) {
        this.gameCallback = gameCallback;
    }

    public abstract void getPopularGames();
    public abstract void insertGames(List<GameApiResponse> gameApiResponse, String i);
    public abstract void deleteAll();
    public abstract void getGame(int id);
    public abstract void getBestGames();
    public abstract void getLatestGames();
    public abstract void getIncomingGames();
    public abstract void getExploreGames();
}
