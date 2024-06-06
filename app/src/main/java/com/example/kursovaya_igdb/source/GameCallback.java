package com.example.kursovaya_igdb.source;

import com.example.kursovaya_igdb.model.GameApiResponse;

import java.util.List;

public interface GameCallback {
    void onSuccessFromRemote(List<GameApiResponse> gameApiResponse, String i);
    void onSuccessFromLocal(List<GameApiResponse> gameApiResponses, String i);
    void onSuccessDeletion();
    void onGameWantedStatusChanged(GameApiResponse updatedGame, List<GameApiResponse> wantedGames);
    void onGameWantedStatusChanged(List<GameApiResponse> wantedGames);
    void onGamePlayingStatusChanged(GameApiResponse updatedGame, List<GameApiResponse> wantedGames);
    void onGamePlayingStatusChanged(List<GameApiResponse> playingGames);
    void onGamePlayedStatusChanged(GameApiResponse updatedGame, List<GameApiResponse> wantedGames);
    void onGamePlayedStatusChanged(List<GameApiResponse> playedGames);
    void onSuccessFromCloudReading(List<GameApiResponse> wantedGames, String wanted);
    void onSuccessFromCloudWriting(GameApiResponse game, String wanted);
    void onFailureFromCloud(Exception e);
    void onSuccessSynchronization();
}

