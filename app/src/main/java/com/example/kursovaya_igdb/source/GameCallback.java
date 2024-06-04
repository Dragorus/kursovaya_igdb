package com.example.kursovaya_igdb.source;

import com.example.kursovaya_igdb.model.GameApiResponse;

import java.util.List;

public interface GameCallback {
    void onSuccessFromLocal(List<GameApiResponse> gameApiResponses, String i);
    void onSuccessFromRemote(List<GameApiResponse> gameApiResponse, String i);
    void onSuccessDeletion();
    void onSuccessFromCloudReading(List<GameApiResponse> wantedGames, String wanted);
    void onFailureFromCloud(Exception e);
    void onSuccessSynchronization();
}

