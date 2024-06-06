package com.example.kursovaya_igdb.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.kursovaya_igdb.model.GameApiResponse;

import java.util.List;

public interface IGamesRepository {
    MutableLiveData<List<GameApiResponse>> fetchPopularGames(long lastUpdate, boolean networkAvailable);
    MutableLiveData<List<GameApiResponse>> fetchBestGames(long lastUpdate, boolean networkAvailable);
    MutableLiveData<List<GameApiResponse>> fetchLatestGames(long lastUpdate, boolean networkAvailable);
    MutableLiveData<List<GameApiResponse>> fetchIncomingGames(long lastUpdate, boolean networkAvailable);
    MutableLiveData<GameApiResponse> fetchGame(int id);
    MutableLiveData<List<GameApiResponse>> fetchExploreGames(boolean networkAvailable);
    MutableLiveData<List<GameApiResponse>> fetchCompanyGames(String company);
    MutableLiveData<List<GameApiResponse>> fetchFranchiseGames(String franchise);
    MutableLiveData<List<GameApiResponse>> fetchGenreGames(String genre);
    void updateWantedGame(GameApiResponse game);
    void updatePlayingGame(GameApiResponse game);
    void updatePlayedGame(GameApiResponse game);
    MutableLiveData<List<GameApiResponse>> getWantedGames(boolean isFirstLoading);
    MutableLiveData<List<GameApiResponse>> getPlayingGames(boolean isFirstLoading);
    MutableLiveData<List<GameApiResponse>> getPlayedGames(boolean isFirstLoading);
    MutableLiveData<List<GameApiResponse>> getForYouGames(long lastUpdate);
    MutableLiveData<List<GameApiResponse>> getSearchedGames(String userInput);
    MutableLiveData<List<GameApiResponse>> getSimilarGames(List<Integer> similarGames);
    MutableLiveData<List<GameApiResponse>> getAllPopularGames();
    MutableLiveData<List<GameApiResponse>> getAllBestGames();
    MutableLiveData<List<GameApiResponse>> getAllLatestGames();
    MutableLiveData<List<GameApiResponse>> getAllIncomingGames();
    MutableLiveData<List<GameApiResponse>> getFilteredGames(String genre, String platform, String year);
}
