package com.example.kursovaya_igdb.source;

import com.example.kursovaya_igdb.model.GameApiResponse;

import java.util.List;

public abstract class BaseSavedGamesDataSource {
    protected GameCallback gameCallback;

    public void setGameCallback(GameCallback gameCallback) {
        this.gameCallback = gameCallback;
    }

    public abstract void getWantedGames();
    public abstract void addWantedGame(GameApiResponse game);
    public abstract void addPlayingGame(GameApiResponse game);
    public abstract void addPlayedGame(GameApiResponse game);
    public abstract void synchronizeWantedGame(List<GameApiResponse> notSynchronizedGamesList);
    public abstract void deleteWantedGame(GameApiResponse game);
    public abstract void getPlayingGames();
    public abstract void getPlayedGames();

    public abstract void synchronizePlayingGame(List<GameApiResponse> notSynchronizedGamesList);
    public abstract void deletePlayingGame(GameApiResponse game);

    public abstract void synchronizePlayedGame(List<GameApiResponse> notSynchronizedGamesList);
    public abstract void deletePlayedGame(GameApiResponse game);

}
